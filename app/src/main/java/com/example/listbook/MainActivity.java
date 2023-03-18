package com.example.listbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements Removable{

    //добавьте константы, каждая будет отвечать за соответствующее активити
    final static int REQUEST_CODE_RATING = 1;
    final static int REQUEST_CODE_ADDBOOK = 2;
    int position;
    //создаем переменную списка
    ListView list;
    //создаем список книг для отображения в списке
    ArrayList<Book> listBook = new ArrayList<Book>();
    BookAdapter bookAdapter;

    // создаем переменные для работы с БД
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //сопоставляем визуальный элемент с переменной
        list =findViewById(R.id.list);

        databaseHelper = new DBHelper(getApplicationContext());

        //вызываем метод заполнения списка книг, сам метод описан ниже
        setInitialData();

        // создаем адаптер
        bookAdapter = new BookAdapter(this, R.layout.listitem, listBook);
        // устанавливаем адаптер
        list.setAdapter(bookAdapter);

        registerForContextMenu(list);

        // слушатель выбора элемента в списке
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              position=i;
               //создаем интент для перехода на новую активити
               Intent intent =new Intent(getApplicationContext(),MainActivity2.class);
               // передаем в него данные, i - позиция элемента в списке
               intent.putExtra(Book.class.getSimpleName(), listBook.get(i));
               startActivityForResult(intent, REQUEST_CODE_RATING);
           }
       });



    }
    //метод заполнения списка книгами
    private void setInitialData(){
        if(listBook!=null)  listBook.clear();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from "+ DBHelper.TABLE, null);

        while(userCursor.moveToNext()){
            String name = userCursor.getString(1);
            String avtor= userCursor.getString(2);
            Float rating = userCursor.getFloat(3);
            listBook.add(new Book(name,avtor,rating));
        }
        userCursor.close();
        db.close();

       /* listBook.add(new Book("Алиса в стране чудес","Льюис Кэрролл",4));
        listBook.add(new Book("Двадцать тысяч лье под водой","Жюль Верн",0));
        listBook.add(new Book("Дракула","Брэм Стокер",0));
        listBook.add(new Book("Война миров","Герберт Уэллс",0));
        listBook.add(new Book("Дверь в лето","Роберт Хайнлайн",5));
        listBook.add(new Book("Звездный десант","Роберт Хайнлайн",0));
        listBook.add(new Book("Нейромант","Уильям Гибсон",0));
        listBook.add(new Book("2001: Космическая Одиссея","Артур Кларк",0));
        listBook.add(new Book("Парк Юрского периода","Майкл Крайтон",0));
        listBook.add(new Book("Машина времени","Герберт Уэллс",0));
        listBook.add(new Book("1984","Джордж Оруэлл ",0));
        listBook.add(new Book("451 градус по Фаренгейту","Рэй Брэдбери",0));
        listBook.add(new Book("Солярис","Станислав Лем",0));
        listBook.add(new Book("Марсианские хроники","Рэй Брэдбери",0));
        listBook.add(new Book("Ожерелье планет Эйкумены","Урсула Ле Гуин",0));
        listBook.add(new Book("Дюна","Фрэнк Герберт ",0));
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

        // проверяем есть ли возвращенные данные
        if (data == null) {return;}
        // получаем данные из активити
        Book book = (Book) data.getSerializableExtra(Book.class.getSimpleName());

        // если данные пришли из MainAktivity2
        if (requestCode==REQUEST_CODE_RATING) {

            // устанавливаем новый рейтинг текущему элементу
          //  listBook.get(position).setRating(book.getRating());

            updateData(book, position);
            setInitialData();
        }
        //если данные пришли из AddBookActivity
        if (requestCode==REQUEST_CODE_ADDBOOK)
        {
            //Добавляем новый элемент в список
           // listBook.add(book);
            insertData(book);
            setInitialData();
        }
        // обновляем список для отображения новых данных
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //получаем id выбранного пункта меню
        int id = item.getItemId();
        switch(id){
            case R.id.opotion_add:
                // переходим в активити добавления новой книги
                Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADDBOOK);

            return true;

            case R.id.option_up:
                // сортируем так что большой рейтинг сверху
                Collections.sort(listBook, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        if(book.getRating()- t1.getRating()>0) return -1;
                        else
                        if (book.getRating()- t1.getRating()<0) return 1;
                        else
                            return 0 ;
                    }
                });
                // обновляем адаптер
                bookAdapter.notifyDataSetChanged();
            return true;

            case R.id.option_down:
                // сортируем так что большой рейтинг снизу
                Collections.sort(listBook, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        if(book.getRating()- t1.getRating()>0) return 1;
                        else
                        if (book.getRating()- t1.getRating()<0) return -1;
                        else
                            return 0 ;
                    }
                });
                bookAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // создаем меню
        getMenuInflater().inflate(R.menu.context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
       // устанавливаем адаптер между списком и контекстным меню
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.context_del:
                // удаляем выделенный элемент списка

                String selectedBook =listBook.get(info.position).getNameBook();
                CustomDialogFragment dialog = new CustomDialogFragment();
                Bundle args = new Bundle();
                args.putString("book", selectedBook);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "custom");

                return true;

            default:
                return super.onContextItemSelected(item);
        }


    }
    void updateData(Book book, int id)
    {   String selectedBook =book.getNameBook().replace('_',' ');
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.RATING, book.getRating());

        Log.i("h1",selectedBook);
        db.update(DBHelper.TABLE, cv,"nameBook = ?", new String[]{selectedBook});
        db.close();
    }

    void insertData(Book book){
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAMEBOOK, book.getNameBook());
        cv.put(DBHelper.COLUMN_AVTORNAME, book.getAvtorName());
        cv.put(DBHelper.RATING, book.getRating());

        Log.i("h1",book.getNameBook());
        db.insert(DBHelper.TABLE, null, cv);
        db.close();
    }
    public void deleteData(String selectedBook){
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE, "nameBook= ?", new String[]{selectedBook});
        db.close();
        setInitialData(); // заного загружаем список книг из БД в listbook
        bookAdapter.notifyDataSetChanged();
    }
}