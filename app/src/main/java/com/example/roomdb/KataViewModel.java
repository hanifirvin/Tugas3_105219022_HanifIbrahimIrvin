package com.example.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KataViewModel extends AndroidViewModel {

    private KataRepository mRepository;
    private LiveData<List<Kata>> mAllWords;


    public KataViewModel(@NonNull Application application) {
        super(application);
        mRepository = new KataRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Kata>> getAllWords() { return mAllWords; }

    LiveData<List<Kata>> getWordByKey(String key) { return mRepository.getWordsByKey(key); }

    public void insert(Kata word) { mRepository.insert(word); }

    public void deleteKata(String word) {mRepository.delete(word);}
}
