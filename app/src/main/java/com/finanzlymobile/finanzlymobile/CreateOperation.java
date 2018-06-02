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

public class CreateOperation extends AppCompatActivity {

    private String[] operations;
    private Spinner types;
    private EditText txtName, txtValue;
    private TextInputLayout lblName, lblValue;
    private Resources res;
    private int incomeImg, expenseImg;
    private RadioGroup isPaidRadios;
    private RadioButton paidYes;
    private RadioButton paidNo;
    private LinearLayout radiosWrapper;
    private TextView operationReason;
    private Intent i;
    private String boardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation);

        types = findViewById(R.id.cmbType);
        lblName = findViewById(R.id.lblName);
        txtName = findViewById(R.id.txtName);
        lblValue = findViewById(R.id.lblValue);
        txtValue = findViewById(R.id.txtValue);
        radiosWrapper = findViewById(R.id.is_paid_radios_wrapper);
        operationReason = findViewById(R.id.operation_reason);

        isPaidRadios = findViewById(R.id.is_paid_radios);
        paidYes = findViewById(R.id.paid_yes);
        paidNo = findViewById(R.id.paid_no);
        isPaidRadios.check(R.id.paid_no);

        radiosWrapper.setVisibility(View.GONE);

        res = this.getResources();

        loadImages();

        operations = res.getStringArray(R.array.operation_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, operations);
        types.setAdapter(adapter);

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

        i = getIntent();
        String boardName =  i.getStringExtra("boardName");
        boardId =  i.getStringExtra("boardId");
        operationReason.setText(res.getString(R.string.operation_description).toString() + "\n" + boardName);
    }

    private void loadImages() {
        incomeImg = R.drawable.incomes;
        expenseImg = R.drawable.expenses;
    }

    public void save(View v){
        Operation.Type opType = Operation.Type.INCOME;
        int selectedImage = incomeImg;
        boolean paid = false;

        if(types.getSelectedItemPosition() == 1){
            opType = Operation.Type.EXPENSE;
            selectedImage = expenseImg;
            paid = paidYes.isChecked();
        }

        if(isValid()) {
            Operation op = new Operation(txtName.getText().toString(), Double.parseDouble(txtValue.getText().toString()),
                    selectedImage, opType, paid);

            op.save(boardId);

            Snackbar.make(v, res.getString(R.string.saved_operation), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            clear();
        }
    }

    public boolean isValid(){
        if (validar_aux(txtName, lblName)) return false;
        else  if (validar_aux(txtValue, lblValue)) return false;

        if(Double.parseDouble(txtValue.getText().toString()) == 0.0){
            txtValue.requestFocus();
            txtValue.setError(res.getString(R.string.value_greater_than_zero));
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

    public void clear(View v){
        clear();
    }

    public void clear(){
        txtName.setText("");
        txtValue.setText("");
        types.setSelection(0);
        paidNo.setChecked(true);
        txtName.requestFocus();
    }
}
