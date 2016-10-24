package com.example.nerds.tcctest;

/**
 * Created by camargo on 17/10/2016.
 */
public class Usuario {
    //Dados inseridos pelo usuário na primeira vez que ele entrar no app; (editável?)
    private int id;
    private double sensM, sensT, sensN;

    public int getId() {
        return id;
    }

    public double getSensM() {
        return sensM;
    }

    public double getSensT() {
        return sensT;
    }

    public double getSensN() {
        return sensN;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSensM(double sensM) {
        this.sensM = sensM;
    }

    public void setSensT(double sensT) {
        this.sensT = sensT;
    }

    public void setSensN(double sensN) {
        this.sensN = sensN;
    }
}
