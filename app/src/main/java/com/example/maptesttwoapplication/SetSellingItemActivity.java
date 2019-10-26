package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class SetSellingItemActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText product_name, seller_name, seller_contact_no, seller_product_details
            , selling_price,seller_contact_details;
    private ImageView mImageView;
    private Button sellButton;
    private ProgressBar progressBar;
    int choice;
    String[] serviceListitem;
    String item = "";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private Uri mImageUri;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_selling_item);

        product_name = findViewById(R.id.seller_product_name);
        seller_name = findViewById(R.id.seller_name);
        seller_contact_no = findViewById(R.id.seller_contact_no);
        selling_price = findViewById(R.id.seller_selling_price);
        seller_product_details = findViewById(R.id.seller_product_details);
        seller_contact_details = findViewById(R.id.seller_contact_detail);

        mImageView = findViewById(R.id.seller_show_choose_file);
        sellButton = findViewById(R.id.sell_button);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.INVISIBLE);

        serviceListitem = getResources().getStringArray(R.array.selling_type);

        sendingNotificationToUser();

    }
    public void sellingType(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SetSellingItemActivity.this);
        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setSingleChoiceItems(serviceListitem, choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice = i;
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                item = serviceListitem[choice];

            }
        });
        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "select one service at least", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });


        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }


    public void chooseFile(View view) {
        openFileChooser();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void sellButton(View view) {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
        } else if (item.isEmpty()) {
            sellingType(view);
        } else {
            uploadFile(item);
            sellButton.setEnabled(false);
        }

    }

    private void uploadFile(final String item) {


        progressBar.setVisibility(View.VISIBLE);


        if (mImageUri != null) {
            String fileName = String.valueOf(System.currentTimeMillis());
            final StorageReference fileReference = firebaseStorage.getReference("Seller_Data")
                    .child("new_item").child(firebaseUser.getUid()).child(fileName
                            + "." + getFileExtension(mImageUri));
            final String imageFileName =fileName+ "." + getFileExtension(mImageUri);
            mUploadTask = fileReference.putFile(mImageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    final String downloadUrl =
                                            uri.toString();
                                    dataLoadingWithImage(downloadUrl,imageFileName);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void dataLoadingWithImage(String url,String imageFileName) {
        final DocumentReference documentReference = firebaseFirestore.collection("Seller_Data")
                .document("new_item").collection(firebaseUser.getUid()).document();

        Toast.makeText(SetSellingItemActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
        SellerData sellerData = new SellerData(documentReference.getId(),product_name.getText().toString(), seller_name.getText().toString()
                , seller_contact_no.getText().toString(), selling_price.getText().toString(), item,
                url, seller_product_details.getText().toString(),seller_contact_details.getText().toString(),imageFileName);
        documentReference.set(sellerData);
        loadDataForBuyer(documentReference.getId(),url,imageFileName);
        startActivity(new Intent(SetSellingItemActivity.this, MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void loadDataForBuyer(String id,String url,String imageFileName) {
        DocumentReference documentReference = firebaseFirestore.collection("buyer_Data")
                .document(id);
        SellerData sellerData = new SellerData(documentReference.getId(),product_name.getText().toString(), seller_name.getText().toString()
                , seller_contact_no.getText().toString(), selling_price.getText().toString(), item,
                url, seller_product_details.getText().toString(),seller_contact_details.getText().toString(),imageFileName);
        documentReference.set(sellerData);
    }
    private void sendingNotificationToUser() {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("NewProduct","myNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("selling")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    String msg = "successful";
                    if(!task.isSuccessful()){
                        msg="failed";
                    }
                        Toast.makeText(SetSellingItemActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}
