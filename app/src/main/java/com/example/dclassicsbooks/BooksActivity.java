package com.example.dclassicsbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;

    // 🔽 Dropdown
    LinearLayout dropdown;
    ImageView arrow;
    LinearLayout selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // ------------------------
        // DROPDOWN SETUP
        // ------------------------
        setupDropdown("Books");

        selected = findViewById(R.id.selectedLayout);
        dropdown = findViewById(R.id.dropdownMenu);
        View dropdownView = findViewById(R.id.dropdownView);
        arrow = findViewById(R.id.iconArrow);

        TextView txtSelected = findViewById(R.id.txtSelected);
        ImageView iconSelected = findViewById(R.id.iconSelected);

        txtSelected.setText("Books");
        iconSelected.setImageResource(R.drawable.ic_books_selected);

        selected.setOnClickListener(v -> toggleDropdown());

        // layering fix
        View headerContainer = (View) findViewById(R.id.headerView).getParent();
        headerContainer.bringToFront();
        headerContainer.setElevation(30f);

        dropdownView.bringToFront();
        dropdownView.setElevation(50f);

        // ------------------------
        // NAVIGATION
        // ------------------------
        findViewById(R.id.itemHome).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        findViewById(R.id.itemStores).setOnClickListener(v -> {
            startActivity(new Intent(this, StoresActivity.class));
            finish();
        });

        findViewById(R.id.itemLogout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // ------------------------
        // RECYCLER VIEW
        // ------------------------
        recyclerView = findViewById(R.id.bookRecyclerView);

        bookList = new ArrayList<>();

        bookList.add(new Book(
                "Meditations",
                "Marcus Aurelius",
                "A collection of personal writings from a Roman emperor reflecting on Stoic philosophy.",
                "Non-Fiction",
                "0140449337",
                319,
                "27 Apr. 2006",
                R.drawable.img_meditations
        ));

        bookList.add(new Book(
                "The Subtle Art of Not Giving A F*ck",
                "Mark Manson",
                "A blunt, modern take on self-improvement.",
                "Non-Fiction",
                "9124238341",
                580,
                "24 Jun. 2023",
                R.drawable.img_the_subtle_art_of_not_giving_a_f
        ));

        bookList.add(new Book(
                "Beyond Good and Evil",
                "Friedrich Nietzsche",
                "A philosophical critique of traditional morality.",
                "Non-Fiction",
                "014044923X",
                240,
                "27 Feb. 2003",
                R.drawable.img_beyond_good_and_evil
        ));

        bookList.add(new Book(
                "Letters To Milena",
                "Franz Kafka",
                "A collection of deeply emotional letters.",
                "Non-Fiction",
                "1784874000",
                272,
                "6 Dec. 2018",
                R.drawable.img_letters_to_milena
        ));

        bookList.add(new Book(
                "The Art of Loving",
                "Erich Fromm",
                "Explores love as a skill.",
                "Non-Fiction",
                "9781480402447",
                142,
                "26 Feb. 2013",
                R.drawable.img_the_art_of_loving
        ));

        bookList.add(new Book(
                "White Nights",
                "Fyodor Dostoyevsky",
                "A bittersweet story about loneliness.",
                "Fiction",
                "6349606558",
                88,
                "3 Sep. 2025",
                R.drawable.img_white_nights
        ));

        bookList.add(new Book(
                "The Stranger",
                "Albert Camus",
                "A novel about absurdism.",
                "Fiction",
                "1916700334",
                105,
                "6 Jun. 2024",
                R.drawable.img_the_stranger
        ));

        bookList.add(new Book(
                "Anna Karenina",
                "Leo Tolstoy",
                "A tragic love story about a married woman who pursues a passionate affair. It explores love, betrayal, family, and the consequences of following one’s desires.",
                "Fiction",
                "0140449175",
                864,
                "30 Jan. 2003",
                R.drawable.img_anna_karenina
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

    // ------------------------
    // SETUP DROPDOWN
    // ------------------------
    private void setupDropdown(String current) {

        View itemHome = findViewById(R.id.itemHome);
        View itemBooks = findViewById(R.id.itemBooks);
        View itemStores = findViewById(R.id.itemStores);

        itemHome.setVisibility(View.VISIBLE);
        itemBooks.setVisibility(View.VISIBLE);
        itemStores.setVisibility(View.VISIBLE);

        switch (current) {
            case "Home":
                itemHome.setVisibility(View.GONE);
                break;
            case "Books":
                itemBooks.setVisibility(View.GONE);
                break;
            case "Stores":
                itemStores.setVisibility(View.GONE);
                break;
        }
    }

    // ------------------------
    // DROPDOWN ANIMATION
    // ------------------------
    private void toggleDropdown() {
        if (dropdown.getVisibility() == View.GONE) {

            dropdown.setAlpha(0f);
            dropdown.setTranslationY(-20f);
            dropdown.setVisibility(View.VISIBLE);

            dropdown.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();

            arrow.animate().rotation(180f).setDuration(200);

        } else {
            closeDropdown();
        }
    }

    private void closeDropdown() {
        dropdown.animate()
                .alpha(0f)
                .translationY(-20f)
                .setDuration(150)
                .withEndAction(() -> dropdown.setVisibility(View.GONE))
                .start();

        arrow.animate().rotation(0f).setDuration(150);
    }

    // ------------------------
    // OUTSIDE CLICK CLOSE
    // ------------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                dropdown.getVisibility() == View.VISIBLE) {

            int[] dropLoc = new int[2];
            dropdown.getLocationOnScreen(dropLoc);

            int[] selectedLoc = new int[2];
            selected.getLocationOnScreen(selectedLoc);

            float x = event.getRawX();
            float y = event.getRawY();

            boolean insideDropdown =
                    x >= dropLoc[0] && x <= dropLoc[0] + dropdown.getWidth() &&
                            y >= dropLoc[1] && y <= dropLoc[1] + dropdown.getHeight();

            boolean insideSelected =
                    x >= selectedLoc[0] && x <= selectedLoc[0] + selected.getWidth() &&
                            y >= selectedLoc[1] && y <= selectedLoc[1] + selected.getHeight();

            if (!insideDropdown && !insideSelected) {
                closeDropdown();
            }
        }

        return super.dispatchTouchEvent(event);
    }
}