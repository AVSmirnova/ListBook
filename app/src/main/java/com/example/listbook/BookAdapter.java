package com.example.listbook;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    // добавляем поля
    private LayoutInflater inflater;
    private int layout;
    private List<Book> bookList;


    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> bookList) {
        super(context, resource, bookList);

        this.bookList = bookList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //создаем объект View для каждого отдельного элемента в списке
        View view=inflater.inflate(this.layout, parent, false);

        //Из созданного объекта View получаем элементы по id:
        TextView nameView = view.findViewById(R.id.namebook);
        TextView avtorView = view.findViewById(R.id.avtorname);
        RatingBar rating = view.findViewById(R.id.rating);

        //используя параметр position, получаем объект Book, для которого создается разметка
        Book book = bookList.get(position);

        //полученные элементы наполняем из полученного по позиции объекта Book
        nameView.setText(book.getNameBook());
        avtorView.setText(book.getAvtorName());
        rating.setRating(book.getRating());

        // возвращаем заполненный элемент списка
        return view;
    }

}
