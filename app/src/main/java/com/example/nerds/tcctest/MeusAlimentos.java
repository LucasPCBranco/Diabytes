package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nerds on 02/11/2016.
 */

public class MeusAlimentos extends AppCompatActivity{
    /* Esse é o verdadeiro MainActivity. Em breve, será transformado de fato na tela principal do app*/

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Verifica se é ou não a primeira vez que o app abriu. Se for, abre a outra intent*/

        setContentView(R.layout.activity_meus_alimentos);

        //session.verificaAcesso();

        //Definindo a toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        //Titulo e icone que fica na toolbar
        getSupportActionBar().setTitle(R.string.tbTitle);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        final Bundle b = getIntent().getExtras();

        //Atribui o valor do listView ao que será exibido na tela
        listView = (ListView) findViewById(R.id.listView_alimentos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Baseado na posição em i do Banco de Dados no onResume(), chegamos a:
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cria um Bundle que salva as info's da posição para ser enviado para outra Activity
                //A posição passada como parâmetro é atribuído ao Bundle
                b.putInt("posicao", position);

                //Cria a transição entre a MainActivity com a tela de transição (TESTE COM nova)
                Intent i = new Intent(MeusAlimentos.this, CalcActivity.class);
                //Insere no intent o Bundle
                i.putExtras(b);

                //Chama a nova Activity (A MUDAR)
                startActivity(i);

            }
        });
    }
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
                startActivity(new Intent(MeusAlimentos.this, NewAlimentoActivity.class));
                break;

            case R.id.newAlimento:
                //Novo Alimento
                Intent i = new Intent(MeusAlimentos.this, NewAlimentoActivity.class);
                startActivity(i);
                break;

            case R.id.menu_2:
                Intent iD = new Intent(MeusAlimentos.this, ConfiguracoesActivity.class);
                startActivity(iD);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        /* ESSA PARTE ABAIXO IRÁ PERTENCER A OUTRA ACTIVITY EM BREVE.*/
        super.onResume();
        //Chamará o recurso do DB (insert, select, etc)
        DBLocal bd = new DBLocal(this);
        //Criação de uma ArrayList do SELECT
        ArrayList<String> listaAlimentos = new ArrayList<String>();
        //Usando for, vai criando uma posição ArrayList para cada Select presente
        for (int i = 0; i < bd.selectAlimentos().size(); i++) {
            if(bd.selectAlimentos().size() > 0){
                listaAlimentos.add(bd.selectAlimentos().get(i).getNome());
                //Irá adaptar a entrada de um item na listView através de um Array
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAlimentos);
                //Adapta os valores do listView baseado no ArrayList
                listView.setAdapter(arrayAdapter);
            }else {

            }
        }
    }

}
