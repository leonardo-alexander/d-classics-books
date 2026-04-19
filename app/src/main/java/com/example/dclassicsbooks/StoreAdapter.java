package com.example.dclassicsbooks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    List<Store> storeList;
    Context context;

    public StoreAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStore;
        TextView name, address, distance;
        Button btnMap;

        public ViewHolder(View itemView) {
            super(itemView);

            imgStore = itemView.findViewById(R.id.imgStore);
            name = itemView.findViewById(R.id.txtName);
            address = itemView.findViewById(R.id.txtAddress);
            distance = itemView.findViewById(R.id.txtDistance);
            btnMap = itemView.findViewById(R.id.btnMap);
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = storeList.get(position);

        holder.name.setText(store.name);
        holder.address.setText(store.address);
        holder.distance.setText(store.distance);

        holder.imgStore.setImageResource(store.imageResId);

        holder.btnMap.setOnClickListener(v -> {
            String uri = "geo:0,0?q=" + store.address;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}