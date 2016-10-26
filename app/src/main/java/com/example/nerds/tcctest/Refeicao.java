package com.example.nerds.tcctest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Refeicao {
    //Classe Refeicao: armazena tipo de refeição, alimentos envolvidos (?) e horário da refeição, além da insulina.
    private String tipo;
    private ArrayList<Alimento> alimentos; //Isso aqui pode dar ruim no futuro
    private TimeUnit hora; //TimeUnit, Time
    private double ui;

    /* GETTERS E SETTERS*/
    public String getTipo() {
        return tipo;
    }

    public double getUi() {
        return ui;
    }

    public ArrayList<Alimento> getAlimentos() {
        return alimentos;
    }

    public TimeUnit getHora() {
        return hora;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /* Set diferente - Adiciona o alimento necessário*/
    public void setAlimentos(Alimento alimento) {
        this.alimentos.add(alimento);
    }

    public void setHora(TimeUnit hora) {
        this.hora = hora;
    }

    public void setUi(double ui) {
        this.ui = ui;
    }
}
