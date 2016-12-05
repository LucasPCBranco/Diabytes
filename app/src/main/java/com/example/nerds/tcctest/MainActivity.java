package com.example.nerds.tcctest;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Chamando o ListView que servirá como base
    public ListView listView;

    //Puxando a classe SessionManager
    private SessionManager sessionManager;

    //Tempos de conexão, puxando adaptador
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVAlimento;
    private AdapterAlimento mAdapter;

    SearchView searchView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        if(sessionManager.isConfig()){
            abrirPrimeiroAcesso();
            finish();
        }else{
        setContentView(R.layout.activity_refeicoes);

        //Setando a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle(R.string.tbTitle);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        //Está como ListView_alimentos, porque o layout é (ATÉ SEGUNDA ORDEM) o mesmo
        listView = (ListView) findViewById(R.id.listView_refeicoes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Baseado na posição em i do Banco de Dados no onResume(), chegamos a:
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cria um Bundle que salva as info's da posição para ser enviado para outra Activity
                //A posição passada como parâmetro é atribuído ao Bundle
                Bundle b = new Bundle();
                b.putInt("posicao", position);
                //Cria a transição entre as telas
                Intent i = new Intent(MainActivity.this, DetalhesRefeicaoActivity.class);
                //Insere no intent o Bundle
                i.putExtras(b);
                //Chama a nova Activity (A MUDAR)
                startActivity(i);

            }
        });
    }
    }
    @Override


        /*Alterar para mRVAlimentos (RecyclerView) - corrigir erro do setOnItemClickListener no RecyclerView */

    //EDIT Colocar esse método em comentários para utilizar da ATPSNA para testar outras telas diretamente

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Verifica qual a opção do menu que foi selecionado
        switch(item.getItemId()) {
            //Usamos como referência o id do item, através de um switch/case
            case R.id.menu_1:
                /* Novo Alimento*/
                startActivity(new Intent(MainActivity.this, NewAlimentoActivity.class));
                break;

            case R.id.newAlimento:
                //Nova Refeição
                startActivity(new Intent(MainActivity.this, TipoRefeicaoActivity.class));
                break;

            case R.id.menu_2:
                //Configurações oficial
                startActivity(new Intent(MainActivity.this, ConfiguracoesActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query and create object of class AsyncFetch
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();

        }
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                //COLOCAR ARQUIVOS .php DENTRO DA PASTA diabytes EM c:\xampp\htdocs
                url = new URL("http://192.168.78.1/diabytes/db-search.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // add parameter to our above url
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("searchQuery", searchQuery);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<Alimento> data = new ArrayList<>();

            pdLoading.dismiss();
            if (result.equals("no rows")) {
                Toast.makeText(MainActivity.this, "No Results found for entered query", Toast.LENGTH_LONG).show();
            } else {

                try {

                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        Alimento InfoAlimentos = new Alimento();
                        InfoAlimentos.setNome(json_data.getString("NAlimento"));
                        InfoAlimentos.setPorcao(json_data.getString("PAlimento"));
                        InfoAlimentos.setgPorcao(json_data.getInt("VPorcao"));
                        InfoAlimentos.setgCarb(Float.valueOf(json_data.getString("GramaCarb")));
                        data.add(InfoAlimentos);
                    }

                    // Setup and Handover data to recyclerview
                    mRVAlimento = (RecyclerView) findViewById(R.id.fishPriceList);
                    mAdapter = new AdapterAlimento(MainActivity.this, data);
                    mRVAlimento.setAdapter(mAdapter);
                    mRVAlimento.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }
    }
    protected void onResume() {
        super.onResume();
        System.out.println("onResume() no HISTORICO utilizado");
        //Chamará o recurso do DB (insert, select, etc)
        DBLocal bd = new DBLocal(this);
        //Criação de uma ArrayList do SELECT
        ArrayList<String> listaRefeicao = new ArrayList<String>();
        //Usando for, vai criando uma posição ArrayList para cada Select presente
        for (int i = 0; i < bd.selectRefeicoes().size(); i++) {
            if(bd.selectRefeicoes().size() > 0){
                System.out.println("LISTINHA DO SUCESSO: " + bd.selectRefeicoes().get(i).getNome());
                listaRefeicao.add(bd.selectRefeicoes().get(i).getNome());
                //Irá adaptar a entrada de um item na listView através de um Array
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaRefeicao);
                //Adapta os valores do listView baseado no ArrayList
                listView.setAdapter(arrayAdapter);
            }else {
                System.out.println("As refeições estão em branco :(");
            }
        }
    }


    //Método para abrir a tela de primeiro acesso, caso necessário
    private void abrirPrimeiroAcesso(){
        sessionManager.setConfig(false); //Torna a opção de primeira vez FALSE, e então, não abrirá essa tela;
        Intent i = new Intent(MainActivity.this, FirstAccess.class);
        startActivity(i);
        finish();
    }


}
