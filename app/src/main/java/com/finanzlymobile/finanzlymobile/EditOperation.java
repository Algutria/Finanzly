package com.finanzlymobile.finanzlymobile;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EditOperation extends AppCompatActivity {
    private Spinner types;
    private EditText txtNameEdit, txtValueEdit;
    private TextInputLayout lblNameEdit, lblValueEdit;
    private String id, name;
    private double value;
    private Resources res;
    private RadioGroup isPaidRadios;
    private RadioButton paidYes;
    private RadioButton paidNo;
    private LinearLayout radiosWrapper;
    private Intent i;
    private int incomeImg, expenseImg;
    private Board board;
    private Operation operation;
    private Operation newOperation;
    private String[] operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_operation);

        txtNameEdit = findViewById(R.id.txtNameEdit);
        txtValueEdit = findViewById(R.id.txtValueEdit);
        lblNameEdit = findViewById(R.id.lblNameEdit);
        lblValueEdit = findViewById(R.id.lblDescriptionEdit);
        types = findViewById(R.id.cmbTypeEdit);
        radiosWrapper = findViewById(R.id.is_paid_radios_wrapper);
        isPaidRadios = findViewById(R.id.is_paid_radios_edit);
        paidYes = findViewById(R.id.paid_yes);
        paidNo = findViewById(R.id.paid_no);

        res = this.getResources();

        i = getIntent();
        operation = i.getParcelableExtra("data");
        board = i.getParcelableExtra("board");

        id = operation.getId();
        name = operation.getName();
        value = operation.getValue();
        int image = operation.getImage();
        Operation.Type type = operation.getType();
        boolean isPaid = operation.isPaid();


        txtNameEdit.setText(name);
        txtValueEdit.setText(Math.round(value)+"");

        if(isPaid){
            isPaidRadios.check(R.id.paid_yes);
        }else{
            isPaidRadios.check(R.id.paid_no);
        }

        res = this.getResources();

        loadImages();

        operations = res.getStringArray(R.array.operation_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, operations);
        types.setAdapter(adapter);

        radiosWrapper.setVisibility(View.GONE);
        if(type == Operation.Type.INCOME){
            types.setSelection(0);
            radiosWrapper.setVisibility(View.GONE);
        }else{
            types.setSelection(1);
            radiosWrapper.setVisibility(View.VISIBLE);
        }

        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    radiosWrapper.setVisibility(View.VISIBLE);
                }else{
                    radiosWrapper.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void edit(View v){

        Operation.Type opType = Operation.Type.INCOME;
        int selectedImage = incomeImg;
        boolean paid = false;

        if(types.getSelectedItemPosition() == 1){
            opType = Operation.Type.EXPENSE;
            selectedImage = expenseImg;
            paid = paidYes.isChecked();
        }

        if(isValid()) {
            Operation op = new Operation(operation.getId(), txtNameEdit.getText().toString(), Double.parseDouble(txtValueEdit.getText().toString()),
                    selectedImage, opType, paid);

            op.edit(board.getId());

            Snackbar.make(v, res.getString(R.string.edited_operation), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            Intent i = new Intent(EditOperation.this, BoardDetails.class);
            i.putExtra("data", board);
            startActivity(i);
        }
    }

    private void loadImages() {
        incomeImg = R.drawable.incomes;
        expenseImg = R.drawable.expenses;
    }

    public void clear(View v){
        clear();
    }

    public void clear(){
        txtNameEdit.setText("");
        txtValueEdit.setText("");
        types.setSelection(0);
        paidNo.setChecked(true);
        txtNameEdit.requestFocus();
    }

    public boolean isValid(){
        if (validar_aux(txtNameEdit, lblNameEdit)) return false;
        else  if (validar_aux(txtValueEdit, lblValueEdit)) return false;

        if(Double.parseDouble(txtValueEdit.getText().toString()) == 0.0){
            txtValueEdit.requestFocus();
            txtValueEdit.setError(res.getString(R.string.value_greater_than_zero));
            return false;
        }

        return true;
    }

    public boolean validar_aux(TextView t, TextInputLayout ct){
        if (t.getText().toString().isEmpty()){
            t.requestFocus();
            t.setError(res.getString(R.string.cant_be_blank));
            return true;
        }
        return false;
    }
}
