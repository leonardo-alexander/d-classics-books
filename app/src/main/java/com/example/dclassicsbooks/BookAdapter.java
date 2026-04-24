package com.example.dclassicsbooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> books;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onClick(Book book);
    }

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView txtAuthor, txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);

        holder.txtAuthor.setText(book.getAuthor());
        holder.txtTitle.setText(book.getTitle());
        holder.imgBook.setImageResource(book.getImageRes());

        holder.itemView.setOnClickListener(v -> listener.onClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
