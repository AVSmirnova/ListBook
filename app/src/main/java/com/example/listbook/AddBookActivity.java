package com.example.listbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddBookActivity extends AppCompatActivity {

    Button btnAdd;
    EditText nameBook;
    EditText avtorBook;
    RatingBar rating;
    Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        // находим элементы
        btnAdd=findViewById(R.id.btnAdd);
        nameBook=findViewById(R.id.nameBook);
        avtorBook=findViewById(R.id.avtorBook);
        rating=findViewById(R.id.rating);

        //добавляем обработчик нажатия на кнопку
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // создаем новый экземпляр книги
                book=new Book(nameBook.getText().toString(),avtorBook.getText().toString(),rating.getRating());
               // передаем его обратно в активитимейн
                Intent intent=new Intent();
                intent.putExtra(Book.class.getSimpleName(),book);
                // отправляем результат в мейнактивити
                setResult(RESULT_OK, intent);
               // закрываем активити
                finish();
            }
        });

    }

}