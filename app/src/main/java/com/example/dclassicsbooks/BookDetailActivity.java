package com.example.dclassicsbooks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BookDetailActivity extends AppCompatActivity {

    ImageView imgBook;
    TextView txtTitle, txtAuthor, txtInfo, txtDesc;

    EditText etAddress, etPhone;
    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // === Book UI ===
        imgBook = findViewById(R.id.imgBook);
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtInfo = findViewById(R.id.txtInfo);
        txtDesc = findViewById(R.id.txtDesc);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // === Form UI (rename these IDs in XML if possible) ===
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        btnBuy = findViewById(R.id.btnBuy);

        // === Get Data ===
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String desc = intent.getStringExtra("desc");
        String genre = intent.getStringExtra("genre");
        String isbn = intent.getStringExtra("isbn");
        int pages = intent.getIntExtra("pages", 0);
        String date = intent.getStringExtra("date");
        int image = intent.getIntExtra("image", 0);

        // === Set Data safely ===
        if (image != 0) imgBook.setImageResource(image);
        if (title != null) txtTitle.setText(title);
        if (author != null) txtAuthor.setText(author);

        txtInfo.setText(
                (genre != null ? genre : "") + "\n" +
                        "ISBN " + (isbn != null ? isbn : "-") + "\n" +
                        pages + " pages\n" +
                        (date != null ? date : "")
        );

        if (desc != null) txtDesc.setText(desc);

        // === Button Click ===
        btnBuy.setOnClickListener(v -> {

            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // 1. Empty validation
            if (address.isEmpty() || phone.isEmpty()) {

                new MaterialAlertDialogBuilder(BookDetailActivity.this)
                        .setTitle("Error")
                        .setMessage("Address and phone number must be filled.")
                        .setPositiveButton("OK", null)
                        .show();

                return;
            }

            // 2. Numeric validation
            if (!phone.matches("\\d+")) {

                new MaterialAlertDialogBuilder(BookDetailActivity.this)
                        .setTitle("Error")
                        .setMessage("Phone number must be numeric.")
                        .setPositiveButton("OK", null)
                        .show();

                return;
            }

            // 3. Success → show dialog → redirect
            new MaterialAlertDialogBuilder(BookDetailActivity.this)
                    .setTitle("Purchase Successful")
                    .setMessage("A confirmation email has been sent to your email.")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> {

                        Intent goToBooks = new Intent(BookDetailActivity.this, BooksActivity.class);
                        startActivity(goToBooks);
                        finish();

                    })
                    .show();
        });
    }
}