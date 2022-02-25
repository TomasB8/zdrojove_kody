package app.aktivity.zaznamenavanieaktivit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DataModel1 extends SQLiteOpenHelper {

    protected static final String DB_DATABAZA = "dbZaznamy";
    protected static int DB_VERZIA = 2;
    protected static String DB_TABULKA = "tblZaznamy";

    public static final String ATR_ID = "_id";
    public static final String ATR_NAZOV = "nazov";
    public static final String ATR_DATUM = "datum";
    public static final String ATR_CAS = "cas";
    public static final String ATR_POZNAMKA = "poznamka";
    public static final String ATR_EPOCH = "epochDatum";

    public DataModel1(Context ctx){
        super(ctx, DB_DATABAZA, null, DB_VERZIA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABULKA
                + " (" + ATR_ID + " INTEGER PRIMARY KEY,"
                + ATR_NAZOV + " TEXT,"
                + ATR_DATUM + " TEXT,"
                + ATR_CAS + " TEXT,"
                + ATR_POZNAMKA + " TEXT,"
                + ATR_EPOCH + " INTEGER"
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
        val.put(ATR_DATUM, atributy.get(ATR_DATUM));
        val.put(ATR_CAS, atributy.get(ATR_CAS));
        val.put(ATR_POZNAMKA, atributy.get(ATR_POZNAMKA));
        val.put(ATR_EPOCH, Long.parseLong(atributy.get(ATR_EPOCH)));
        long id = db.insert(DB_TABULKA, null, val);
        db.close();
        return id;
    }

    public void vymazZaznam(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DB_TABULKA + " WHERE _id='" + id + "'";
        db.execSQL(deleteQuery);
    }

    public void vymazZaznamy(String nazov){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DB_TABULKA + " WHERE nazov='" + nazov + "'";
        db.execSQL(deleteQuery);
    }

    public int aktualizujZaznam(HashMap<String, String> atributy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ATR_NAZOV, atributy.get(ATR_NAZOV));
        val.put(ATR_DATUM, atributy.get(ATR_DATUM));
        val.put(ATR_CAS, atributy.get(ATR_CAS));
        val.put(ATR_POZNAMKA, atributy.get(ATR_POZNAMKA));
        val.put(ATR_EPOCH, Long.parseLong(atributy.get(ATR_EPOCH)));
        return db.update(DB_TABULKA, val, ATR_ID + " = ?",
                new String[]{atributy.get(ATR_ID)});
    }

    public void aktualizujNazov(String oldName, String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        String sSQL = "UPDATE " + DB_TABULKA + " SET nazov='" + newName + "' WHERE nazov='" + oldName + "'";
        db.execSQL(sSQL);
    }

    public int dajZaznamyCount(String nazov){
        String sSQL = "SELECT * FROM " + DB_TABULKA + " WHERE nazov='" + nazov + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        int number = 0;
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                number++;
            }while(cursor.moveToNext());
        }
        return number;
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
                hm.put(ATR_DATUM, cursor.getString(2));
                hm.put(ATR_CAS, cursor.getString(3));
                hm.put(ATR_POZNAMKA, cursor.getString(4));
                alVysledky.add(hm);
            }while(cursor.moveToNext());
        }
        return alVysledky;
    }

    public HashMap<String, String> dajZaznam(String id){
        HashMap<String, String> hm = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sSQL = "SELECT * FROM " + DB_TABULKA + " WHERE _id='"+id+"'";
        Cursor cursor = db.rawQuery(sSQL, null);
        if(cursor.moveToFirst()){
            do{
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_DATUM, cursor.getString(2));
                hm.put(ATR_CAS, cursor.getString(3));
                hm.put(ATR_POZNAMKA, cursor.getString(4));
            }while(cursor.moveToNext());
        }
        return hm;
    }

    public ArrayList<String> dajDatumy(){
        ArrayList<String> datumy = new ArrayList<>();
        String sSQL = "SELECT datum FROM " + DB_TABULKA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                String datum = cursor.getString(0);
                datumy.add(datum);
            }while(cursor.moveToNext());
        }
        return datumy;
    }

//    public ArrayList<HashMap<String, String>> dajZaznamyPodlaNazvu(String nazov) {
//        ArrayList<HashMap<String, String>> datumy = new ArrayList<>();
//        String sSQL = "SELECT * FROM " + DB_TABULKA;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(sSQL, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                HashMap<String, String> hm = new HashMap<>();
//                hm.put(ATR_ID, cursor.getString(0));
//                hm.put(ATR_NAZOV, cursor.getString(1));
//                hm.put(ATR_DATUM, cursor.getString(2));
//                hm.put(ATR_CAS, cursor.getString(3));
//                hm.put(ATR_POZNAMKA, cursor.getString(4));
//                datumy.add(hm);
//            }while(cursor.moveToNext());
//        }
//        return datumy;
//    }

    public ArrayList<String> dajZaznamyPodlaNazvu(String nazov) {
        ArrayList<String> datumy = new ArrayList<>();
        String sSQL = "SELECT datum FROM tblZaznamy WHERE nazov = '"+nazov+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                String datum = cursor.getString(0);
                datumy.add(datum);
            }while(cursor.moveToNext());
        }
        return datumy;
    }

    public ArrayList<HashMap<String, String>> dajDatumyPodlaDatumu(String datum) {
        ArrayList<HashMap<String, String>> datumy = new ArrayList<>();
        String sSQL = "SELECT * FROM tblZaznamy WHERE datum = '"+datum+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> hm = new HashMap<>();
                hm.put(ATR_ID, cursor.getString(0));
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_DATUM, cursor.getString(2));
                hm.put(ATR_CAS, cursor.getString(3));
                hm.put(ATR_POZNAMKA, cursor.getString(4));
                datumy.add(hm);
            }while(cursor.moveToNext());
        }
        return datumy;
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

    public ArrayList<HashMap<String, String>> dajZaznamyPodlaDatumuDESC(String nazov){
        ArrayList<HashMap<String, String>> datumy = new ArrayList<>();
        String sSQL = "SELECT * FROM tblZaznamy WHERE nazov = '"+nazov+"' ORDER BY epochDatum DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sSQL, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> hm = new HashMap<>();
                hm.put(ATR_ID, cursor.getString(0));
                hm.put(ATR_NAZOV, cursor.getString(1));
                hm.put(ATR_DATUM, cursor.getString(2));
                hm.put(ATR_CAS, cursor.getString(3));
                hm.put(ATR_POZNAMKA, cursor.getString(4));
                datumy.add(hm);
            }while(cursor.moveToNext());
        }
        return datumy;
    }
}
