package com.example.nerds.tcctest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DBLocal extends SQLiteOpenHelper{

    private static final String DB = "mydb";

    private static final String TABLE_A = "alimento";
    private static final String TABLE_U = "usuario";
    private static final String TABLE_R = "refeicao";

    //Tabela Alimentos
    private static final String NOME = "nome";
    private static final String PORCAO = "porcao";
    private static final String CARB = "gcarb";
    private static final String PORC = "gPorcao";

    //Tabela Usuario
    private static final String FATORM = "fatorSensiM";
    private static final String FATORT = "fatorSensiT";
    private static final String FATORN = "fatorSensiN";

    private static final String CHOUIM = "chouiM";
    private static final String CHOUIT = "chouiT";
    private static final String CHOUIN = "chouiN";

    private static final String META = "metaGlicemica";

    //Tabela Refeição
    private static final String NOME_R = "nomeRef";
    private static final String DATA = "data";
    private static final String LIST = "stringAlimentos";
    private static final String PERIODO = "periodo";
    private static final String RESULTADO = "calculo";

    private static final String TAG_ALIMENTO = "Alimento";
    private static final String TAG_USUARIO = "Usuario";
    private static final String TAG_REFEICAO = "Refeicao";


    public DBLocal(Context context) {
        /* Normalmente, os parâmetros são context, name, factory e version.
        * Porém, nesse caso, só será necessário usar o context como parâmetro, sendo assim:*/
        super(context, DB, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Mais para fins de teste, eu concatenei os valores das tabelas.

        //tbAlimento
        db.execSQL("CREATE TABLE " + TABLE_A + "(id INTEGER PRIMARY KEY, nome TEXT, "
                +"porcao TEXT, gcarb REAL, gPorcao REAL) ");

        //tbUsuario
        db.execSQL("CREATE TABLE " + TABLE_U + " (" + FATORM + " REAL, " + FATORT + " REAL, "
                + FATORN + " REAL, " + CHOUIM + " REAL, " + CHOUIT + " REAL, " + CHOUIN + " REAL, "
                + META + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_R + " ( " + NOME_R + " TEXT, " + DATA + " TEXT," + LIST
         + " TEXT, " + PERIODO + " TEXT, "  + RESULTADO + " REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_U);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_A);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_R);
        onCreate(db);
    }

    public boolean insertAlimento (Alimento alimento){

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            //Tenta criar valores baseado no objeto Alimento
            contentValues.put(NOME, alimento.getNome());
            contentValues.put(PORCAO, alimento.getPorcao());
            contentValues.put(CARB, alimento.getgCarb());
            contentValues.put(PORC, alimento.getgPorcao());
            bd.insert(TABLE_A, null, contentValues); //Na ordem: tabela, TableHack (?), valores a serem add
            return true;
        }catch(Exception e){
            Log.e(TAG_ALIMENTO, "insertAlimento: " +e.getMessage());
            return false;
        }

    }

    public ArrayList<Alimento> selectAlimentos(){

        SQLiteDatabase bd = this.getReadableDatabase();
        ArrayList<Alimento> lista = new ArrayList<Alimento>();
        System.out.println(lista.size());
        try{
        Cursor cur = bd.rawQuery("SELECT * FROM " + TABLE_A, null);
        //Pega o primeiro resultado
        cur.moveToFirst();
        //Enquanto não for o último dado
        while(cur.isAfterLast() == false){
            //Informações sobre um alimento
            Alimento a = new Alimento();
            a.setId(cur.getInt(cur.getColumnIndex("id"))); //Pega o ID da coluna
            /* Seta de acordo com o cursor*/
            a.setNome(cur.getString(cur.getColumnIndex(NOME)));
            a.setPorcao(cur.getString(cur.getColumnIndex(PORCAO)));
            a.setgCarb(cur.getFloat(cur.getColumnIndex(CARB)));
            a.setgPorcao(cur.getFloat(cur.getColumnIndex(PORC)));
            lista.add(a);
            cur.moveToNext(); //Após o fim das informações, move-se à próxima
        }
        return lista;
    }catch(Exception e){
            Log.e(TAG_ALIMENTO, "selectAlimentos: " + e.getMessage());
            return lista;
        }
    }

    public ArrayList<Alimento> searchAlimentos(String param){
        /* Esquema próximo ao do selelctAlimentos, mas especificado */

        SQLiteDatabase sqlite = this.getReadableDatabase();
        ArrayList<Alimento> lista = new ArrayList<Alimento>();
        try{
            //Cursor concatenado para se adequar ao parâmetro inserido pelo usuário
            Cursor cur = sqlite.rawQuery("SELECT * FROM" + TABLE_A + "WHERE " + NOME + "LIKE = '%" + param + "%'", null);
            cur.moveToFirst();

            while(cur.isAfterLast() == false){
                Alimento a = new Alimento();
                a.setId(cur.getInt(cur.getColumnIndex("id"))); //Pega o ID da coluna
                /* Seta de acordo com o cursor*/
                a.setNome(cur.getString(cur.getColumnIndex(NOME)));
                a.setPorcao(cur.getString(cur.getColumnIndex(PORCAO)));
                a.setgCarb(cur.getFloat(cur.getColumnIndex(CARB)));
                a.setgPorcao(cur.getFloat(cur.getColumnIndex(PORC)));
                lista.add(a);
                cur.moveToNext(); //Após o fim das informações, move-se à próxima
            }
            return lista;
        }catch(SQLiteException e){
            e.printStackTrace();
            return null;
        }
    }

    //Métodos para a classe Usuario
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
            bd.insert(TABLE_U, null, contentValues); //Na ordem: tabela, TableHack (?), valores a serem add

            System.out.println("META F.A.: " + META);
            return true;
        } catch (Exception e) {
            Log.e(TAG_USUARIO, "insertUsuario: " + e.getMessage());
            return false;
        }

    }

    public boolean updateUsuario(Usuario usuario) {

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(FATORM, usuario.getSensM());
            contentValues.put(FATORT, usuario.getSensT());
            contentValues.put(FATORN, usuario.getSensN());

            contentValues.put(CHOUIM, usuario.getCHOuiM());
            contentValues.put(CHOUIT, usuario.getCHOuiT());
            contentValues.put(CHOUIN, usuario.getCHOuiN());

            contentValues.put(META, usuario.getMetaGlicemica());
            bd.update(TABLE_U, contentValues, "WHERE META = " + META, null);

            System.out.println("META CFG.: " + META);
            return true;
        } catch (Exception e) {
            Log.e(TAG_USUARIO, "updateUsuario: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> selectUsuario() {

        SQLiteDatabase bd = this.getReadableDatabase();
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        System.out.println("Lista de USUÁRIOS: " + lista.size());
        try {
            Cursor cur = bd.rawQuery("SELECT * FROM " + TABLE_U, null);
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
            Log.e(TAG_USUARIO, "selectUsuario: " + e.getMessage());
            return lista;
        }
    }

    //Métodos para a classe Refeição
    public boolean insertRefeicao (Refeicao refeicao){

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put(NOME_R, refeicao.getNome());
            contentValues.put(DATA, refeicao.getData());
            //Conversão -> ArrayList para String
            ArrayList<String> a = refeicao.getAlimentos();
            JSONObject json = new JSONObject();
            json.put("uniqueArrays", new JSONArray(a));
            String conv = json.toString();
            contentValues.put(LIST, conv);
            contentValues.put(PERIODO, refeicao.getPeriodo());
            contentValues.put(RESULTADO, refeicao.getUi());
            bd.insert(TABLE_R, null, contentValues); //Na ordem: tabela, TableHack (?), valores a serem add
            return true;
        }catch(Exception e){
            Log.e(TAG_ALIMENTO, "insertAlimento: " +e.getMessage());
            return false;
        }

    }

    public ArrayList<Refeicao> selectRefeicoes(){

        SQLiteDatabase bd = this.getReadableDatabase();
        ArrayList<Refeicao> lista = new ArrayList<Refeicao>();
        System.out.println(lista.size());
        try{
            Cursor cur = bd.rawQuery("SELECT * FROM " + TABLE_R, null);
            //Pega o primeiro resultado
            cur.moveToFirst();
            //Enquanto não for o último dado
            while(cur.isAfterLast() == false){
                Refeicao r = new Refeicao();
                r.setNome(cur.getString(cur.getColumnIndex(NOME_R)));
                r.setData(cur.getString(cur.getColumnIndex(DATA)));
                //Conversão String -> ArrayList<Alimento>
                JSONArray jsonArray = new JSONArray(cur.getString(cur.getColumnIndex(LIST)));
                ArrayList<String> a = new ArrayList<String>();
                for(int i = 0; i < jsonArray.length(); i++){
                    String al = jsonArray.getString(i);
                    a.add(al);
                }
                r.setAlimentos(a);
                r.setPeriodo(cur.getString(cur.getColumnIndex(PERIODO)));
                r.setUi(cur.getDouble(cur.getColumnIndex(RESULTADO)));
                lista.add(r);
                cur.moveToNext(); //Após o fim das informações, move-se à próxima
            }
            return lista;
        }catch(Exception e){
            Log.e(TAG_ALIMENTO, "selectAlimentos: " + e.getMessage());
            return lista;
        }
    }

}

