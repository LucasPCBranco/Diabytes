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
    private EditText editgPorcao;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alimento);

        editName = (EditText) findViewById(R.id.editName);
        editPorcao = (EditText) findViewById(R.id.editPorcao);
        editCarb = (EditText) findViewById(R.id.editCarb);
        editgPorcao = (EditText) findViewById(R.id.editgPorcao);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlimento();
            }
        });

        /* Tinha um código aqui que foi transferido para a CalcActivity*/
    }

    //Método para internamento fazer o salvamento das informações da tela
    private void salvarAlimento() {

        DBLocal bd = new DBLocal(this); //Instância para chamar novamente o BD que salvará as infos
        Alimento novo = new Alimento(); //Criando novo alimento
        novo.setNome(editName.getText().toString());
        novo.setPorcao(editPorcao.getText().toString());

        //Nos casos específico do carboidrato e da porção, faremos uma conversão para Float;
        float carb = Float.valueOf(editCarb.getText().toString());
        novo.setgCarb(carb);

        float porcao = Float.valueOf(editgPorcao.getText().toString());
        novo.setgPorcao(porcao);

        //Insere o Aliemnto no BD
        bd.insertAlimento(novo);
        finish();
    }
}
