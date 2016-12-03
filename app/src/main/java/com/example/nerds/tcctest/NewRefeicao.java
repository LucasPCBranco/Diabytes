package com.example.nerds.tcctest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Wallace on 02/11/2016.
 */

public class NewRefeicao extends AppCompatActivity{

    //Constantes - Essas constantes serão usadas para posteriormente servirem como base para a recriação da Activity
    static final String SOMA_CARB = "total";
    static final String LISTA_ALI = "alimentos"; //Vai servir como base para o Alimento
    static final String PERIODO = "periodo";

    public static final String PREFERENCIA = "MinhasPreferencias";

    public ListView ref_ListAlimentos;
    private float total, calculo; //Usado para armazenar as somas de carboidrato do usuário
    private List<String> alimentos = new ArrayList<String>(); //ArrayList que será adaptada para a ListView dos alimentos
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
        final SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA, 0);
        sharedPreferences.getFloat("calculo", 0);


        final DadosUsuario db = new DadosUsuario(this);
/*
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
        } */

        String serie = sharedPreferences.getString("alimentos", null);
        alimentos = Arrays.asList(TextUtils.split(serie, ","));

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

        double ManhaC, TardeC, NoiteC, ManhaF, TardeF, NoiteF;
        int Meta;
        ManhaC = TardeC = NoiteC = ManhaF = TardeF = NoiteF = Meta = 0;


        db.selectUsuario();
        try{
            /*Explicação: parecido com o sistema de Alimentos encontrado no MainActivity,
            seta o valor de variáveis para o cálculo futuro*/
            ManhaC = db.selectUsuario().get(1).getSensM();
            TardeC = db.selectUsuario().get(1).getSensT();
            NoiteC = db.selectUsuario().get(1).getSensN();

            //Normalmente faria um if/else, mas agora vou usar o método simples
            //float atual = Float.parseFloat(editText_glicemia.toString());
            float atual = Float.valueOf(editText_glicemia.getText().toString());
            double resDouble = (total / ManhaC) + ((atual - Meta) / ManhaF);

            //Partição do processo de Double para Float
            calculo = (float) (resDouble);

        }catch(Exception e){
            e.printStackTrace();
        }



        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Salvar: Fazer o cálculo baseado nos dados do usuário, e exibir detalhes - Primeiro
                momento, meter o louco. Essa tela será posteriormente alterada*/
                Bundle bNew = new Bundle();
                bNew.putString("alimento", nomeBundle);
                bNew.putString("nomeRef", editText_nome.toString());
                bNew.putString("periodo", txtPeriodo.toString());
                bNew.putDouble("calculo", calculo);

                Intent i = new Intent(NewRefeicao.this, DetalhesRefeicaoActivity.class);
                i.putExtras(bNew);
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
        //Sistema de TextUtils, muito útil Kappa
        savedInstanceState.putString(LISTA_ALI, TextUtils.join(",", alimentos));
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

    public void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("periodo", txtPeriodo.toString());
        editor.putFloat("calculo", calculo);
        editor.putString("nome", editText_nome.toString());
        //Esquematização de ArrayList para String. Processo será revertido através de sistema similar
        editor.putString("alimentos", TextUtils.join(",", alimentos));

        }

    public void onStop(){

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
