package com.example.nerds.tcctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nerds on 03/11/2016.
 */

public class SelecaoActivity extends AppCompatActivity {
    private Button btnTACO, btnMeus; //Opções disponíveis por botão.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao);

        btnTACO = (Button) findViewById(R.id.slc_btnTACO);
        btnMeus = (Button) findViewById(R.id.slc_btnMeus);

        final Bundle bNew = getIntent().getExtras();

        btnTACO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMeus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre MeusAlimentos - No momento, chama-se MainActivity
                Intent i = new Intent(SelecaoActivity.this, MeusAlimentos.class);
                i.putExtras(bNew);
                startActivity(i);
            }
        });
    }
}
