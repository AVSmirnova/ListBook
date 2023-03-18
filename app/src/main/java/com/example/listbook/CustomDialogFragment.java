package com.example.listbook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    Removable removableList;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        removableList = (Removable) context;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // принимаем строку
        String bookName = getArguments().getString("book");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление книги")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы хотите удалить " + bookName + "?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        removableList.deleteData(bookName);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
