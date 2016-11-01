package com.example.nerds.tcctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    private TextView textNome, textPorcao, textCarb;
    public double calc; //Variável que armazenará o cálculo de carboidratos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);


        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle(R.string.tbTitle);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);



        //Para conseguir as informações para RECEBER A POSIÇÃO CLICADA, é necessário:
        Bundle bMain = getIntent().getExtras();

        if(bMain != null) {
            /*Caso consiga encontrar algo na Bundle, ou seja, se for derivada de um click em um
            item, conseguimos a posição no item que foi clicado*/
            int posicao = bMain.getInt("posicao");
            DBLocal bd = new DBLocal(this);

            //Atribue os valores relativos a posição
            textNome.setText(bd.selectAlimentos().get(posicao).getNome());
            textPorcao.setText(bd.selectAlimentos().get(posicao).getPorcao());
            //Sistema extenso de conversão Float para String
            textCarb.setText(String.valueOf(bd.selectAlimentos().get(posicao).getgCarb()));

            //Adiciona o valor de carboidratos da refeição.
            calc = calc + bd.selectAlimentos().get(posicao).getgCarb();

        }

    }
}
