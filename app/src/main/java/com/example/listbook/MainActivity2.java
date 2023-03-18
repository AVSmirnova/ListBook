package com.example.listbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;

public class MainActivity2 extends AppCompatActivity {
    Book book;
    RatingBar ratingBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //найдем в разметке элемент вебвью и определим для него переменную
        WebView webView=findViewById(R.id.web);
      // установим для вебвью поддержку JS
        webView.getSettings().setJavaScriptEnabled(true);
        // создадим экземпляр созданного класса WebClient
        WebClient webViewClient= new WebClient();
        // установим его в качестве вебклиента для вебвью
        webView.setWebViewClient(webViewClient);

       // найдем элемент рейтинг
        ratingBook =findViewById(R.id.rating);

        // получаем данные переданные из основной активити
        Bundle arguments= getIntent().getExtras();

        if(arguments!=null){
            book=(Book)arguments.getSerializable(Book.class.getSimpleName());
            //устанавливаем значение рейтинга
            ratingBook.setRating(book.getRating());
           // заменяем пробелы в названии книги на подчерки для использования в ссылке
            book.nameBook= book.nameBook.replace(" ","_");
           // формируем ссылку и загружаем в вебвью
            String url=String.format("https://ru.wikipedia.org/wiki/%s",book.nameBook);
            webView.loadUrl(url);

        }

    }

    @Override
    public void onBackPressed() {

        // устанавливаем новый рейтинг в текущий объект
        book.setRating(ratingBook.getRating());
        // создаем интент для передачи данных
        Intent intent = new Intent();
        // передаем обратно экземпляр книги с изменным рейтингом
        intent.putExtra(Book.class.getSimpleName(),book);
        // отправляем результат в мейнактивити
        setResult(RESULT_OK, intent);
        super.onBackPressed();


    }
}
