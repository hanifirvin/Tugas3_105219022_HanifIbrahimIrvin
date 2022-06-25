package com.example.roomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Kata.class}, version = 1, exportSchema = false)
public abstract class KataRoomDatabase extends RoomDatabase {

    public abstract KataDao kataDao();
    private static KataRoomDatabase INSTANCE;

    static KataRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            KataRoomDatabase.class, "kata_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static KataRoomDatabase.Callback sRoomDatabaseCallback =
            new KataRoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final KataDao mDao;
        String[] words = {"kuda", "buaya", "ular"};

        PopulateDbAsync(KataRoomDatabase db) {
            mDao = db.kataDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll("hewan");

            for (int i = 0; i <= words.length - 1; i++) {
                Kata word = new Kata(words[i], "hewan");
                mDao.insert(word);
            }
            return null;
        }
    }
}
