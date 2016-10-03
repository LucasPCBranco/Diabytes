package com.example.nerds.tcctest;

/**
 * Created by Nerds on 26/09/2016.
 */

public class Alimento {
    //Atributos básicos do alimento
    private int id;
    private String nome, porcao;
    private float gCarb; //Carboidratos em gramas

    //GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPorcao() {
        return porcao;
    }

    public void setPorcao(String porcao) {
        this.porcao = porcao;
    }

    public float getgCarb() {
        return gCarb;
    }

    public void setgCarb(float gCarb) {
        this.gCarb = gCarb;
    }




}
