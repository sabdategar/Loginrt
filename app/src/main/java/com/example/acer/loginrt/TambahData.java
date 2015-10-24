package com.example.acer.loginrt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Acer on 10/24/2015.
 */
public class TambahData extends AppCompatActivity{

    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data);

        TextView tvnama = (TextView)findViewById(R.id.tvnama);
        Intent i =getIntent();
        nama = i.getStringExtra("username");

        tvnama.setText(nama);

    }
}
