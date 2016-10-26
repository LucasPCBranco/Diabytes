package com.example.nerds.tcctest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //Chamando o ListView que servirá como base
    public ListView listView;
    public boolean firstAcess;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted =  false;
        //Se for 1º acesso, abre a activity para configurações iniciais
        /* if(!previouslyStarted){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(String.valueOf(previouslyStarted), Boolean.TRUE);
            edit.commit();
            Intent firstconf = new Intent(MainActivity.this, FirstAccess.class);
            startActivity(firstconf);
        } */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle(R.string.tbTitle);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);




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

                //Cria a transição entre a MainActivity com a tela de transição (TESTE COM nova)
                Intent i = new Intent(MainActivity.this, CalcActivity.class);
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

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

            //case R.id.menu_1:


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
