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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    LinearLayout dropdown;
    ImageView arrow;
    LinearLayout selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ------------------------
        // GREETING
        // ------------------------
        TextView greeting = findViewById(R.id.tvGreeting);

        String username = getIntent().getStringExtra("username");
        username = (username == null) ? "Tester" : username;

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

        // ensure dropdown appears above everything
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

        // ------------------------
        // CAROUSEL
        // ------------------------
        RecyclerView carousel = findViewById(R.id.carouselRecycler);

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

        // carousel setup
        carousel.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        carousel.setAdapter(new CarouselAdapter(stores));

        // snap effect (important)
        new PagerSnapHelper().attachToRecyclerView(carousel);
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