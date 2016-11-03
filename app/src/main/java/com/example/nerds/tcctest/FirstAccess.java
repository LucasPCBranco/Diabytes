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

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        session = new SessionManager(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configinicial);
        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Primeiro Acesso");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);
    }
    //Metodo usado no botão, manda o usuário de volta pro Main
    public void gotoMain(View view){
        Intent i = new Intent(FirstAccess.this, MainActivity.class);
        startActivity(i); }

       /* Button botao = (Button) findViewById(R.id.btn_cfg);
        final EditText fatorsensiM = (EditText) findViewById(R.id.txtBox_fatorsensiM);
        final EditText fatorsensiT = (EditText) findViewById(R.id.txtBox_fatorsensiT);
        final EditText fatorsensiN = (EditText) findViewById(R.id.txtBox_fatorsensiN);
        final EditText CHOporUIm = (EditText) findViewById(R.id.txtbox_CHOporUI_M);
        final EditText CHOporUIt = (EditText) findViewById(R.id.txtbox_CHOporUI_T);
        final EditText CHOporUIn = (EditText) findViewById(R.id.txtbox_CHOporUI_N);*/

    /* botao.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Atribuindo valores para as variáveis.
            float sensiM = Float.parseFloat(fatorsensiM.getText().toString());
            float sensiT = Float.parseFloat(fatorsensiT.getText().toString());
            float sensiN = Float.parseFloat(fatorsensiN.getText().toString());
            float uichoM = Float.parseFloat(CHOporUIm.getText().toString());
            float uichoT = Float.parseFloat(CHOporUIt.getText().toString());
            float uichoN = Float.parseFloat(CHOporUIn.getText().toString()); */

                /* Esses dados são salvos graças a sessão aberta no início do código*/
           /* session.salvarDados(sensiM, sensiT, sensiN, uichoM, uichoT, uichoN);

            Intent i = new Intent(FirstAccess.this, MainActivity.class);
            session.contexto.startActivity(i);
        }
    }); */
    }

