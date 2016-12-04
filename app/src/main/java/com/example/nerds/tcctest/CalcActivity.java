package com.example.nerds.tcctest;

import android.content.Intent;
import android.support.v4.app.NavUtils;
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
        final Bundle bMain = getIntent().getExtras();

        if(bMain != null) {
            /*Caso consiga encontrar algo na Bundle, ou seja, se for derivada de um click em um
            item, conseguimos a posição no item que foi clicado*/
            int posicao = bMain.getInt("posicao");
            DBLocal bd = new DBLocal(this);
            //Atribue os valores relativos a posição
            textNome.setText(bd.selectAlimentos().get(posicao).getNome());
            textPorcao.setText(bd.selectAlimentos().get(posicao).getPorcao());
            //Sistema extenso de conversão Float para String
            calc = bd.selectAlimentos().get(posicao).getgCarb();
            textCarb.setText(String.valueOf(calc));
        }

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Futuramente, aqui será apenas para a AlimentoActivity (assim que não der merda) */

                //Gravando mais um Bundle - Para uso da NewRefeicao (Activity)
                String nome = textNome.getText().toString();
                bMain.putString("nomeAli", nome);
                //GAMBIARRA MASTER ABAIXO - De TextView para String para Float, sendo que a priori, é uma float mesmo
                try {
                    bMain.putFloat("carb", calc);
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                bMain.putInt("porcao", numPorcao.getValue());

                Intent i = new Intent(CalcActivity.this, NewRefeicao.class);
                //Dessa forma, a tela NewRefeicao recebe os dados. Será que funciona?
                //Intent i = NavUtils.getParentActivityIntent(CalcActivity.this);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //NavUtils.navigateUpTo(CalcActivity.this, i);
                i.setFlags(Intent.FILL_IN_DATA);
                i.putExtra("bCalc", bMain);
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
