package com.example.nerds.tcctest;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Refeicao {
    //Classe Refeicao: armazena tipo de refeição, alimentos envolvidos (?) e horário da refeição, além da insulina.
    private String tipo;
    private ArrayList<Alimento> alimentos; //Isso aqui pode dar ruim no futuro
    private TimeUnit hora; //TimeUnit, Time
    private float glicemia; //Glicemia Atual
    private double ui;

    //Importação classe PrimeiroAcesso e TipoRefeicao
    FirstAccess configi = new FirstAccess();
    TipoRefeicaoActivity periodo = new TipoRefeicaoActivity();

    /* GETTERS E SETTERS */

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getUi() { return ui; }

    public ArrayList<Alimento> getAlimentos() { return alimentos; }
    /* Set diferente - Adiciona o alimento necessário para EXIBIÇÃO*/
    public void setAlimentos(Alimento alimento) { this.alimentos.add(alimento); }

    public TimeUnit getHora() { return hora; }
    public void setHora(TimeUnit hora) { this.hora = hora; }

    public float getGlicemia() { return glicemia; }
    public void setGlicemia(float glicemia) { this.glicemia = glicemia; }

    public void calculoCarb(){

    }

  /* public int calculoGlicemia(int atual, int meta){
        return (meta - atual) /* / fatorSensibilidade*/;
//}
    /*
    public int calculoCarboidratos(double carboidratos) {
        return carboidratos / fatorCHOporUi;
    } */

  /*  public void calculoUi(double ui) {
        return calculoGlicemia() + calculoCarboidratos();
    }
    */


    public double calculoUi(int glicAtual, int glicMeta, double carboidratos) {

        String per = periodo.valor;
        float fatorSensi;
        float fatorCHOporUi;


        if (per.equals("Manhã")){
            fatorSensi = configi.sensiM;
        }
        if (per.equals("Tarde")){
            fatorSensi = configi.sensiT;
        }
        if (per.equals("Noite")){
            fatorSensi = configi.sensiN;
        }
        else {
            fatorSensi = 1;
        }

        if (per.equals("Manhã")){
            fatorCHOporUi = configi.sensiM;
        }
        if (per.equals("Tarde")){
            fatorCHOporUi = configi.sensiT;
        }
        if (per.equals("Noite")){
            fatorCHOporUi = configi.sensiN;
        }
        else {
            fatorCHOporUi = 1;
        }

        float A = (glicMeta - glicAtual) / fatorSensi;
        double B = carboidratos / fatorCHOporUi;
        ui = A + B;
        return ui;
    }

    /* Ex- "getUi", vai englobar todos os cálculos relacionados a refeição*/
    //public void calculoUi(double ui) {
        //A = (Glicemia Atual - Glicemia Alvo) / fatorSensi
        //B = Carboidratos / fatorCHOporUI
        //ui = A + B;

        //Usuario escolhe Período -> M/T/N

        //A =
        //fatorSensi = configi.sensiM / configi.sensiT / configi.sensiN
        //Glicemia Alvo = configi.metaglicemia
        //Glicemia Atual = glicemia

        //B =
        //Carboidratos = Soma dos valores de todos os alimentos inseridos no ArrayList 'alimentos'
        //fatorCHOporUI = configi.uichoM / configi.uichoT / configi.uichoN

        //Insulina = A + B
        /* Criar código adequado*/

    }



    /* Método para cálculo dos carboidratods. Explicando a lógica:
       O usuário irá selecionar os Alimentos, que no caso, estão concentrados na ArrayList<Alimentos>
       Se esses dados forem devidamente pegos, dá para usar um cálculo simples de multiplicação*/

