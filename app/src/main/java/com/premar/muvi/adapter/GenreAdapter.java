package com.premar.muvi.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.premar.muvi.model.Genre;
import com.premar.muvi.viewholders.GenreViewHolder;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> {
    private Context context;
    private List<Genre> genreList;
    private int rowLayout;

    public GenreAdapter(Context context, List<Genre> genreList, int rowLayout) {
        this.context = context;
        this.genreList = genreList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int position) {
        genreViewHolder.genreName.setText(genreList.get(position).getGenreName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }
}
