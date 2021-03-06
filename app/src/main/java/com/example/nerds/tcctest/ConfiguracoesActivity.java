package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Nerds on 03/11/2016.
 */

public class ConfiguracoesActivity extends AppCompatActivity{

    float cfg_sensiM;
    float cfg_sensiT;
    float cfg_sensiN;
    float cfg_uichoM;
    float cfg_uichoT;
    float cfg_uichoN;
    int cfg_metaglicemica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Atualizar Dados");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);


        Button botao = (Button) findViewById(R.id.botaoSalvarConfig);
        final EditText newFatorM = (EditText) findViewById(R.id.newFatorM);
        final EditText newFatorT = (EditText) findViewById(R.id.newFatorT);
        final EditText newFatorN = (EditText) findViewById(R.id.newFatorN);
        final EditText newCHOm = (EditText) findViewById(R.id.newCHOm);
        final EditText newCHOt = (EditText) findViewById(R.id.newCHOt);
        final EditText newCHOn = (EditText) findViewById(R.id.newCHOn);
        final EditText newMeta = (EditText) findViewById(R.id.newMeta);

        final DBLocal db = new DBLocal(this);



        System.out.println("CONFIGURAÇÕES META: " + cfg_metaglicemica);
        cfg_sensiM = cfg_sensiT = cfg_sensiN = cfg_uichoM = cfg_uichoT = cfg_uichoN = cfg_metaglicemica = 0;

        System.out.println("CONFIGURAÇÕES META: " + cfg_metaglicemica);


                botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Atribuindo valores para as variáveis.
                if(!newFatorM.getText().toString().equals("")) {cfg_sensiM = Float.parseFloat(newFatorM.getText().toString());} else { cfg_sensiM = 0;}
                if(newFatorT != null || !newFatorT.equals("")) {cfg_sensiT = Float.parseFloat(newFatorT.getText().toString());}
                if(newFatorN != null || !newFatorN.equals("")) {cfg_sensiN = Float.parseFloat(newFatorN.getText().toString());}
                if(newCHOm != null || !newCHOm.equals("")) {cfg_uichoM = Float.parseFloat(newCHOm.getText().toString());}
                if(newCHOt != null || !newCHOt.equals("")) {cfg_uichoT = Float.parseFloat(newCHOt.getText().toString());}
                if(newCHOn != null || !newCHOn.equals("")) {cfg_uichoN = Float.parseFloat(newCHOn.getText().toString());}
                if(newMeta != null || !newMeta.equals("")) {cfg_metaglicemica = Integer.parseInt(newMeta.getText().toString());}

                Usuario u = new Usuario();
                if(cfg_sensiM != 0) {u.setSensM(cfg_sensiM);}
                if(cfg_sensiT != 0) {u.setSensT(cfg_sensiT);}
                if(cfg_sensiN != 0) {u.setSensN(cfg_sensiN);}

                if(cfg_uichoM != 0) {u.setCHOuiM(cfg_uichoM);}
                if(cfg_uichoT != 0) {u.setCHOuiT(cfg_uichoT);}
                if(cfg_uichoN != 0) {u.setCHOuiN(cfg_uichoN);}

                if(cfg_metaglicemica != 0) {u.setMetaGlicemica(cfg_metaglicemica);}

                System.out.println("CONFIGURAÇÕES META: " + cfg_metaglicemica);

                db.updateUsuario(u);

                Intent i = new Intent(ConfiguracoesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
