package com.example.trenlopsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "db.sqlite", null   , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Authors(idauthor integer primary key, name text, address text, email text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Authors");
        onCreate(sqLiteDatabase);
    }
    public int insertAuthor(Author author){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idauthor",author.getId_author());
        contentValues.put("name",author.getName());
        contentValues.put("address",author.getAddress());
        contentValues.put("email",author.getEmail());

        int result = (int) database.insert("Authors",null,contentValues);
        database.close();
        return result;
    }

    public ArrayList<Author> getAllAuthor(){
        ArrayList<Author> authors = new ArrayList<>();
        SQLiteDatabase database =getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Authors",null);
        if(cursor!=null)
            cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            authors.add(new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return authors;
    }
    public Author getAllAuthorByID(int id){
//        ArrayList<Author> authors = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Authors WHERE idauthor= "+id+" ",null);
        if(cursor!=null)
            cursor.moveToFirst();

        Author author = new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        cursor.close();
        database.close();
        return author;
    }
    public boolean deleteAuthor(int id){
        SQLiteDatabase database = getWritableDatabase();
        if(database.delete("Authors","idauthor" + "=?",
                new String[] {String.valueOf(id)}) > 0){
            database.close();
        };
        return true;
    }
    public boolean updateAuthor(int id, String name, String address, String email){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("idauthor",id);
        contentValues.put("name",name);
        contentValues.put("address",address);
        contentValues.put("email",email);

        database.update("Authors",contentValues,"idauthor ="+id,null);
        return true;
    }

    // truy vấn không trả về kết quá Create,insert,update,delete,...
    public void Querydata(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    // truy vấn trả về kết quả: Select
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery(sql,null);
    }
}
