package com.example.nerds.tcctest;

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
    private String nome, tipo;
    private double ui;
    private TextView txtNome, txtTipo, txtUi;
    private ListView alimentoListView;
    private Button buttonVoltar, buttonExcluir;
    ArrayList<String> listAlimentos = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_refeicao);

        //ids XML
        txtNome = (TextView) findViewById(R.id.textNome);
        txtTipo = (TextView) findViewById(R.id.textTipo);
        txtUi = (TextView) findViewById(R.id.textUI);
        alimentoListView = (ListView) findViewById(R.id.listView);
        buttonVoltar = (Button) findViewById(R.id.button);
        buttonExcluir = (Button) findViewById(R.id.button2);

        Bundle bDetalhe = getIntent().getExtras();

        if(bDetalhe!= null) {
            nome = bDetalhe.getString("nomeRef");
            tipo = bDetalhe.getString("periodo");
            ui = bDetalhe.getDouble("calculo");
            listAlimentos = bDetalhe.getStringArrayList("alimento");

            txtNome.setText(nome);
            txtTipo.setText(tipo);
            String convCalculo = String.valueOf(ui);
            txtUi.setText(convCalculo);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listAlimentos);
            //Adapta os valores do listView baseado no ArrayList
            alimentoListView.setAdapter(arrayAdapter);
        }

        final String nomeB = nome;
        final String dataB = bDetalhe.getString("data");
        final ArrayList<String> listB = listAlimentos;
        final String periodo = tipo;
        final double resultado = ui;

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBLocal db = new DBLocal(DetalhesRefeicaoActivity.this);
                Refeicao r = new Refeicao();
                r.setNome(nomeB);
                r.setData(dataB);
                r.setAlimentos(listB);
                r.setPeriodo(periodo);
                r.setUi(resultado);
                db.insertRefeicao(r);
            }
        });

    }

}
