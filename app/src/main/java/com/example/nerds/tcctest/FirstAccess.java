package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Wallace on 17/10/2016.
 */

public class FirstAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_configinicial);

    //Definindo a toolbar
    Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(my_toolbar);

    //Titulo e icone que fica na toolbar
    getSupportActionBar().setTitle("Primeiro Acesso");
    getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        Button botao = (Button) findViewById(R.id.btn_cfg);
        EditText fatorsensiM = (EditText) findViewById(R.id.txtBox_fatorsensiM);
        EditText fatorsensiT = (EditText) findViewById(R.id.txtBox_fatorsensiT);
        EditText fatorsensiN = (EditText) findViewById(R.id.txtBox_fatorsensiN);
        EditText UIporCHOm = (EditText) findViewById(R.id.txtbox_UIporCHO_M);
        EditText UIporCHOt = (EditText) findViewById(R.id.txtbox_UIporCHO_T);
        EditText UIporCHOn = (EditText) findViewById(R.id.txtbox_UIporCHO_N);
    }

    public void gotoMain(View view){
        Intent i = new Intent(FirstAccess.this, MainActivity.class);
    }
}
