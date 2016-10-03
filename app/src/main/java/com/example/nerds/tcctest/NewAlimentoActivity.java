package com.example.nerds.tcctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAlimentoActivity extends AppCompatActivity {
    //Atributos vindos do XML da tela
    private EditText editName;
    private EditText editPorcao;
    private EditText editCarb;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alimento);

        editName = (EditText) findViewById(R.id.editName);
        editPorcao = (EditText) findViewById(R.id.editPorcao);
        editCarb = (EditText) findViewById(R.id.editCarb);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlimento();
            }
        });

        //Para conseguir as informações para RECEBER A POSIÇÃO CLICADA, é necessário:
        Bundle bMain = getIntent().getExtras();

        if(bMain != null) {
            /*Caso consiga encontrar algo na Bundle, ou seja, se for derivada de um click em um
            item, conseguimos a posição no item que foi clicado*/
            int posicao = bMain.getInt("posicao");
            DBLocal bd = new DBLocal(this);

            //Atribue os valores relativos a posição
            editName.setText(bd.selectAlimentos().get(posicao).getNome());
            editPorcao.setText(bd.selectAlimentos().get(posicao).getPorcao());
            //Sistema extenso de conversão Float para String
            editCarb.setText(String.valueOf(bd.selectAlimentos().get(posicao).getgCarb()));

            //Por se tratar de apenas uma visualização, mantemos o botão salvar invisível
            buttonSalvar.setVisibility(View.INVISIBLE);

        }
    }

    //Método para internamento fazer o salvamento das informações da tela
    private void salvarAlimento() {

        DBLocal bd = new DBLocal(this); //Instância para chamar novamente o BD que salvará as infos
        Alimento novo = new Alimento(); //Criando novo alimento
        novo.setNome(editName.getText().toString());
        novo.setPorcao(editPorcao.getText().toString());

        //No caso específico do carboidrato, faremos uma conversão para Float;
        float carb = Float.valueOf(editCarb.getText().toString());
        novo.setgCarb(carb);

        //Insere o Aliemnto no BD
        bd.insertAlimento(novo);
        finish();
    }
}
