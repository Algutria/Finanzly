package com.finanzlymobile.finanzlymobile;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Data {
    private static String db = "Boards";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static ArrayList<Board> boards = new ArrayList<>();
    private static ArrayList<Operation> operations = new ArrayList<>();

    public static ArrayList<Board> getBoards() {
        return boards;
    }

    public static void saveBoard(Board b) {
        b.setId(databaseReference.push().getKey());
        databaseReference.child(db).child(b.getId()).setValue(b);
    }

    public static void deleteBoard(Board b){
        databaseReference.child(db).child(b.getId()).removeValue();
    }

    public static void editBoard(Board b){
        databaseReference.child(db).child(b.getId()).setValue(b);
    }

    public static void setBoards(ArrayList<Board> b){
        boards = b;
    }

    public static void setOperations(ArrayList<Operation> op){
        operations = op;
    }
}
