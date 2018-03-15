package com.example.a74021.project1.helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
/**
 * Created by 74021 on 2018/1/20.
 */

public class NoteDateBaseHelper extends SQLiteOpenHelper {

    public static final String CreateNote = "create table note ("
            + "id integer primary key autoincrement, "
            +"content text , "
            +"date text)";

    public NoteDateBaseHelper(Context context)
    {
        super(context,"note",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateNote);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0,int arg1,int arg2)
    {

    }
}
