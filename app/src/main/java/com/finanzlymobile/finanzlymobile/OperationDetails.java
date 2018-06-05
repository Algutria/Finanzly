package com.finanzlymobile.finanzlymobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class OperationDetails extends AppCompatActivity {
    private Board board;
    private Operation operation;
    private String vName, id, vType;
    private double vValue;
    private boolean vIsPaid;
    private String operationPaid;

    private int img;
    private ImageView image;

    private Bundle bundle;
    private Intent i;
    private Resources res;

    private TextView lblName, lblValue, lblType, lblIsPaid;
    private LinearLayout isPaidLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_details);

        lblName = findViewById(R.id.lblName);
        lblValue = findViewById(R.id.lblValue);
        lblType = findViewById(R.id.lblType);
        lblIsPaid = findViewById(R.id.lblIsPaid);
        isPaidLayout = findViewById(R.id.is_paid_layout);

        res = this.getResources();

        i = getIntent();
        operation = i.getParcelableExtra("data");
        board = i.getParcelableExtra("board");

        image = findViewById(R.id.image);
        image.setImageResource(operation.getImage());

        id = operation.getId();
        vName = operation.getName();
        vValue = operation.getValue();
        vIsPaid = operation.isPaid();

        if (operation.getType() == Operation.Type.INCOME) {
            vType = res.getString(R.string.income);
            isPaidLayout.setVisibility(View.GONE);
        } else {
            vType = res.getString(R.string.expense);
            operationPaid = vIsPaid ? res.getString(R.string.yes) : res.getString(R.string.no);
            lblIsPaid.setText(operationPaid);
            isPaidLayout.setVisibility(View.VISIBLE);
        }

        lblName.setText(vName);
        lblValue.setText(Methods.numberToCurrency(vValue));
        lblType.setText(vType);
    }

    public void delete(View v){
        String positiveAns, negativeAns;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(res.getString(R.string.delete_operacion));
        builder.setMessage(res.getString(R.string.are_you_sure_operation));
        positiveAns = res.getString(R.string.yes);
        negativeAns = res.getString(R.string.no);

        builder.setPositiveButton(positiveAns, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                operation.delete(board.getId());
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
        Intent i = new Intent(OperationDetails.this, EditOperation.class);
        i.putExtra("data", operation);
        i.putExtra("board", board);

        startActivity(i);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(OperationDetails.this, BoardDetails.class);
        i.putExtra("data", board);
        startActivity(i);
    }
}
