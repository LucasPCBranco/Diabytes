package com.example.nerds.tcctest;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Refeicao {
    //Classe Refeicao: armazena tipo de refeição, alimentos envolvidos (?) e horário da refeição, além da insulina.
    private String nome, periodo, data; //Futuro? "Data" será mais preciso.
    private ArrayList<Alimento> alimentos; //Isso aqui pode dar ruim no futuro
    private double ui;


    /* GETTERS E SETTERS */

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public double getUi() { return ui; }
    public void setUi(double ui){
        this.ui = ui;
    }

    public ArrayList<Alimento> getAlimentos() { return alimentos; }
    /* Set diferente - Adiciona o alimento necessário para EXIBIÇÃO*/
    public void setAlimentos(ArrayList<Alimento> alimento) { this.alimentos = alimento; }

    public String getData() { return data; }
    public void setData (String data) { this.data = data; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

}



