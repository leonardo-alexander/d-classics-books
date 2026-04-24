package com.example.dclassicsbooks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    ImageView imgBook;
    TextView txtTitle, txtAuthor, txtInfo, txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        imgBook = findViewById(R.id.imgBook);
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtInfo = findViewById(R.id.txtInfo);
        txtDesc = findViewById(R.id.txtDesc);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String desc = intent.getStringExtra("desc");
        String genre = intent.getStringExtra("genre");
        String isbn = intent.getStringExtra("isbn");
        int pages = intent.getIntExtra("pages", 0);
        String date = intent.getStringExtra("date");
        int image = intent.getIntExtra("image", 0);

        imgBook.setImageResource(image);
        txtTitle.setText(title);
        txtAuthor.setText(author);

        txtInfo.setText(
                genre + "\n" +
                "ISBN " + isbn + "\n" +
                pages + " pages\n" +
                date
        );

        txtDesc.setText(desc);
    }
}