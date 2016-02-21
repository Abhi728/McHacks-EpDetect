package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static  String DATABASE_NAME = "contact.db";
    public final static  String TABLE_NAME = "contact_table";
    public final static  String COL_ID = "ID";
    public final static  String COL_NAME = "NAME";
    public final static  String COL_NUMBER = "NUMBER";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NUMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name ,String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_NUMBER, number);
        long result = db.insert(TABLE_NAME,null, cv);
        if (result == 1) {
            return false;
        }
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String maxId = "(SELECT MAX(ID) FROM " + TABLE_NAME + ")";
        return db.delete(TABLE_NAME, "ID = " + maxId, null);
    }
}
