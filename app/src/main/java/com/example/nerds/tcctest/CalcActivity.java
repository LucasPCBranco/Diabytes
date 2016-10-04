package com.example.nerds.tcctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    private TextView textNome, textPorcao, textCarb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        textNome = (TextView) findViewById(R.id.textNome);
        textPorcao = (TextView) findViewById(R.id.textPorcao);
        textCarb = (TextView) findViewById(R.id.textCarb);
        
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


        }

    }
}
