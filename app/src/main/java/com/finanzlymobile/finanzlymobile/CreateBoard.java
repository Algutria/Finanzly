package com.finanzlymobile.finanzlymobile;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateBoard extends AppCompatActivity {
    private EditText name, description;
    private TextInputLayout lblName;
    private TextInputLayout lblDescription;
    private Resources res;
    private ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        name = findViewById(R.id.txtName);
        description = findViewById(R.id.txtDescription);
        lblName = findViewById(R.id.lblName);
        lblDescription = findViewById(R.id.lblDescription);

        res = this.getResources();
        load_images();
    }

    public void load_images(){
        images = new ArrayList<>();
        //images.add(R.drawable.images);
        //images.add(R.drawable.important);
        //images.add(R.drawable.incomes);
        //images.add(R.drawable.expenses);
        images.add(R.drawable.calculate);
    }

    public void save(View v){
        if(isValid()) {
            Board b = new Board(name.getText().toString(), description.getText().toString(), Methods.randomImage(images), new ArrayList<Operation>());
            b.save();

            Snackbar.make(v, res.getString(R.string.saved_board), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            clear();
        }
    }

    public void clear(View v){
        clear();
    }

    public void clear(){
        name.setText("");
        description.setText("");
        name.requestFocus();
    }

    public boolean isValid(){
        if (validar_aux(name, lblName)) return false;
        else  if (validar_aux(description, lblDescription)) return false;
        else if (Methods.boardPresent(Data.getBoards(), name.getText().toString())){
            name.setError(res.getString(R.string.board_already_exist));
            name.requestFocus();
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
