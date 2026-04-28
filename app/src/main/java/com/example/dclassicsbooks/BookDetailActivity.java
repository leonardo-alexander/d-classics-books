package com.example.dclassicsbooks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        // === Form UI ===
        etAddress = findViewById(R.id.etUsername);
        etPhone = findViewById(R.id.etPassword);
        btnBuy = findViewById(R.id.btnLogin);

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

        // === Set Data ===
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

        // === Button Click ===
        btnBuy.setOnClickListener(v -> {

            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // reset errors first
            etAddress.setError(null);
            etPhone.setError(null);

            // 1. Empty validation
            if (address.isEmpty()) {
                etAddress.setError("Address required");
                etAddress.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                etPhone.setError("Phone number required");
                etPhone.requestFocus();
                return;
            }

            // 2. Numeric phone validation
            if (!phone.matches("\\d+")) {
                etPhone.setError("Phone must be numeric");
                etPhone.requestFocus();
                return;
            }

            // 3. Success → go to Home
            Intent successIntent = new Intent(BookDetailActivity.this, HomeActivity.class);
            startActivity(successIntent);
            finish();
        });
    }
}