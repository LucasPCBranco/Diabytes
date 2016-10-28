package com.example.nerds.tcctest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;

import java.util.HashMap;

public class SessionManager {
    //Classe para conseguir verificar se é ou não a primeira vez que o app está sendo rodada

    //Constantes
    private static final String PREF_NAME = "DiabytesPref";
    private static final String IS_CONFIG = "IsConfig"; //Questiona se já fora configurado

    public static final String SENS_M = "fatorSensM";
    public static final String SENS_T = "fatorSensT";
    public static final String SENS_N = "fatorSensN";

    public static final String CHOPORUI_M = "UIporCHOm";
    public static final String CHOPORUI_T = "UIporCHOt";
    public static final String CHOPORUI_N = "UIporCHOn";




    // Atributos
    SharedPreferences pref;
    Editor editor;
    Context contexto; //Chamado assim para não haver conflito

    public SessionManager(Context context){
        this.contexto = context;
        pref = contexto.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void salvarDados(float sensM, float sensT, float sensN,
                            float chouiM, float chouiT, float chouiN){
        //Atribuíndo valores ao Editor

        editor.putBoolean(IS_CONFIG, true);

        editor.putFloat(SENS_M, sensM);
        editor.putFloat(SENS_T, sensT);
        editor.putFloat(SENS_N, sensN);
        editor.putFloat(CHOPORUI_M, chouiM);
        editor.putFloat(CHOPORUI_T, chouiT);
        editor.putFloat(CHOPORUI_N, chouiN);

        editor.commit();
    }

    public HashMap<String, Float> getInfo(){
        HashMap<String, Float> user = new HashMap<String, Float>();

        user.put(SENS_M, pref.getFloat(SENS_M, 0));
        user.put(SENS_T, pref.getFloat(SENS_T, 0));
        user.put(SENS_N, pref.getFloat(SENS_N, 0));

        user.put(CHOPORUI_M, pref.getFloat(CHOPORUI_M, 0));
        user.put(CHOPORUI_T, pref.getFloat(CHOPORUI_T, 0));
        user.put(CHOPORUI_N, pref.getFloat(CHOPORUI_N, 0));

        return user;
    }


}
