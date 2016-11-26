package com.example.nerds.tcctest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Wallace on 02/11/2016.
 */

public class NewRefeicao extends AppCompatActivity{

    //Constantes - Essas constantes serão usadas para posteriormente servirem como base para a recriação da Activity
    static final String SOMA_CARB = "somaCarb";
    static final String LISTA_ALI = "alimentos"; //Vai servir como base para o Alimento
    static final String PERIODO = "periodo";

    public ListView ref_ListAlimentos;
    private float total; //Usado para armazenar as somas de carboidrato do usuário
    private ArrayList<String> alimentos = new ArrayList<String>(); //ArrayList que será adaptada para a ListView dos alimentos
    private String periodo;
    private TextView txtPeriodo; //Texto que tem o período selecionado derivado de TipoRefeicaoActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        txtPeriodo = (TextView) findViewById(R.id.ref_txtPeriodoSelec);

        //Verificação - se já tem algo salvo, recupera esses valores
        if(savedInstanceState != null){
            //Dados a serem recuperados
            total = savedInstanceState.getFloat(SOMA_CARB);
            alimentos = savedInstanceState.getStringArrayList(LISTA_ALI);
            periodo = savedInstanceState.getString(PERIODO);

        }

        //Iniciando Bundle com informações pegas da CalcActivity
        Bundle bCalc = getIntent().getBundleExtra("bCalc");
        //Informações pegas da TipoRefeicaoActivity
        Bundle bTipo = getIntent().getBundleExtra("bTipo");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_refeicao);
        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Nova Refeição");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        ref_ListAlimentos = (ListView) findViewById(R.id.ref_listAlimentos);

        String periodo = bTipo.getString("periodo");
        System.out.println("PERÍODO:  " + periodo);
        if(periodo != null) {
            //Selecionando o texto da Bundle.
            txtPeriodo.setText(periodo);
        }
        if(bCalc == null){
            //Ué
        }else{
            /*Uma vez que está setado, a informação adquirida deve ser usada para REGISTRO e CÁLCULO
            1°) Nome - fica salvo justamente na ListView */
            String nome = bCalc.getString("nome");
            System.out.println("AQUI, Ó: " + nome); //Teste para ver se a variável passa
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
        savedInstanceState.putString(PERIODO, periodo);
        super.onSaveInstanceState(savedInstanceState);
    }

    /* Verificação e utilização do sistema para recuperar dados da activity*/
    public void onRestoredInstance(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        total = savedInstanceState.getFloat(SOMA_CARB);
        alimentos = savedInstanceState.getStringArrayList(LISTA_ALI);
        periodo = savedInstanceState.getString(PERIODO);
    }
}
