package com.example.listbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db"; // название бд
    private static final int DB_VERSION = 1; // версия базы данных
    static final String TABLE = "books"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMEBOOK = "nameBook";
    public static final String COLUMN_AVTORNAME = "avtorName";
    public static final String RATING = "rating";

    // конструктор
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAMEBOOK  + " TEXT, "
                + COLUMN_AVTORNAME + " TEXT, "
                + RATING +" REAL);");
        // добавление начальных данных
       initialData(db);
    }
    // метод занесения данных в таблицу
    void initialData(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAMEBOOK
                + ", " + COLUMN_AVTORNAME + ", "+ RATING  + ") VALUES ('Алиса в стране чудес','Льюис Кэрролл',4);");

        // добавьте сюда остальные данные
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
