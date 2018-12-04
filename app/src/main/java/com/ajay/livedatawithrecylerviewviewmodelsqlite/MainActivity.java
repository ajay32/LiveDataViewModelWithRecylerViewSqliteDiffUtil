package com.ajay.livedatawithrecylerviewviewmodelsqlite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FavAdapter mFavAdapter;
    private FavouritesViewModel mFavViewModel;
    private List<Favourites> mFav;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // creating object of class that extends AndroidViewModel
        mFavViewModel = ViewModelProviders.of(this).get(FavouritesViewModel.class);


        // this ovserver listener gets called whenevger   mFavs.setValue(newFavs); gets called ..calls come here so you can set data in onChnage method boom
        final Observer<List<Favourites>> favsObserver = new Observer<List<Favourites>>() {
            @Override
            public void onChanged(@Nullable final List<Favourites> updatedList) {
                if (mFav == null) {
                    mFav = updatedList;
                    //updating data here in onchange when data changes or when activty gets launched
                    mFavAdapter = new FavAdapter(mFav,mFavViewModel);
                    recyclerView.setAdapter(mFavAdapter);
                } else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                        @Override
                        public int getOldListSize() {
                            return mFav.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return updatedList.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return mFav.get(oldItemPosition).mId ==
                                    updatedList.get(newItemPosition).mId;
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            Favourites oldFav = mFav.get(oldItemPosition);
                            Favourites newFav = updatedList.get(newItemPosition);
                            return oldFav.equals(newFav);
                        }
                    });
                    result.dispatchUpdatesTo(mFavAdapter);
                    mFav = updatedList;
                    //updating data here in onchange when data changes
                    mFavAdapter = new FavAdapter(mFav,mFavViewModel);
                    recyclerView.setAdapter(mFavAdapter);
                }
            }
        };



        // opening a dialog on clicking fab button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inUrl = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("New favourite")
                        .setMessage("Add a url link below")
                        .setView(inUrl)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = String.valueOf(inUrl.getText());
                                long date = (new Date()).getTime();
                                // VM AND VIEW
                                mFavViewModel.addFav(url, date);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        // this is just setting observer listener in onCreate method
        mFavViewModel.getFavs().observe(this, favsObserver);


    }  // end of on create
}
