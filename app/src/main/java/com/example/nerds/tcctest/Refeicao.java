package com.example.nerds.tcctest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Refeicao {
    //Classe Refeicao: armazena tipo de refeição, alimentos envolvidos (?) e horário da refeição, além da insulina.
    private String tipo;
    private ArrayList<Alimento> alimentos; //Isso aqui pode dar ruim no futuro
    private TimeUnit hora; //TimeUnit, Time
    private double ui;
    private float glicemia;

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

    public float getGlicemia() { return glicemia; }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /* Set diferente - Adiciona o alimento necessário para EXIBIÇÃO*/
    public void setAlimentos(Alimento alimento) {
        this.alimentos.add(alimento);
    }

    public void setHora(TimeUnit hora) {
        this.hora = hora;
    }

    public void setUi(double ui) {
        this.ui = ui;
    }

    public void setGlicemia(float glicemia) { this.glicemia = glicemia; }

    /* Método para cálculo dos carboidratods. Explicando a lógica:
       O usuário irá selecionar os Alimentos, que no caso, estão concentrados na ArrayList<Alimentos>
       Se esses dados forem devidamente pegos, dá para usar um cálculo simples de multiplicação*/
}
