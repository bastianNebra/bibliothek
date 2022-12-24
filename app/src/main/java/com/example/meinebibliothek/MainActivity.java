package com.example.meinebibliothek;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_Button;
    MeinDatenBankManager myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_Button= findViewById(R.id.add_button);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EinfuegenActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MeinDatenBankManager(MainActivity.this);
        book_id= new ArrayList<>();
        book_title= new ArrayList<>();
        book_author= new ArrayList<>();
        book_pages= new ArrayList<>();
        storeDataInArrays();

        customAdapter= new CustomAdapter(MainActivity.this,this, book_id,
                book_title, book_author,
                book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(MainActivity.this)));


        //Auslogen
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton btnLogout = findViewById(R.id.button_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.ausgabeAlles();
        //es gibt keine Daten
        if(cursor.getCount() == 0){
            Toast.makeText(this," Keine Daten", Toast.LENGTH_SHORT).show();

        }
        else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}