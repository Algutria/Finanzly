package com.finanzlymobile.finanzlymobile;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements  BoardAdapter.OnBoardClickListener{
    private RecyclerView listing;
    private ArrayList<Board> boards = new ArrayList<>();
    private BoardAdapter adapter;
    private LinearLayoutManager llm;
    private Intent i;
    private DatabaseReference databaseReference;
    private final String BD = "Boards";
    private FloatingActionButton fab;
    private LinearLayout emptyState;
    private TextView boardsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listing = findViewById(R.id.lstOptions);
        fab = findViewById(R.id.fab);
        emptyState = findViewById(R.id.empty_state);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BoardAdapter(this.getApplicationContext(), boards, this);

        listing.setLayoutManager(llm);
        listing.setAdapter(adapter);

        listing.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);
        emptyState.setVisibility(View.INVISIBLE);

        boardsTitle = findViewById(R.id.boards_title);
        boardsTitle.setVisibility(View.INVISIBLE);

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

                if(boards.size() == 0){
                    listing.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                    emptyState.setVisibility(View.VISIBLE);
                }else{
                    emptyState.setVisibility(View.INVISIBLE);
                    listing.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    boardsTitle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(Principal.this, CreateBoard.class);
                startActivity(i);
            }
        });
    }

    public void createBoard(View v){
        i = new Intent(Principal.this, CreateBoard.class);
        startActivity(i);
    }

    public void onBoardClick(Board b) {
        Intent i = new Intent(Principal.this, BoardDetails.class);
        i.putExtra("data", b);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.termonology:
                return true;
            case R.id.about_us:
                return true;
            case R.id.logout:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
