package com.finanzlymobile.finanzlymobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardDetails extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Board b;
    private String vName, id, vDescription;
    private int img;
    private Bundle bundle;
    private Intent i;
    private ImageView image;
    private Resources res;
    private TextView name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_details);


        name = findViewById(R.id.lblName);
        description = findViewById(R.id.lblDescription);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        //image = findViewById(R.id.boardImage);

        res = this.getResources();

        i = getIntent();
        bundle = i.getBundleExtra("datos");

        id = bundle.getString("id");
        vName = bundle.getString("name");
        vDescription = bundle.getString("description");
        //img = bundle.getInt("image");

        collapsingToolbarLayout.setTitle(vName);
        //image.setImageDrawable(ResourcesCompat.getDrawable(res,img,null));

        description.setText(vDescription);
    }

    public void delete(View v){
        String positiveAns, negativeAns;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(res.getString(R.string.delete_board));
        builder.setMessage(res.getString(R.string.are_you_sure));
        positiveAns = res.getString(R.string.yes);
        negativeAns = res.getString(R.string.no);

        builder.setPositiveButton(positiveAns, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Board b = new Board(id);
                b.delete();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negativeAns, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void edit(View v){
        Intent i = new Intent(BoardDetails.this, EditBoard.class);
        Bundle b2 = new Bundle();

        b2.putString("id", id);
        b2.putString("name", vName);
        b2.putString("description", vDescription);
        i.putExtra("data", b2);

        startActivity(i);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(BoardDetails.this, Principal.class);
        startActivity(i);
    }
}