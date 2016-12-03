package com.example.nerds.tcctest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Wallace on 02/11/2016.
 */

public class NewRefeicao extends AppCompatActivity{

    //Constantes - Essas constantes serão usadas para posteriormente servirem como base para a recriação da Activity
    static final String SOMA_CARB = "total";
    static final String LISTA_ALI = "alimentos"; //Vai servir como base para o Alimento
    static final String PERIODO = "periodo";

    public ListView ref_ListAlimentos;
    private float total; //Usado para armazenar as somas de carboidrato do usuário
    private ArrayList<String> alimentos = new ArrayList<String>(); //ArrayList que será adaptada para a ListView dos alimentos
    private String periodo, nomeBundle;
    private TextView txtPeriodo; //Texto que tem o período selecionado derivado de TipoRefeicaoActivity
    private EditText editText_nome, editText_glicemia;
    private Button btnSalvar, btnAdd; //Botões para funcionalidades



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_refeicao);
        System.out.println("onCreate() UTILIZADO");


        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Nova Refeição");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        txtPeriodo = (TextView) findViewById(R.id.ref_txtPeriodoSelec);

        editText_nome = (EditText) findViewById(R.id.editText_nome);
        editText_glicemia = (EditText) findViewById(R.id.editText_glicemia);
        ref_ListAlimentos = (ListView) findViewById(R.id.ref_listAlimentos);

        //Pegando SharedPreferences
        final SharedPreferences sharedPreferences = getSharedPreferences(FirstAccess.PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();

        //Verificação - se já tem algo salvo, recupera esses valores
        if(savedInstanceState == null){
            System.out.println("ENTROU!");

        }else{
            //Dados a serem recuperados
            periodo = savedInstanceState.getString(PERIODO);
            txtPeriodo.setText(periodo);
            alimentos = savedInstanceState.getStringArrayList(LISTA_ALI);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alimentos);
            ref_ListAlimentos.setAdapter(arrayAdapter);
            total = savedInstanceState.getFloat(SOMA_CARB);
        }
/*
        periodo = (TextView) findViewById(R.id.ref_txtPeriodoSelec);

        periodo.setText(bTipo.getString("periodo"));
*/
        //Iniciando Bundle com informações pegas da CalcActivity
        Bundle bCalc = getIntent().getBundleExtra("bCalc");
        //Informações pegas da TipoRefeicaoActivity
        Bundle bTipo = getIntent().getBundleExtra("bTipo");

        if(bTipo != null) {
            String periodo = bTipo.getString("periodo");
            System.out.println("PERÍODO:  " + periodo);
            this.periodo = periodo; //Para uso de recuperação
            txtPeriodo.setText(periodo);
        }
        else if(bCalc != null){
            /*Uma vez que está setado, a informação adquirida deve ser usada para REGISTRO e CÁLCULO
            1°) Nome - fica salvo justamente na ListView */
            String nome = bCalc.getString("nome");
            System.out.println("AQUI, Ó: " + nome); //Teste para ver se a variável passa
            alimentos.add(nome);
            nomeBundle = nome;
            /*2°) gCarb - vai se juntar a soma de carboidratos */
            float carb = bCalc.getFloat("carb");
            System.out.println("CARBOIDRATOS de " + nome + ": " + carb);
            System.out.println("Pré-passagem: " + total);
            /*3°) porcao - será usado para cálculo mais correto do carboidrato */
            int porc = bCalc.getInt("porcao");
            System.out.println("NÚMEROS DE PORÇÕES: " + porc);
            total =+ (carb * porc);
            System.out.println("PÓS PASSAGEM: " +  total);
        }

        //Adaptando os dados da Array na ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alimentos);
        ref_ListAlimentos.setAdapter(arrayAdapter);

        btnSalvar = (Button) findViewById(R.id.ref_btnSalvar);
        btnAdd = (Button) findViewById(R.id.ref_btnNewAlimento);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Salvar: Fazer o cálculo baseado nos dados do usuário, e exibir detalhes - Primeiro
                momento, meter o louco. Essa tela será posteriormente alterada*/

                final float ManhaF = sharedPreferences.getFloat("fatorM", 0);
                final float TardeF = sharedPreferences.getFloat("fatorT", 0);
                final float NoiteF = sharedPreferences.getFloat("fatorN", 0);

                final float ManhaC = sharedPreferences.getFloat("CHOporuiM", 0);
                final float TardeC = sharedPreferences.getFloat("CHOporuiT", 0);
                final float NoiteC = sharedPreferences.getFloat("CHOporuiN", 0);

                final float Meta = sharedPreferences.getFloat("meta", 0);

                Bundle bNew = new Bundle();
                bNew.putString("alimento", nomeBundle);
                bNew.putString("nomeRef", editText_nome.toString());
                bNew.putString("periodo", "Manhã");


                //Normalmente faria um if/else, mas agora vou usar o método simples
                //float atual = Float.parseFloat(editText_glicemia.toString());
                float atual = Float.valueOf(editText_glicemia.getText().toString());
                float calculo = (total / ManhaC) + ((atual - Meta) / ManhaF);

                bNew.putFloat("calculo", calculo);


                Intent i = new Intent(NewRefeicao.this, DetalhesRefeicaoActivity.class);
                i.putExtra("bNew", bNew);
                startActivity(i);


            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add: Vai para a tela de seleção de origem dos alimentos
                 Intent i = new Intent(NewRefeicao.this, SelecaoActivity.class);
                startActivity(i);
            }
        });
    }

    /* Salvando os dados da Activity para sua reconstrução */
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Atribuindo valor a instância
        savedInstanceState.putFloat(SOMA_CARB, total);
        savedInstanceState.putStringArrayList(LISTA_ALI, alimentos);
        savedInstanceState.putString(PERIODO, periodo);
        System.out.println("onSaveInstanceState() UTILIZADO");
    }

    /* Verificação e utilização do sistema para recuperar dados da activity
    * Até segunda ordem, não será utilizado, por causa do sistema criado com OnSaveInstaceState()*/
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        total = savedInstanceState.getFloat(SOMA_CARB);
        alimentos = savedInstanceState.getStringArrayList(LISTA_ALI);
        periodo = savedInstanceState.getString(PERIODO);
        System.out.println("onRestoreInstanceState() Criado com sucesso");
    }

    public void onPause(Bundle savedInstanceState){
        super.onPause();
        if(savedInstanceState == null){
        savedInstanceState.putFloat(SOMA_CARB, total);
        savedInstanceState.putStringArrayList(LISTA_ALI, alimentos);
        savedInstanceState.putString(PERIODO, periodo);
        System.out.println("onPause() criado");
    }
    }

    public void onResume(Bundle savedInstanceState){
        super.onResume();
        if(savedInstanceState != null){
            total = savedInstanceState.getFloat(SOMA_CARB);
            alimentos = savedInstanceState.getStringArrayList(LISTA_ALI);
            periodo = savedInstanceState.getString(PERIODO);
            System.out.println("onResume() chamado");
        }
    }
}
