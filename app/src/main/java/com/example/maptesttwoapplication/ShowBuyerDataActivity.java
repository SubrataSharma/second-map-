package com.example.maptesttwoapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.maptesttwoapplication.Fragment.BuyerFragment;
import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ShowBuyerDataActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView buyer_price_data,buyer_product_name_address
            ,product_detail,seller_detail;
    private ImageView product_image;
    private ProgressBar progressBar;
    private String sellerPhoneNO = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buyer_data);
        product_image = findViewById(R.id.buyer_product_image_show);
        buyer_price_data = findViewById(R.id.buyer_price_data_show);
        buyer_product_name_address = findViewById(R.id.buyer_product_name_address_show);
        product_detail = findViewById(R.id.product_detail_data_show);
        seller_detail = findViewById(R.id.seller_data_show);
        progressBar =findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);
        String buyerId = Objects.requireNonNull(getIntent().getExtras()).getString("buyerID");

        assert buyerId != null;
        db.collection("buyer_Data").document(buyerId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        assert documentSnapshot != null;
                        SellerData sellerData = documentSnapshot.toObject(SellerData.class);
                        assert sellerData != null;
                        sellerPhoneNO = sellerData.getContactNo();
                        dataLoadingForBuyer(sellerData);
                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void dataLoadingForBuyer(SellerData sellerData) {
        assert sellerData != null;
        Glide.with(ShowBuyerDataActivity.this)
                .load(sellerData.getFileUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;

                    }
                }).into(product_image);
        buyer_price_data.setText("₹ "+sellerData.getSellingPrice());
        if(sellerData.getSellingType().equals("wholesaling")){
        String nameAddress=" " + sellerData.getProductName() + " <br>"+
                "for <b>" +sellerData.getSellingType() + "</b><br> "
                + " ☞ " + sellerData.getContactDetail();
        buyer_product_name_address.setText(HtmlCompat.fromHtml(nameAddress, HtmlCompat.FROM_HTML_MODE_COMPACT)
                , TextView.BufferType.SPANNABLE);
        }else {
            String nameAddress=" " + sellerData.getProductName() + " <br>"
                    + " ☞ " + sellerData.getContactDetail();
            buyer_product_name_address.setText(HtmlCompat.fromHtml(nameAddress, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    , TextView.BufferType.SPANNABLE);
        }
        product_detail.setText(sellerData.getProductDetail());
        String sellerDetail="<i>name</i> : "+ sellerData.getSellerName() + " <br>"
                + "\uD83D\uDCF1 +0 " + sellerData.getContactNo();
        seller_detail.setText(HtmlCompat.fromHtml(sellerDetail, HtmlCompat.FROM_HTML_MODE_COMPACT)
                , TextView.BufferType.SPANNABLE);

    }

    public void callToSellerButton(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sellerPhoneNO, null));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(this, .class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        super.onBackPressed();
    }
}
