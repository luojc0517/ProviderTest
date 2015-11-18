package com.jackie.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnQuery).setOnClickListener(this);
        findViewById(R.id.btnUpdate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Uri uriDir = Uri.parse("content://com.jackie.databasetest.provider/book");
        Uri uriItem = Uri.parse("content://com.jackie.databasetest.provider/book/" + newId);
        switch (v.getId()) {
            case R.id.btnAdd:
                ContentValues book = new ContentValues();
                book.put("name", "无声告白");
                book.put("price", 17.15);
                book.put("pages", 290);
                book.put("author", "伍绮诗");
                Uri newUri = getContentResolver().insert(uriDir, book);
                newId = newUri.getPathSegments().get(1);
                break;
            case R.id.btnQuery:
                Cursor cursor = getContentResolver().query(uriDir, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        Log.v("jackie", "name is" + " " + name);
                        Log.v("jackie", "author is" + " " + author);
                        Log.v("jackie", "price is" + " " + price);
                        Log.v("jackie", "pages is" + " " + pages);
                    }
                }

                break;
            case R.id.btnUpdate:
                ContentValues bookNew = new ContentValues();
                bookNew.put("name", "年少荒唐");
                bookNew.put("price", 18.00);
                bookNew.put("pages", 323);
                bookNew.put("author", "朱炫");
                getContentResolver().update(uriItem, bookNew, null, null);
                break;
            case R.id.btnDelete:
                getContentResolver().delete(uriItem, null, null);
                break;
        }
    }
}
