package com.example.roomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class KataRepository {
    private KataDao mKataDao;
    private LiveData<List<Kata>> mAllWords;


    KataRepository(Application application) {
        KataRoomDatabase db = KataRoomDatabase.getDatabase(application);
        mKataDao = db.kataDao();
        mAllWords = mKataDao.getAllWords("hewan");
    }

    LiveData<List<Kata>> getAllWords() {
        return mAllWords;
    }

    LiveData<List<Kata>> getWordsByKey(String key) {
        return mKataDao.getAllWords(key);
    }

    public void insert (Kata word) {
        new insertAsyncTask(mKataDao).execute(word);
    }

    public void delete (String keyword) {
        new deleteWordAsyncTask(mKataDao, keyword).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Kata, Void, Void> {

        private KataDao mAsyncTaskDao;

        insertAsyncTask(KataDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Kata... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Kata, Void, Void> {
        private KataDao mAsyncTaskDao;
        private String key;

        deleteWordAsyncTask(KataDao dao,String keyword) {
            mAsyncTaskDao = dao;
            key=keyword;
        }

        @Override
        protected Void doInBackground(final Kata... params) {
            mAsyncTaskDao.deleteWord(key);
            return null;
        }
    }
}
