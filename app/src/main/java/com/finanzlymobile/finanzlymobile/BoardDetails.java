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
    private static final String TAG = "=====================";
    private Toolbar toolbar;
    private Board board;
    private String vName, id, vDescription;
    private ArrayList<Operation> vOperations = new ArrayList<>();
    private int img;
    private Bundle bundle;
    private Intent i;
    private ImageView image;
    private Resources res;
    private TextView name, description, totalIncomes, totalExpenses, totalPocket, totalBalance;

    private double incomes = 0, expenses = 0;

    private RecyclerView listing;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_details);

        name = findViewById(R.id.lblName);
        description = findViewById(R.id.lblDescription);
        totalIncomes = findViewById(R.id.total_incomes);
        totalExpenses = findViewById(R.id.total_expenses);
        totalPocket = findViewById(R.id.total_pocket);
        totalBalance = findViewById(R.id.total_balance);

        listing = findViewById(R.id.lstOperations);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        toolbar = findViewById(R.id.toolbar_title);
        //image = findViewById(R.id.boardImage);

        res = this.getResources();

        i = getIntent();
        board = (Board) i.getParcelableExtra("data");

        id = board.getId();
        vName = board.getName();
        vDescription = board.getDescription();
        vOperations = board.getOperations();

        img = board.getImage();

        toolbar.setTitle(vName);
        //image.setImageDrawable(ResourcesCompat.getDrawable(res,img,null));

        description.setText(vDescription);

        OperationAdapter adapter = new OperationAdapter(this.getApplicationContext(), vOperations, this);
        listing.setLayoutManager(llm);
        listing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        double totalPocketValue  = 0;

        if(vOperations != null){
            for (int i = 0; i < vOperations.size(); i++){
                if(vOperations.get(i) != null) {
                    if (vOperations.get(i).getType() == Operation.Type.INCOME) {

                        incomes += vOperations.get(i).getValue();
                    }

                    if (vOperations.get(i).getType() == Operation.Type.EXPENSE) {
                        expenses += vOperations.get(i).getValue();

                        if(vOperations.get(i).isPaid()){
                            totalPocketValue += vOperations.get(i).getValue();
                        }
                    }
                }
            }
        }

        totalIncomes.setText(numberToCurrency(incomes));
        totalExpenses.setText(numberToCurrency(expenses));

        double balance= incomes - expenses;
        totalBalance.setText(numberToCurrency(balance));

        totalPocket.setText(numberToCurrency(incomes - totalPocketValue));

        ImageView newIncome = findViewById(R.id.new_income);
        ImageView newExpense = findViewById(R.id.new_expense);

        newIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToOperations(Operation.Type.EXPENSE);
            }
        });

        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToOperations(Operation.Type.INCOME);
            }
        });
    }

    public void sendToOperations(Operation.Type type){
        Intent i = new Intent(BoardDetails.this, CreateOperation.class);
        i.putExtra("type", type);
        i.putExtra("boardId", board.getId());

        startActivity(i);
    }

    public String numberToCurrency(double value){
        String formattedValue = "$" + NumberFormat.getNumberInstance().format(value);
        return formattedValue;
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
        i.putExtra("data", board);

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