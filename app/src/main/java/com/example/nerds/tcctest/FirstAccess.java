package com.example.nerds.tcctest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    float sensiM;
    float sensiT;
    float sensiN;
    float uichoM;
    float uichoT;
    float uichoN;
    int metaglicemica;
    SharedPreferences sharedPreferences;

    static final String PREFERENCIAS = "myPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        session = new SessionManager(getApplicationContext());

        sharedPreferences = getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        final SharedPreferences.Editor ed = sharedPreferences.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configinicial);
        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Primeiro Acesso");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);
        Button botao = (Button) findViewById(R.id.btn_cfg);
        final EditText fatorsensiM = (EditText) findViewById(R.id.txtBox_fatorsensiM);
        final EditText fatorsensiT = (EditText) findViewById(R.id.txtBox_fatorsensiT);
        final EditText fatorsensiN = (EditText) findViewById(R.id.txtBox_fatorsensiN);
        final EditText CHOporUIm = (EditText) findViewById(R.id.txtbox_CHOporUI_M);
        final EditText CHOporUIt = (EditText) findViewById(R.id.txtbox_CHOporUI_T);
        final EditText CHOporUIn = (EditText) findViewById(R.id.txtbox_CHOporUI_N);
        final EditText metaGlicemica = (EditText) findViewById(R.id.txtbox_metaGlicemica);

        final DBLocal db = new DBLocal(this);


        System.out.println("PRIMEIRO ACESSO META: " + metaglicemica);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Atribuindo valores para as variáveis.
                sensiM = Float.parseFloat(fatorsensiM.getText().toString());
                sensiT = Float.parseFloat(fatorsensiT.getText().toString());
                sensiN = Float.parseFloat(fatorsensiN.getText().toString());
                uichoM = Float.parseFloat(CHOporUIm.getText().toString());
                uichoT = Float.parseFloat(CHOporUIt.getText().toString());
                uichoN = Float.parseFloat(CHOporUIn.getText().toString());
                metaglicemica = Integer.parseInt(metaGlicemica.getText().toString());

                Usuario u = new Usuario();
                u.setSensM(sensiM);
                u.setSensT(sensiT);
                u.setSensN(sensiN);

                u.setCHOuiM(uichoM);
                u.setCHOuiT(uichoT);
                u.setCHOuiN(uichoN);

                u.setMetaGlicemica(metaglicemica);
                db.insertUsuario(u);

               /* ed.putFloat("fatorM", sensiM);
                ed.putFloat("fatorT", sensiT);
                ed.putFloat("fatorN", sensiN);

                ed.putFloat("UIchoM", uichoM);
                ed.putFloat("UIchoT", uichoT);
                ed.putFloat("UIchoN", uichoN);

                ed.putFloat("meta", metaglicemica);

                ed.commit();
                 Esses dados são salvos graças a sessão aberta no início do código*/


                Intent i = new Intent(FirstAccess.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}

