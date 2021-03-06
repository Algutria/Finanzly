package com.finanzlymobile.finanzlymobile;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {
    private static String db = FirebaseAuth.getInstance().getUid();
    private static String opDB = "operations";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static ArrayList<Board> boards = new ArrayList<>();
    private static ArrayList<Operation> operations = new ArrayList<>();

    public static ArrayList<Board> getBoards() {
        return boards;
    }

    public static ArrayList<Operation> getOperations(String boardId) {
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getId().equals(boardId)) {
                if (boards.get(i).getOperations() != null) {
                    return boards.get(i).getOperations();
                }
            }
        }

        return new ArrayList<>();
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

    public static void saveOperation(Operation operation, String boardId) {
        int id = 0;
        for (int i = 0; i < boards.size(); i++){
            if(boards.get(i).getId().equals(boardId)){
                if(boards.get(i).getOperations() != null){
                    id = boards.get(i).getOperations().size();
                    operation.setId(id+"");
                    boards.get(i).getOperations().add(operation);
                }
            }
        }

        operation.setId(id+"");

        databaseReference.child(db).child(boardId).child(opDB).child(""+id).setValue(operation);
    }

    public static void editOperation(Operation operation, String boardId) {
        databaseReference.child(db).child(boardId).child(opDB).child(operation.getId()).setValue(operation);
    }

    public static void deleteOperation(Operation operation, String boardId) {
        databaseReference.child(db).child(boardId).child(opDB).child(operation.getId()).removeValue();
    }
}
