package com.example.nerds.tcctest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //Chamando o ListView que servirá como base
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Atribui o valor do listView ao que será exibido na tela
        listView = (ListView) findViewById(R.id.listView_alimentos);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Baseado na posição em i do Banco de Dados no onResume(), chegamos a:
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cria um Bundle que salva as info's da posição para ser enviado para outra Activity
                Bundle b = new Bundle();
                /* A posição passada como parâmetro é atribuído ao Bundle*/
                b.putInt("posicao", position);

                //Cria a transição entre a MainActivity com a tela de transição (A MUDAR)
                Intent i = new Intent(MainActivity.this, NewAlimentoActivity.class);
                //Insere no intent o Bundle
                i.putExtras(b);

                //Chama a nova Activity (A MUDAR)
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu); //Insere menu_new ao Menu da Activity principal

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Verifica qual a opção do menu que foi selecionado
        switch(item.getItemId()) {
            //Usamos como referência o id do item, através de um switch/case
            case R.id.newAlimento:
                startActivity(new Intent(MainActivity.this, NewAlimentoActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        //Chamará o recurso do DB (insert, select, etc)
        DBLocal bd = new DBLocal(this);
        //Criação de uma ArrayList do SELECT
        ArrayList<String> listaAlimentos = new ArrayList<String>();
        //Usando for, vai criando uma posição ArrayList para cada Select presente
            for (int i = 0; i < bd.selectAlimentos().size(); i++)
                listaAlimentos.add(bd.selectAlimentos().get(i).getNome());
        //Irá adaptar a entrada de um item na listView através de um Array
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAlimentos);
        //Adapta os valores do listView baseado no ArrayList
        listView.setAdapter(arrayAdapter);
        super.onResume();
    }
}
