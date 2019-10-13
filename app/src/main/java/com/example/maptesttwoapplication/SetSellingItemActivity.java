package com.example.maptesttwoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetSellingItemActivity extends AppCompatActivity {
    EditText product_name,seller_name,seller_contact_no, seller_product_details;

    int choice;
    List<String> allService ;
    String[] serviceListitem;
    boolean[] serviceCheckedItems;
    ArrayList<Integer> serviceUserSelectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_selling_item);

        product_name=findViewById(R.id.seller_product_name);
        seller_name=findViewById(R.id.seller_name);
        seller_contact_no=findViewById(R.id.seller_contact_no);
        seller_product_details=findViewById(R.id.seller_product_details);

        serviceListitem = getResources().getStringArray(R.array.selling_type);
        serviceCheckedItems = new boolean[serviceListitem.length];

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

    public void sellingType(View view) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SetSellingItemActivity.this);
            mBuilder.setTitle(R.string.dialog_title);
            mBuilder.setSingleChoiceItems(serviceListitem, choice, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    choice=i;
                    Toast.makeText(SetSellingItemActivity.this, ""+choice, Toast.LENGTH_SHORT).show();
                }
            });
            /*mBuilder.setMultiChoiceItems(serviceListitem, serviceCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {


                }
            });*/

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String item;
                    item = serviceListitem[choice];

                    Toast.makeText(SetSellingItemActivity.this, item, Toast.LENGTH_SHORT).show();


                }
            });

            mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "select one service at least", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    for (int i = 0; i < serviceCheckedItems.length; i++) {
                        serviceCheckedItems[i] = false;
                        serviceUserSelectedItems.clear();
                        Toast.makeText(getApplicationContext(), "select one service at least", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();

    }

    public void sellButton(View view) {
    }
}
