package com.finanzlymobile.finanzlymobile;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditBoard extends AppCompatActivity {
    private EditText txtNameEdit, txtDescriptionEdit;
    private TextInputLayout lblNameEdit, lblDescriptionEdit;
    private Resources res;
    private Intent i;
    private int image;
    private String id, name, description;
    private ArrayList<Operation> operations;
    private Board board;
    private Board newBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);

        txtNameEdit = findViewById(R.id.txtNameEdit);
        txtDescriptionEdit = findViewById(R.id.txtDescriptionEdit);
        lblNameEdit = findViewById(R.id.lblNameEdit);
        lblDescriptionEdit = findViewById(R.id.lblDescriptionEdit);

        res = this.getResources();

        i = getIntent();
        board = i.getParcelableExtra("data");

        id = board.getId();
        name = board.getName();
        description = board.getDescription();
        image = board.getImage();
        operations = board.getOperations();

        txtNameEdit.setText(name);
        txtDescriptionEdit.setText(description);
    }

    public void edit(View v){
        String nameValue = txtNameEdit.getText().toString();
        String descriptionValue = txtDescriptionEdit.getText().toString();

        if (isValid()){
            newBoard = new Board(id, nameValue, descriptionValue, image, operations);
            newBoard.edit();
            Snackbar.make(v, res.getString(R.string.edited_board), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            onBackPressedE();
        }
    }

    public boolean isValid(){
        if (validarAuxE(txtNameEdit, lblNameEdit)) return false;
        else if (validarAuxE(txtDescriptionEdit, lblDescriptionEdit)) return false;
        else if (Methods.boardPresentE(Data.getBoards(), txtNameEdit.getText().toString(), name)) {
            txtNameEdit.setError(res.getString(R.string.board_already_exist));
            txtNameEdit.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validarAuxE(TextView t, TextInputLayout ct){
        if (t.getText().toString().isEmpty()){
            t.requestFocus();
            t.setError(getString(R.string.cant_be_blank));
            return true;
        }

        return false;
    }

    public void clear(View v){
        clear();
    }

    public void clear(){
        txtNameEdit.setText("");
        txtDescriptionEdit.setText("");

        txtNameEdit.requestFocus();
    }

    public void onBackPressedE(){
        Intent i = new Intent(this, BoardDetails.class);
        i.putExtra("data", newBoard);
        startActivity(i);
    }
}
