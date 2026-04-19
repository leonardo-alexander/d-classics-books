package com.example.dclassicsbooks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class StoresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    List<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "StoresActivity loaded");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        recyclerView = findViewById(R.id.storeRecyclerView);

        stores = new ArrayList<>();

        stores.add(new Store(
                "D’Classics Books Baker Street",
                "14 Baker St, Marylebone, London W1U 3AA, UK",
                "0.1 km",
                R.drawable.img_store1
        ));

        stores.add(new Store(
                "D’Classics Books Marylebone Lane",
                "52 Marylebone Ln, London W1U 2NT, UK",
                "0.6 km",
                R.drawable.img_store2
        ));

        stores.add(new Store(
                "D’Classics Books Regent’s Park Corner",
                "23 Park Rd, London NW1 6XN, UK",
                "0.9 km",
                R.drawable.img_store3
        ));

        stores.add(new Store(
                "D’Classics Books Oxford Street West",
                "312 Oxford St, London W1C 1HF, UK",
                "1.4 km",
                R.drawable.img_store4
        ));

        stores.add(new Store(
                "D’Classics Books Soho Literary Hub",
                "18 Greek St, Soho, London W1D 4DS, UK",
                "2.0 km",
                R.drawable.img_store5
        ));

        adapter = new StoreAdapter(this, stores);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}