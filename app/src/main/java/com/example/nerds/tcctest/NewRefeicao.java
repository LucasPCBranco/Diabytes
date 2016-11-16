package com.example.nerds.tcctest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Wallace on 02/11/2016.
 */

public class NewRefeicao extends AppCompatActivity{

    //Constantes - Essas constantes serão usadas para posteriormente servirem como base para a recriação da Activity
    static final String SOMA_CARB = "somaCarb";
    static final String LISTA_ALI = "alimentos"; //Vai servir como base para o Alimento

    public ListView ref_ListAlimentos;
    private float total; //Usado para armazenar as somas de carboidrato do usuário
    private ArrayList<String> alimentos = null; //ArrayList que será adaptada para a ListView dos alimentos

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


        if(bCalc != null){
            /*Uma vez que está setado, a informação adquirida deve ser usada para REGISTRO e CÁLCULO
            1°) Nome - fica salvo justamente na ListView */
            String nome = bCalc.getString("nome");
            alimentos.add(nome);
            /*2°) gCarb - vai se juntar a soma de carboidratos */
            float carb = bCalc.getFloat("carb");
            /*3°) porcao - será usado para cálculo mais correto do carboidrato */
            int porc = bCalc.getInt("porcao");
            total =+ (carb * porc);
            
        }

        //Adaptando os dados da Array na ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alimentos);
        ref_ListAlimentos.setAdapter(arrayAdapter);

    }

    /* Salvando os dados da Activity*/
    public void onSaveInstanceState(Bundle savedInstanceState){
        //Atribuindo valor a instância
        savedInstanceState.putFloat(SOMA_CARB, total);
        savedInstanceState.putStringArrayList(LISTA_ALI, alimentos);

        super.onSaveInstanceState(savedInstanceState);
    }

    /* Verificação e utilização do sistema para recuperar dados da activity*/
    public void onRestoredInstance(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Verificação - se já tem algo salvo, recupera esses valores
        if(savedInstanceState != null){
            //Dados a serem recuperados
            savedInstanceState.getFloat(SOMA_CARB);
            savedInstanceState.getStringArrayList(LISTA_ALI);

        }
    }
}
