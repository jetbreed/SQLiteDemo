package com.niit.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessModeller extends SQLiteOpenHelper {
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String SURNAME = "SURNAME";
    public static final String AGE = "AGE";
    public static final String MEMBER = "MEMBER";
    public static final String ACTIVE_MEMBER = "ACTIVE_MEMBER";
    public static final String ID = "ID";

    public DatabaseAccessModeller(@Nullable Context theMainActvityClass) {
        super(theMainActvityClass, "member.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase member_db) {
        String createTable = "CREATE TABLE " + MEMBER +
                " (" + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRSTNAME +
                " TEXT, " +
                SURNAME +
                " TEXT, " +
                AGE +
                " INT, " +
                ACTIVE_MEMBER +
                " BOOL)"
                ;

        member_db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(MemberModel memberModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIRSTNAME, memberModel.getFirstName());
        cv.put(SURNAME, memberModel.getSurName());
        cv.put(AGE, memberModel.getAge());
        cv.put(ACTIVE_MEMBER, memberModel.isActive());

//        db.insert(MEMBER,null,cv);
          long insert = db.insert(MEMBER,null,cv);
          if(insert == -1){
             return false;
          }else{
              return true;
        }

    }

    public List<MemberModel> getResultList(){
        List<MemberModel> resultset = new ArrayList<>();

        String queryAll = "SELECT * FROM " + MEMBER;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryAll, null);

        if(cursor.moveToFirst()){
            do{
                int memberID = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String surname = cursor.getString(2);
                int age = cursor.getInt(3);
                boolean memberActive = cursor.getInt(4) == 1 ? true: false;

                MemberModel memberModel = new MemberModel(memberID, firstName, surname, age, memberActive);
                resultset.add(memberModel);

            }while(cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return resultset;
    }

    public boolean deleteOne(MemberModel memberModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MEMBER + " WHERE " + ID + " = " + memberModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteAll(MemberModel memberModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MEMBER;

//        db.execSQL(queryString, null);
        db.execSQL(queryString);
//        db.close();
        return true;
    }

}

