package com.example.nerds.tcctest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    private TextView textNome, textPorcao, textCarb;
    public float calc; //Variável que armazenará o cálculo de carboidratos
    private Button botaoCancelar, botaoSalvar;
    private NumberPicker numPorcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);


        textNome = (TextView) findViewById(R.id.textNome_Calc);
        textPorcao = (TextView) findViewById(R.id.textPorcao_Calc);
        textCarb = (TextView) findViewById(R.id.textCarb_Calc);

        botaoCancelar = (Button) findViewById(R.id.botaoCancelar);
        botaoSalvar = (Button) findViewById(R.id.botaoSalvar);

        numPorcao = (NumberPicker) findViewById(R.id.numPorcao_Calc);

        /*Mínimo de porções para serem calculadas: 1
         Valor máximo decorativo: 20 - Analisar? */
        numPorcao.setMinValue(1);
        numPorcao.setMaxValue(20);

        numPorcao.setWrapSelectorWheel(true);

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

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Futuramente, aqui será apenas para a AlimentoActivity (assim que não der merda) */

                Bundle b = new Bundle();
                //Gravando mais um Bundle - Para uso da NewRefeicao (Activity)
                String nome = textNome.getText().toString();
                b.putString("nome", nome);
                //GAMBIARRA MASTER ABAIXO - De TextView para String para Float, sendo que a priori, é uma float mesmo
                try {
                    String conv = String.valueOf(textCarb);
                    float carb = Float.parseFloat(conv);
                    b.putFloat("carb", carb);
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                b.putInt("porcao", numPorcao.getValue());
                //Dessa forma, a tela NewRefeicao recebe os dados. Será que funciona?
                Intent i = new Intent(CalcActivity.this, NewRefeicao.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Quando clicado em salvar, o usuário retornará a tela Main, que no caso, seria a
                   de refeições*/
                //calc = calc()
                Intent i = new Intent(CalcActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    /* Método interno para cálculo das gramas de carboidrato baseado no NumberPicker*/

}
