package app.aktivity.zaznamenavanieaktivit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataModel extends SQLiteOpenHelper {

    protected static final String DB_DATABAZA = "dbAktivity";
    protected static final int DB_VERZIA = 1;
    protected static final String DB_TABULKA = "tblAktivity";

    public static final String ATR_ID = "_id";
    public static final String ATR_NAZOV = "nazov";
    public static final String ATR_FARBA = "farba";
    public static final String ATR_POCET = "pocet";

    public DataModel(Context ctx){
        super(ctx, DB_DATABAZA, null, DB_VERZIA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABULKA
            + " (" + ATR_ID + " INTEGER PRIMARY KEY,"
            + ATR_NAZOV + " TEXT,"
            + ATR_FARBA + " TEXT,"
            + ATR_POCET + " INTEGER"
            + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + DB_TABULKA;
        db.execSQL(query);
        onCreate(db);
    }

    public long vlozZaznam(HashMap<String, String> atributy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ATR_NAZOV, atributy.get(ATR_NAZOV));
        val.put(ATR_FARBA, atributy.get(ATR_FARBA));
        val.put(ATR_POCET, Integer.parseInt(atributy.get(ATR_POCET)));
        long id = db.insert(DB_TABULKA, null, val);
        db.close();
        return id;
    }

    public int aktualizujAktivitu(HashMap<String, String> atributy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ATR_NAZOV, atributy.get(ATR_NAZOV));
        val.put(ATR_FARBA, atributy.get(ATR_FARBA));
        val.put(ATR_POCET, Integer.parseInt(atributy.get(ATR_POCET)));
        return db.update(DB_TABULKA, val, ATR_ID + " = ?",
                new String[]{atributy.get(ATR_ID)});
    }

    public void vymazAktivitu(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DB_TABULKA + " WHERE _id='" + id + "'";
        db.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> dajZaznamy(){
        ArrayList<HashMap<String, String>> alVysledky;
        alVysledky = new ArrayList<>();
        String sSQL = "SELECT * FROM " + DB_TABULKA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> hm = new HashMap<>();
                hm.put(ATR_ID, cursor.getString(0));
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_FARBA, cursor.getString(2));
                hm.put(ATR_POCET, cursor.getString(3));
                alVysledky.add(hm);
            }while(cursor.moveToNext());
        }
        return alVysledky;
    }

    public ArrayList<HashMap<String, String>> dajZaznamyOrdered(){
        ArrayList<HashMap<String, String>> alVysledky;
        alVysledky = new ArrayList<>();
        String sSQL = "SELECT * FROM " + DB_TABULKA + " ORDER BY pocet DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> hm = new HashMap<>();
                hm.put(ATR_ID, cursor.getString(0));
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_FARBA, cursor.getString(2));
                hm.put(ATR_POCET, cursor.getString(3));
                alVysledky.add(hm);
            }while(cursor.moveToNext());
        }
        return alVysledky;
    }

    public HashMap<String, String> dajZazanam(String id){
        HashMap<String, String> hm = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sSQL = "SELECT * FROM " + DB_TABULKA + " WHERE _id='"+id+"'";
        Cursor cursor = db.rawQuery(sSQL, null);
        if(cursor.moveToFirst()){
            do{
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_FARBA, cursor.getString(2));
                hm.put(ATR_POCET, cursor.getString(3));
            }while(cursor.moveToNext());
        }
        return hm;
    }

    public String dajIdPodlaNazvu(String nazov){
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String sSQL = "SELECT _id FROM " + DB_TABULKA + " WHERE nazov='"+nazov+"'";
        Cursor cursor = db.rawQuery(sSQL, null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return id;
    }

    public HashMap<String, String> dajZaznamyPodlaNazvu(String nazov) {
        HashMap<String, String> hm = new HashMap<>();
        String sSQL = "SELECT * FROM tblAktivity WHERE nazov = '"+nazov+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_FARBA, cursor.getString(2));
                hm.put(ATR_POCET, cursor.getString(3));
            }while(cursor.moveToNext());
        }
        return hm;
    }

    public String dajFarbuPodlaNazvu(String nazov) {
        HashMap<String, String> hm = new HashMap<>();
        String sSQL = "SELECT farba FROM tblAktivity WHERE nazov = '"+nazov+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);
        String farba = "";

        if(cursor.moveToFirst()){
            do{
                farba = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return farba;
    }

    public int dajZazanamyCount(){
        ArrayList<HashMap<String, String>> alVysledky;
        alVysledky = new ArrayList<>();
        String sSQL = "SELECT * FROM " + DB_TABULKA;
        SQLiteDatabase db = this.getWritableDatabase();
        int number = 0;
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> hm = new HashMap<>();
                hm.put(ATR_ID, cursor.getString(0));
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_FARBA, cursor.getString(2));
                hm.put(ATR_POCET, cursor.getString(3));
                alVysledky.add(hm);
                number++;
            }while(cursor.moveToNext());
        }
        return number;
    }

    public ArrayList<String> dajNazvy(){
        ArrayList<String> nazvy = new ArrayList<>();
        String sSQL = "SELECT nazov FROM " + DB_TABULKA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                String meno = cursor.getString(0);
                nazvy.add(meno);
            }while(cursor.moveToNext());
        }
        return nazvy;
    }

    public ArrayList<String> dajNazvyOrdered(){
        ArrayList<String> nazvy = new ArrayList<>();
        String sSQL = "SELECT nazov FROM " + DB_TABULKA + " ORDER BY pocet DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                String meno = cursor.getString(0);
                nazvy.add(meno);
            }while(cursor.moveToNext());
        }
        return nazvy;
    }

    public ArrayList<String> dajFarby(){
        ArrayList<String> farby = new ArrayList<>();
        String sSQL = "SELECT farba FROM " + DB_TABULKA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                String farba = cursor.getString(0);
                farby.add(farba);
            }while(cursor.moveToNext());
        }
        return farby;
    }

    public String dajPocet(String nazov){
        String pocet = "";
        String sSQL = "SELECT pocet FROM " + DB_TABULKA + " WHERE nazov='" + nazov + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                pocet = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return pocet;
    }
}
