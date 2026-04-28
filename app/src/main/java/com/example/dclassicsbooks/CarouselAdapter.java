package com.example.dclassicsbooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    List<Store> stores;

    public CarouselAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = stores.get(position);

        holder.image.setImageResource(store.getImage());
        holder.title.setText(store.getName());
        holder.address.setText(store.getAddress());
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, address;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgStore);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
        }
    }
}
