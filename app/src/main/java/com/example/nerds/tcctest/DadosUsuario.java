package com.example.nerds.tcctest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nerds on 03/12/2016.
 */

public class DadosUsuario extends SQLiteOpenHelper {

    private static final String DB = "mydb";
    //Tabela Usuário
    private static final String TABLE = "usuario";
    private static final String TAG = "DadosUsuario";

    private static final String FATORM = "fatorSensiM";
    private static final String FATORT = "fatorSensiT";
    private static final String FATORN = "fatorSensiN";

    private static final String CHOUIM = "chouiM";
    private static final String CHOUIT = "chouiT";
    private static final String CHOUIN = "chouiN";

    private static final String META = "metaGlicemica";

    //Antes tinha aqui um tbUsuario, mas é desnecessário

    public DadosUsuario(Context context) {
        /* Normalmente, os parâmetros são context, name, factory e version.
        * Porém, nesse caso, só será necessário usar o context como parâmetro, sendo assim:*/
        super(context, DB, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Usando o valor das constantes

        //tbAlimento
        db.execSQL("CREATE TABLE " + TABLE + " (" + FATORM + " REAL " + FATORT + " REAL "
                + FATORN + " REAL " + CHOUIM + " REAL " + CHOUIT + " REAL " + CHOUIN + " REAL "
                + META + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUsuario(Usuario usuario) {

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            //Tenta criar valores baseado no objeto Alimento
            contentValues.put(FATORM, usuario.getSensM());
            contentValues.put(FATORT, usuario.getSensT());
            contentValues.put(FATORN, usuario.getSensN());

            contentValues.put(CHOUIM, usuario.getCHOuiM());
            contentValues.put(CHOUIT, usuario.getCHOuiT());
            contentValues.put(CHOUIN, usuario.getCHOuiN());

            contentValues.put(META, usuario.getMetaGlicemica());
            bd.insert(TABLE, null, contentValues); //Na ordem: tabela, TableHack (?), valores a serem add
            return true;
        } catch (Exception e) {
            Log.e(TAG, "insertAlimento: " + e.getMessage());
            return false;
        }

    }

    public ArrayList<Usuario> selectUsuario() {

        SQLiteDatabase bd = this.getReadableDatabase();
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        System.out.println(lista.size());
        try {
            Cursor cur = bd.rawQuery("SELECT * FROM " + TABLE, null);
            //Pega o primeiro resultado
            cur.moveToFirst();
            //Enquanto não for o último dado
            while (cur.isAfterLast() == false) {
                //Cria um objeto para ser inserido na ArrayList
                Usuario u = new Usuario();
            /* Seta de acordo com o cursor*/
                u.setSensM(cur.getFloat(cur.getColumnIndex(FATORM)));
                u.setSensT(cur.getFloat(cur.getColumnIndex(FATORT)));
                u.setSensN(cur.getFloat(cur.getColumnIndex(FATORN)));

                u.setCHOuiM(cur.getFloat(cur.getColumnIndex(CHOUIM)));
                u.setCHOuiT(cur.getFloat(cur.getColumnIndex(CHOUIT)));
                u.setCHOuiN(cur.getFloat(cur.getColumnIndex(CHOUIN)));

                u.setMetaGlicemica(cur.getInt(cur.getColumnIndex(META)));
                lista.add(u);
                cur.moveToNext(); //Após o fim das informações, move-se à próxima
            }
            return lista;
        } catch (Exception e) {
            Log.e(TAG, "selectAlimentos: " + e.getMessage());
            return lista;
        }
    }
}
