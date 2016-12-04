package com.example.nerds.tcctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetalhesRefeicaoActivity extends AppCompatActivity {

    private TextView textNome;
    private String nome, tipo;
    private float ui;
    private TextView txtNome, txtTipo, txtUi;
    private ListView alimentoListView;
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

        Bundle bDetalhe = getIntent().getExtras();

        if(bDetalhe!= null) {
            txtNome.setText(bDetalhe.getString("nomeRef"));
            txtTipo.setText(bDetalhe.getString("periodo"));
            String convCalculo = String.valueOf(bDetalhe.getDouble("calculo"));
            txtUi.setText(convCalculo);
            listAlimentos = bDetalhe.getStringArrayList("alimento");
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listAlimentos);
            //Adapta os valores do listView baseado no ArrayList
            alimentoListView.setAdapter(arrayAdapter);
        }

    }
}
