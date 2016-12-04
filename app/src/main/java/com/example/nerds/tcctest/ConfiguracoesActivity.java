package com.example.nerds.tcctest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Nerds on 03/11/2016.
 */

public class ConfiguracoesActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle(R.string.tbTitle);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);
    }
}
