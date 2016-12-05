package com.example.nerds.tcctest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetalhesRefeicaoActivity extends AppCompatActivity {

    private TextView textNome;
    private String nome, tipo, data;
    private double ui;
    private TextView txtNome, txtTipo, txtUi, txtData;
    private ListView alimentoListView;
    private Button buttonVoltar;
    ArrayList<String> listAlimentos = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_refeicao);

        //ids XML
        txtNome = (TextView) findViewById(R.id.textNome);
        txtTipo = (TextView) findViewById(R.id.textTipo);
        txtUi = (TextView) findViewById(R.id.textUI);
        txtData = (TextView) findViewById(R.id.textData);
        alimentoListView = (ListView) findViewById(R.id.listView);
        buttonVoltar = (Button) findViewById(R.id.button);

        Bundle bDetalhe = getIntent().getExtras();

        if(bDetalhe!= null) {
            System.out.println("TESTE PARA BUNDLE NEWREFEICAO: " + bDetalhe.getString("nomeRef"));
            System.out.println("TESTE PARA BUNDLE HISTORICO: " + bDetalhe.getInt("posicao"));
            if(bDetalhe.getString("nomeRef") != null){
            nome = bDetalhe.getString("nomeRef");
            tipo = bDetalhe.getString("periodo");
            ui = bDetalhe.getDouble("calculo");
            listAlimentos = bDetalhe.getStringArrayList("alimento");
                data = bDetalhe.getString("data");
                Refeicao r = new Refeicao();
                r.setNome(nome);
                r.setData(data);
                r.setAlimentos(listAlimentos);
                System.out.println(listAlimentos);
                r.setPeriodo(tipo);
                r.setUi(ui);
                DBLocal db = new DBLocal(this);
                db.insertRefeicao(r);
                System.out.println("PROCESSO NO DB REALIZADO!");
            }else{
                int posicao = bDetalhe.getInt("posicao");
                DBLocal db = new DBLocal(this);
                nome = db.selectRefeicoes().get(posicao).getNome();
                tipo = db.selectRefeicoes().get(posicao).getPeriodo();
                ui = db.selectRefeicoes().get(posicao).getUi();
                data = db.selectRefeicoes().get(posicao).getData();
                listAlimentos = db.selectRefeicoes().get(posicao).getAlimentos();
            }
            txtNome.setText(nome);
            txtTipo.setText(tipo);
            txtData.setText(data);
            String convCalculo = String.valueOf(ui) + " UI";
            txtUi.setText(convCalculo);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listAlimentos);
            //Adapta os valores do listView baseado no ArrayList
            alimentoListView.setAdapter(arrayAdapter);
        }

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetalhesRefeicaoActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}
