package com.example.dclassicsbooks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements BookAdapter.OnBookClickListener {

    LinearLayout dropdown;
    ImageView arrow;
    LinearLayout selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView greeting = findViewById(R.id.tvGreeting);

        // GET username from SharedPreferences (persistent)
        SharedPreferences prefs = getSharedPreferences("app", MODE_PRIVATE);
        String username = prefs.getString("username", null);

        // fallback (first time from intent)
        if (username == null) {
            username = getIntent().getStringExtra("username");

            if (username != null) {
                // save it so it persists
                prefs.edit().putString("username", username).apply();
            }
        }

        // final fallback
        if (username == null) username = "Tester";

        greeting.setText(getString(R.string.greet, username.toUpperCase()));

        // ------------------------
        // DROPDOWN SETUP
        // ------------------------
        setupDropdown("Home");

        selected = findViewById(R.id.selectedLayout);
        dropdown = findViewById(R.id.dropdownMenu);
        View dropdownView = findViewById(R.id.dropdownView);
        arrow = findViewById(R.id.iconArrow);

        TextView txtSelected = findViewById(R.id.txtSelected);
        ImageView iconSelected = findViewById(R.id.iconSelected);

        txtSelected.setText("Home");
        iconSelected.setImageResource(R.drawable.ic_home_selected);

        selected.setOnClickListener(v -> toggleDropdown());

        // layering
        View headerContainer = (View) findViewById(R.id.headerView).getParent();
        headerContainer.bringToFront();
        headerContainer.setElevation(30f);

        dropdownView.bringToFront();
        dropdownView.setElevation(50f);

        // ------------------------
        // NAVIGATION
        // ------------------------
        findViewById(R.id.itemBooks).setOnClickListener(v -> {
            startActivity(new Intent(this, BooksActivity.class));
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

        // ========================
        // CAROUSEL (STORES)
        // ========================
        RecyclerView carousel = findViewById(R.id.carouselRecycler);
        ImageView btnNext = findViewById(R.id.btnNext);
        ImageView btnPrev = findViewById(R.id.btnPrev);

        List<Store> stores = new ArrayList<>();

        stores.add(new Store("D’Classics Books Baker Street",
                "14 Baker St, Marylebone, London",
                "0.1 km", R.drawable.img_store1));

        stores.add(new Store("D’Classics Books Marylebone Lane",
                "52 Marylebone Ln, London",
                "0.6 km", R.drawable.img_store2));

        stores.add(new Store("D’Classics Books Regent’s Park Corner",
                "23 Park Rd, London",
                "0.9 km", R.drawable.img_store3));

        stores.add(new Store("D’Classics Books Oxford Street West",
                "312 Oxford St, London",
                "1.4 km", R.drawable.img_store4));

        stores.add(new Store("D’Classics Books Soho Literary Hub",
                "18 Greek St, Soho",
                "2.0 km", R.drawable.img_store5));

        carousel.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        carousel.setAdapter(new CarouselAdapter(stores));

        new PagerSnapHelper().attachToRecyclerView(carousel);
        carousel.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager =
                (LinearLayoutManager) carousel.getLayoutManager();

        final int[] currentPosition = {0};

        // NEXT
        btnNext.setOnClickListener(v -> {
            if (currentPosition[0] < stores.size() - 1) {
                currentPosition[0]++;
                carousel.smoothScrollToPosition(currentPosition[0]);
            }
        });

        // PREV
        btnPrev.setOnClickListener(v -> {
            if (currentPosition[0] > 0) {
                currentPosition[0]--;
                carousel.smoothScrollToPosition(currentPosition[0]);
            }
        });

        carousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (pos == -1) {
                        pos = layoutManager.findFirstVisibleItemPosition();
                    }
                    currentPosition[0] = pos;
                }
            }
        });

        // ========================
        // RECOMMENDED BOOKS
        // ========================
        RecyclerView recommended = findViewById(R.id.recommendedRecycler);

        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book(
                "Anna Karenina",
                "Leo Tolstoy",
                "A tragic love story about a married woman...",
                "Fiction",
                "0140449175",
                864,
                "30 Jan. 2003",
                R.drawable.img_anna_karenina
        ));

        bookList.add(new Book(
                "The Stranger",
                "Albert Camus",
                "Follows a detached man who commits a senseless crime...",
                "Fiction",
                "1916700334",
                105,
                "6 Jun. 2024",
                R.drawable.img_the_stranger
        ));

        bookList.add(new Book(
                "Letters To Milena",
                "Franz Kafka",
                "A collection of deeply emotional letters...",
                "Non-Fiction",
                "1784874000",
                272,
                "6 Dec. 2018",
                R.drawable.img_letters_to_milena
        ));

        bookList.add(new Book(
                "No Longer Human",
                "Osamu Dazai",
                "A deeply personal story of a man...",
                "Fiction",
                "0811204812",
                272,
                "1 Feb. 1973",
                R.drawable.img_no_longer_human
        ));

        recommended.setLayoutManager(new GridLayoutManager(this, 2));

        recommended.setAdapter(new BookAdapter(bookList, this));

        recommended.setNestedScrollingEnabled(false);

        int spacing = 16;

        recommended.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);
                int column = position % 2;

                outRect.left = column == 0 ? spacing : spacing / 2;
                outRect.right = column == 1 ? spacing : spacing / 2;
                outRect.top = spacing;
            }
        });
    }

    // ========================
    // BOOK CLICK HANDLER
    // ========================
    @Override
    public void onClick(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);

        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAuthor());
        intent.putExtra("desc", book.getDescription());
        intent.putExtra("category", book.getGenre());
        intent.putExtra("isbn", book.getIsbn());
        intent.putExtra("pages", book.getPages());
        intent.putExtra("date", book.getReleaseDate());
        intent.putExtra("image", book.getImageRes());

        startActivity(intent);
    }

    // ------------------------
    // DROPDOWN LOGIC
    // ------------------------
    private void setupDropdown(String current) {
        View itemHome = findViewById(R.id.itemHome);
        View itemBooks = findViewById(R.id.itemBooks);
        View itemStores = findViewById(R.id.itemStores);

        itemHome.setVisibility(View.VISIBLE);
        itemBooks.setVisibility(View.VISIBLE);
        itemStores.setVisibility(View.VISIBLE);

        if (current.equals("Home")) itemHome.setVisibility(View.GONE);
        if (current.equals("Books")) itemBooks.setVisibility(View.GONE);
        if (current.equals("Stores")) itemStores.setVisibility(View.GONE);
    }

    private void toggleDropdown() {
        if (dropdown.getVisibility() == View.GONE) {
            dropdown.setAlpha(0f);
            dropdown.setTranslationY(-20f);
            dropdown.setVisibility(View.VISIBLE);

            dropdown.animate().alpha(1f).translationY(0f).setDuration(200)
                    .setInterpolator(new DecelerateInterpolator()).start();

            arrow.animate().rotation(180f).setDuration(200);
        } else {
            closeDropdown();
        }
    }

    private void closeDropdown() {
        dropdown.animate().alpha(0f).translationY(-20f).setDuration(150)
                .withEndAction(() -> dropdown.setVisibility(View.GONE)).start();

        arrow.animate().rotation(0f).setDuration(150);
    }

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