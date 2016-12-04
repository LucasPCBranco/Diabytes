package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nerds on 02/11/2016.
 */

public class HistoricoMainActivity extends AppCompatActivity{
    /* Esse é o verdadeiro MainActivity. Em breve, será transformado de fato na tela principal do app*/

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_alimentos);
        //Está como ListView_alimentos, porque o layout é (ATÉ SEGUNDA ORDEM) o mesmo
        listView = (ListView) findViewById(R.id.listView_alimentos);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Baseado na posição em i do Banco de Dados no onResume(), chegamos a:
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cria um Bundle que salva as info's da posição para ser enviado para outra Activity
                //A posição passada como parâmetro é atribuído ao Bundle
                Bundle b = new Bundle();
                b.putInt("posicao", position);

                //Cria a transição entre a MainActivity com a tela de transição (TESTE COM nova)
                Intent i = new Intent(HistoricoMainActivity.this, DetalhesRefeicaoActivity.class);
                //Insere no intent o Bundle
                i.putExtra("bHistorico", b);

                //Chama a nova Activity (A MUDAR)
                startActivity(i);

            }
        });
    }

    protected void onResume() {

        /* ESSA PARTE ABAIXO IRÁ PERTENCER A OUTRA ACTIVITY EM BREVE.*/
        super.onResume();
        //Chamará o recurso do DB (insert, select, etc)
        DBLocal bd = new DBLocal(this);
        //Criação de uma ArrayList do SELECT
        ArrayList<String> listaRefeicao = new ArrayList<String>();
        //Usando for, vai criando uma posição ArrayList para cada Select presente
        for (int i = 0; i < bd.selectRefeicoes().size(); i++) {
            if(bd.selectRefeicoes().size() > 0){
                listaRefeicao.add(bd.selectRefeicoes().get(i).getNome());
                //Irá adaptar a entrada de um item na listView através de um Array
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaRefeicao);
                //Adapta os valores do listView baseado no ArrayList
                listView.setAdapter(arrayAdapter);
            }else {

            }
        }
    }
}
