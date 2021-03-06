package com.example.nerds.tcctest;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_results_toolbar);
        setSupportActionBar(my_toolbar);

        Intent searchIntent = getIntent();

        String query = new String();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){

            query = searchIntent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            Toast.makeText(SearchResultsActivity.this, query,Toast.LENGTH_SHORT).show();
        }

        //Sincronizando - Usando BDLocal do projeto


        ArrayList<String> searchResults = new ArrayList<String>();
        /* Criando um BD para sincronizar a busca*/
        DBLocal bd = new DBLocal(this);
        for(int i = 0; i < bd.searchAlimentos(query).size(); i++){
            String pesquisa = bd.searchAlimentos(query).get(i).getNome();
            if(pesquisa.toLowerCase().contains(query.toLowerCase()))
                searchResults.add(pesquisa);
        }

        ListView listView = (ListView) findViewById(R.id.listView_searchResults);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults);
        listView.setAdapter(adapter);
    }
}
