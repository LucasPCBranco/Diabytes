package com.example.nerds.tcctest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Refeicao {
    //Classe Refeicao: armazena tipo de refeição, alimentos envolvidos (?) e horário da refeição, além da insulina.
    private String tipo;
    private ArrayList<Alimento> alimentos; //Isso aqui pode dar ruim no futuro
    private TimeUnit hora; //TimeUnit, Time
    private float glicemia; //Glicemia Atual
    private double ui;

    //Importação classe PrimeiroAcesso
    FirstAccess configi = new FirstAccess();

    /* GETTERS E SETTERS*/

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getUi() {
        return ui;
    }

    public ArrayList<Alimento> getAlimentos() {
        return alimentos;
    }

    /* Set diferente - Adiciona o alimento necessário para EXIBIÇÃO*/
    public void setAlimentos(Alimento alimento) {
        this.alimentos.add(alimento);
    }

    public TimeUnit getHora() {
        return hora;
    }
    public void setHora(TimeUnit hora) {
        this.hora = hora;
    }

    public float getGlicemia() { return glicemia; }
    public void setGlicemia(float glicemia) { this.glicemia = glicemia; }

    public void calculoCarb(){

    }

    public int calculoGlicemia(int atual, int meta){
        return (meta - atual) /* / fatorSensibilidade*/;
    }



    /* Ex- "getUi", vai englobar todos os cálculos relacionados a refeição
    public void calculoUi(double ui) {
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

        /* Criar código adequado*/

    }



    /* Método para cálculo dos carboidratods. Explicando a lógica:
       O usuário irá selecionar os Alimentos, que no caso, estão concentrados na ArrayList<Alimentos>
       Se esses dados forem devidamente pegos, dá para usar um cálculo simples de multiplicação
}*/
