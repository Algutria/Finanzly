package com.finanzlymobile.finanzlymobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class BoardDetails extends AppCompatActivity implements OperationAdapter.OnOperationClickListener {
    private static final String TAG = "================";
    private Toolbar toolbar;
    private Board b;
    private String vName, id, vDescription;
    private ArrayList<Operation> vOperations = new ArrayList<>();
    private int img;
    private Bundle bundle;
    private Intent i;
    private ImageView image;
    private Resources res;
    private TextView name, description;


    private RecyclerView listing;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_details);


        name = findViewById(R.id.lblName);
        description = findViewById(R.id.lblDescription);

        listing = findViewById(R.id.lstOperations);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        toolbar = findViewById(R.id.toolbar_title);
        //image = findViewById(R.id.boardImage);

        res = this.getResources();

        i = getIntent();
        Board board = (Board) i.getParcelableExtra("data");

        id = board.getId();
        vName = board.getName();
        vDescription = board.getDescription();
        Log.w(TAG, "onCreate: " + vDescription );
        Log.w(TAG, "onCreate: " + vOperations.size() );
        vOperations = board.getOperations();

        if(vOperations == null){
            Log.w(TAG, "onCreate: " + "EMPTYYYY" );
        }else{
            Log.w(TAG, "onCreate: " + "TIENEEEEE" );
        }

        img = board.getImage();

        toolbar.setTitle(vName);
        //image.setImageDrawable(ResourcesCompat.getDrawable(res,img,null));

        description.setText(vDescription);

        OperationAdapter adapter = new OperationAdapter(this.getApplicationContext(), vOperations, this);
        listing.setLayoutManager(llm);
        listing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    @Override
    public void onOperationClick(Operation op) {
        Intent i = new Intent(BoardDetails.this, Principal.class);
        i.putExtra("data", op);
        startActivity(i);
    }
}