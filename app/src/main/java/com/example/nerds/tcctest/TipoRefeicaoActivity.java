package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by Wallace on 26/10/2016.
 */

public class TipoRefeicaoActivity extends AppCompatActivity {

    private Button botaoManha, botaoTarde, botaoNoite, botaoVoltar;
    public String valor; //valor atribuido ao botão para ser repassado

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_refeicao);

        //Associando os botões do layout
        botaoManha = (Button) findViewById(R.id.button_Manha);
        botaoTarde = (Button) findViewById(R.id.button_Tarde);
        botaoNoite = (Button) findViewById(R.id.button_Noite);
        botaoVoltar = (Button) findViewById(R.id.button_Voltar);


        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle("Nova Refeição");
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        //Trabalhando com Listener e setando valor:
        botaoManha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            bundleNewRefeicao("Manhã");
            }
        });

        botaoTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            bundleNewRefeicao("Tarde");
            }
        });

        botaoNoite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleNewRefeicao("Noite");
            }
        });

    }
    //Facilitando com o serviço método auxiliar
    private void bundleNewRefeicao(String valor){
        this.valor = valor;
        Bundle bTipo = new Bundle();
        bTipo.putString("periodo", valor);
        Intent i = new Intent(TipoRefeicaoActivity.this, NewRefeicao.class);
        //Atribuindo o nome de variável a Bundle
        i.putExtra("bTipo", bTipo);
        startActivity(i);
    }


}
