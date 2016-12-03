package com.example.nerds.tcctest;

/**
 * Created by camargo on 17/10/2016.
 */
public class Usuario {
    //Dados inseridos pelo usuário na primeira vez que ele entrar no app; (editável?)
    private double sensM, sensT, sensN;
    private double CHOuiM, CHOuiT, CHOuiN;
    private int metaGlicemica;

    public double getSensM() {
        return sensM;
    }

    public double getSensT() {
        return sensT;
    }

    public double getSensN() {
        return sensN;
    }

    public double getCHOuiM(){
        return CHOuiM;
    }

    public double getCHOuiT() {
        return CHOuiT;
    }

    public double getCHOuiN() {
        return CHOuiN;
    }

    public int getMetaGlicemica(){
        return metaGlicemica;
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

    public void setCHOuiM(double CHOuiM) {
        this.CHOuiM = CHOuiM;
    }

    public void setCHOuiT(double CHOuiT) {
        this.CHOuiT = CHOuiT;
    }

    public void setCHOuiN(double CHOuiN) {
        this.CHOuiN = CHOuiN;
    }

    public void setMetaGlicemica(int metaGlicemica){
        this.metaGlicemica = metaGlicemica;
    }
}
