package com.ajay.livedatawithrecylerviewviewmodelsqlite;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private List<Favourites> mFav;
    private FavouritesViewModel mFavViewModel;

    public FavAdapter (List<Favourites> mFav,FavouritesViewModel mFavViewModel)

    {
        this.mFav = mFav;
        this.mFavViewModel = mFavViewModel;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        Favourites favourites = mFav.get(position);
        holder.mTxtUrl.setText(favourites.mUrl);
        holder.mTxtDate.setText((new Date(favourites.mDate).toString()));
    }

    @Override
    public int getItemCount() {
        return mFav.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtUrl;
        TextView mTxtDate;

        FavViewHolder(View itemView) {
            super(itemView);
            mTxtUrl = itemView.findViewById(R.id.tvUrl);
            mTxtDate = itemView.findViewById(R.id.tvDate);
            ImageButton btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Favourites favourites = mFav.get(pos);
                    mFavViewModel.removeFav(favourites.mId);
                }
            });
        }
    }
}