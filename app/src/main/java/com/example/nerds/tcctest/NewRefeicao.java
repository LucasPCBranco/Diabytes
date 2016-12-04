package com.example.nerds.tcctest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private double total, calculo; //Usado para armazenar as somas de carboidrato do usuário
    private ArrayList<String> alimentos = new ArrayList<String>(); //ArrayList que será adaptada para a ListView dos alimentos
    private String periodo, nomeBundle;
    private TextView txtPeriodo; //Texto que tem o período selecionado derivado de TipoRefeicaoActivity
    private EditText editText_nome, editText_glicemia, editText_data;
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
        editText_data = (EditText) findViewById(R.id.editText_data);
        ref_ListAlimentos = (ListView) findViewById(R.id.ref_listAlimentos);

        //Pegando SharedPreferences
        final SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA, 0);
        sharedPreferences.getFloat("calculo", 0);


        final DBLocal db = new DBLocal(this);

        System.out.println("TESTE:  " + savedInstanceState);

        //Iniciando Bundle com informações pegas da CalcActivity
        Bundle bCalc = getIntent().getBundleExtra("bCalc");
        System.out.println("BCALC, BUNDLE: " + bCalc);
        //Informações pegas da TipoRefeicaoActivity
        Bundle bTipo = getIntent().getBundleExtra("bTipo");

        if(bTipo != null) {
            String periodo = bTipo.getString("periodo");
            System.out.println("PERÍODO:  " + periodo);
            this.periodo = periodo; //Para uso de recuperação
            txtPeriodo.setText(periodo);
        }
        else if(bCalc != null) {
            alimentos = bCalc.getStringArrayList("alimento");
            String nome = bCalc.getString("nomeAli");
                // 1°) Nome - fica salvo justamente na ListView
                System.out.println("AQUI, Ó: " + nome); //Teste para ver se a variável passa
                alimentos.add(nome);
                nomeBundle = nome;
            /*Uma vez que está setado, a informação adquirida deve ser usada para REGISTRO e CÁLCULO */
            editText_nome.setText(bCalc.getString("nomeRef"));
            editText_data.setText(bCalc.getString("data"));
            System.out.println(bCalc.getString("data"));
            txtPeriodo.setText(bCalc.getString("periodo"));
            double tot = bCalc.getDouble("total");
            /*2°) gCarb - vai se juntar a soma de carboidratos */
            float carb = bCalc.getFloat("carb");
            System.out.println("CARBOIDRATOS de " + nome + ": " + carb);
            System.out.println("Pré-passagem: " + total);
            /*3°) porcao - será usado para cálculo mais correto do carboidrato */
            int porc = bCalc.getInt("porcao");
            System.out.println("NÚMEROS DE PORÇÕES: " + porc);
            tot = (carb * porc);
            System.out.println("PÓS PASSAGEM: " + tot);
            //Adaptando os dados da Array na ListView
            if (alimentos != null) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alimentos);
                ref_ListAlimentos.setAdapter(arrayAdapter);
            }
            total = total + tot;
        }
        btnSalvar = (Button) findViewById(R.id.ref_btnSalvar);
        btnAdd = (Button) findViewById(R.id.ref_btnNewAlimento);

        double ManhaC, TardeC, NoiteC, ManhaF, TardeF, NoiteF;
        int Meta;
        ManhaC = TardeC = NoiteC = ManhaF = TardeF = NoiteF = Meta = 0;

        db.selectUsuario();
        for(int i = 0; i < db.selectUsuario().size(); i++) {
            if(db.selectUsuario().size() > 0) {
            /*Explicação: parecido com o sistema de Alimentos encontrado no MainActivity,
            seta o valor de variáveis para o cálculo futuro*/
                ManhaC = db.selectUsuario().get(i).getSensM();
                TardeC = db.selectUsuario().get(i).getSensT();
                NoiteC = db.selectUsuario().get(i).getSensN();

                Meta =  db.selectUsuario().get(i).getMetaGlicemica();

                if(ManhaC != 0) {
                    //Normalmente faria um if/else, mas agora vou usar o método simples
                    //float atual = Float.parseFloat(editText_glicemia.toString());


                    String atualT = editText_glicemia.getText().toString();
                    double atual;

                    if(atualT== null || atualT.isEmpty()) {
                        atual = 0.0;
                    } else {
                        atual = Double.parseDouble(editText_glicemia.getText().toString());
                    }
                    calculo = (total / ManhaC) + ((atual - Meta) / ManhaF);
                }
            }
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Salvar: Fazer o cálculo baseado nos dados do usuário, e exibir detalhes - Primeiro
                momento, meter o louco. Essa tela será posteriormente alterada*/
                Bundle bNew = new Bundle();
                ArrayList<String> al = alimentos;
                bNew.putStringArrayList("alimento", al);
                bNew.putString("nomeRef", editText_nome.getText().toString());
                bNew.putString("periodo", txtPeriodo.getText().toString());
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
                Bundle bNew = new Bundle();
                ArrayList<String> al = alimentos;
                bNew.putStringArrayList("alimento", al);
                bNew.putString("nomeRef", editText_nome.getText().toString());
                bNew.putString("periodo", txtPeriodo.getText().toString());
                bNew.putString("data", editText_data.getText().toString());
                System.out.println("PASSADO PARA O BUNDLE " + total);
                bNew.putDouble("total", total);

                 Intent i = new Intent(NewRefeicao.this, SelecaoActivity.class);
                i.putExtras(bNew);
                startActivity(i);
            }
        });
    }

    /* Salvando os dados da Activity para sua reconstrução */
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Atribuindo valor a instância
       /* savedInstanceState.putDouble(SOMA_CARB, total);
        //Sistema de TextUtils, muito útil Kappa
        savedInstanceState.putString(LISTA_ALI, TextUtils.join(",", alimentos));
        savedInstanceState.putString(PERIODO, periodo);
        System.out.println("onSaveInstanceState() UTILIZADO"); */
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

        }

    public void onStop(){
        super.onStop();

        System.out.println("onStop() utilizado");
    }

    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA, 0);
        if(sharedPreferences != null){
            total = sharedPreferences.getFloat("calculo", 0);
            String serie = sharedPreferences.getString("alimentos", null);
            if(serie != null) {
                //alimentos = Arrays.asList(TextUtils.split(serie, ","));
            }
            periodo = sharedPreferences.getString("periodo", null);
            System.out.println("onResume() chamado");
        }
    }
}
