package com.example.nerds.tcctest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Wallace on 02/11/2016.
 */

public class NewRefeicao extends AppCompatActivity{

    public ListView ref_ListAlimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Iniciando Bundle com informações pegas da CalcActivity
        Bundle bCalc = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_refeicao);
        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Nova Refeição");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        ref_ListAlimentos = (ListView) findViewById(R.id.ref_listAlimentos);
        ArrayList<String> alimentos = null; //Aqui vai ser de onde a lista será adaptada
        float total = 0; //De alguma forma, esse número será passado

        if(bCalc != null){
            /*Uma vez que está setado, a informação adquirida deve ser usada para REGISTRO e CÁLCULO
            1°) Nome - fica salvo justamente na ListView */
            String nome = bCalc.getString("nome");
            alimentos.add(nome);
            /*2°) gCarb - vai se juntar a soma de carboidratos */
            float carb = bCalc.getFloat("carb");
            /*3°) porcao - será usado para cálculo mais correto do carboidrato */
            int porc = bCalc.getInt("porcao");
            
        }

    }
}
