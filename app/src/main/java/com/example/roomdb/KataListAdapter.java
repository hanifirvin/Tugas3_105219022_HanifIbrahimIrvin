package com.example.roomdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KataListAdapter extends RecyclerView.Adapter<KataListAdapter.KataViewHolder>{

    private final LayoutInflater mInflater;
    private List<Kata> mWords; // Cached copy of words
    private Context context;
    KataListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public KataListAdapter.KataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        context = parent.getContext();
        return new KataListAdapter.KataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KataViewHolder holder, int position) {
        if (mWords != null) {
            Kata current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            //holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Kata> words){
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class KataViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private KataViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    public Kata getWordAtPosition (int position) {
        return mWords.get(position);
    }
}
