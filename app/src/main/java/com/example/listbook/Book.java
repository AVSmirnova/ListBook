package com.example.listbook;

import java.io.Serializable;

public class Book implements Serializable{
    String nameBook;     // название книги
    String avtorName;    // автор
    float rating;        //рейтинг

   //конструктор класса
    public Book(String nameBook,String avtorName,float rating)
    {
        this.avtorName=avtorName;
        this.nameBook=nameBook;
        this.rating=rating;
    }

    // методы доступа к полям класса
    public String getNameBook()
    {
        return this.nameBook;
    }
    public void SetNameBook(String nameBook)
    {
        this.nameBook=nameBook;
    }

    public String getAvtorName()
    {
        return this.avtorName;
    }
    public void SetAvtorName(String autorName)
    {
        this.avtorName= autorName ;
    }

    public float getRating()
    {
        return this.rating;
    }
    public void setRating(float rating)
    {
        this.rating=rating;
    }

   /* @Override
    public int compareTo(Book book) {
        if(rating-book.rating>0) return 1;
        else
        if (rating-book.rating<0) return -1;
        else
        return 0 ;
    }*/
}
