package com.nicotimeout.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nicotimeout.app.user.thirdFragment;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    public static final String USER_TABLE = "user_table";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_QUIT_DATE = "quit_date";
    public static final String COLUMN_CIG_PER_DAY = "cig_per_day";
    public static final String COLUMN_CIG_PRICE = "cig_price";
    public static final String COLUMN_CIG_YEARS = "cig_years";

    public static DatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
        // super(ctx, "user.db", null, 1);

    }

    public DatabaseHelper(Context ctx) {
        super(ctx, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUIT_DATE + " STRING, " +
                COLUMN_CIG_PER_DAY + " INTEGER, " +
                COLUMN_CIG_PRICE + " INTEGER, " +
                COLUMN_CIG_YEARS + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    public boolean addOne(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUIT_DATE, userModel.getQuit_date());
        cv.put(COLUMN_CIG_PER_DAY, userModel.getCig_per_day());
        cv.put(COLUMN_CIG_PRICE, userModel.getCig_price());
        cv.put(COLUMN_CIG_YEARS, userModel.getCig_years());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;

        } else {
            return true;
        }

    }

    public Cursor getData() {
        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(queryString, null,null);
    }


    public String getQuitDate() {
        // List<UserModel> returnList = new ArrayList<>();
        String queryString = " Select * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null); // rawQuery returns a Cursor.
        if (cursor.moveToFirst()) {
            do {
                /*  int user_id = cursor.getInt(0);*/
                String quit_date = cursor.getString(1);
             /*   int cig_per_day = cursor.getInt(2);
                int cig_price = cursor.getInt(3);
                int cig_years = cursor.getInt(4);*/

                //UserModel userModel = new UserModel(user_id, quit_date, cig_per_day, cig_price, cig_years);
                return quit_date;
            } while (cursor.moveToNext());

        } else {
            //If nothing in the database, do not add anything to the list.

        }
        cursor.close();
        db.close();
        return getQuitDate();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableStatement = ("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL(deleteTableStatement);
        onCreate(db);
    }
}
