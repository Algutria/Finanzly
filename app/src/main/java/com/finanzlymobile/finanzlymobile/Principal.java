package com.finanzlymobile.finanzlymobile;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements  BoardAdapter.OnBoardClickListener{
    private RecyclerView listing;
    private ArrayList<Board> boards;
    private Resources res;
    private BoardAdapter adapter;
    private LinearLayoutManager llm;
    private Intent i;
    private DatabaseReference databaseReference;
    private final String BD = "Boards";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listing = findViewById(R.id.lstOptions);

        res = this.getResources();

        boards = new ArrayList<>();

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        adapter = new BoardAdapter(this.getApplicationContext(), boards, this);

        listing.setLayoutManager(llm);
        listing.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(BD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boards.clear();

                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Board b = snapshot.getValue(Board.class);
                        boards.add(b);
                    }
                }
                adapter.notifyDataSetChanged();
                Data.setBoards(boards);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(Principal.this, CreateBoard.class);
                startActivity(i);
            }
        });
    }

    public void onBoardClick(Board b) {
        Intent i = new Intent(Principal.this, BoardDetails.class);
        i.putExtra("data", b);
        startActivity(i);
    }
}
