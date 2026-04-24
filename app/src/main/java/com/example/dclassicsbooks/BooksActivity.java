package com.example.dclassicsbooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerView = findViewById(R.id.bookRecyclerView);

        bookList = new ArrayList<>();

        bookList.add(new Book(
                "Meditations",
                "Marcus Aurelius",
                "A collection of personal writings from a Roman emperor reflecting on Stoic philosophy. It focuses on self-discipline, resilience, and accepting things beyond your control while striving to live a virtuous life",
                "Non-Fiction",
                "0140449337",
                319,
                "April 27, 2006",
                R.drawable.img_meditations
        ));

        bookList.add(new Book(
                "The Subtle Art of Not Giving A F*ck",
                "Mark Manson",
                "A blunt, modern take on self-improvement that argues you should stop trying to be positive all the time. Instead, focus on what truly matters and accept life’s struggles as part of growth.",
                "Non-Fiction",
                "9124238341",
                580,
                "June 24, 2023",
                R.drawable.img_the_subtle_art_of_not_giving_a_f
        ));

        adapter = new BookAdapter(bookList, book -> {
            Intent intent = new Intent(BooksActivity.this, BookDetailActivity.class);

            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("desc", book.getDescription());
            intent.putExtra("genre", book.getGenre());
            intent.putExtra("isbn", book.getIsbn());
            intent.putExtra("pages", book.getPages());
            intent.putExtra("date", book.getReleaseDate());
            intent.putExtra("image", book.getImageRes());

            startActivity(intent);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}