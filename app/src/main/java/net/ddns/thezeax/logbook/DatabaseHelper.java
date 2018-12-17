package net.ddns.thezeax.logbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "logbook.db";
    public static final String TABLE_NAME = "transactions";
    public static final String COL1 = "id";
    public static final String COL2 = "price";
    public static final String COL3 = "description";
    public static final String COL4 = "timestamp";
    public static final String COL5 = "origin";
    public static final String COL6 = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        //SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ("+
                COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL2 +" decimal(10,2) not null,"+
                COL3 +" varchar(20) not null,"+
                COL4 +" varchar(20) ,"+
                COL5 +" varchar(20) ,"+
                COL6 +" varchar(20))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(double price, String description, String timestamp, String origin, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, price);
        contentValues.put(COL3, description);
        contentValues.put(COL4, timestamp);
        contentValues.put(COL5, origin);
        contentValues.put(COL6, category);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }
}
