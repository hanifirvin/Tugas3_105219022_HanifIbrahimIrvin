package com.example.roomdb;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectItemListener{

    private static final int NEW_KATA_ACTIVITY_REQUEST_CODE = 2;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordViewModel mWordViewModel;
    private KataListAdapter adapterKata;
    private KataViewModel mKataViewModel;
    private Button btnAddKata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        btnAddKata = findViewById(R.id.btn_add_kata);

        final WordListAdapter adapter = new WordListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView recyclerViewKata = findViewById(R.id.recyclerview2);
        adapterKata = new KataListAdapter(this);
        recyclerViewKata.setAdapter(adapterKata);
        recyclerViewKata.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mKataViewModel = ViewModelProviders.of(this).get(KataViewModel.class);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        btnAddKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddKataActivity.class);
                startActivityForResult(intent, NEW_KATA_ACTIVITY_REQUEST_CODE);
            }
        });


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myWord.getWord(), Toast.LENGTH_SHORT).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                        mKataViewModel.deleteKata(myWord.getWord());
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                adapter.setWords(words);
            }
        });
        mKataViewModel.getAllWords().observe(this, new Observer<List<Kata>>() {
            @Override
            public void onChanged(@Nullable final List<Kata> words) {

                adapterKata.setWords(words);
            }
        });


//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(
                getApplicationContext(),
                "RESULT"+resultCode+"  REQ "+requestCode,
                Toast.LENGTH_SHORT).show();
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        }
        else if (requestCode == NEW_KATA_ACTIVITY_REQUEST_CODE && resultCode == 2) {
            Kata word = new Kata(data.getStringExtra(AddKataActivity.EXTRA_REPLY), data.getStringExtra(AddKataActivity.EXTRA_REPLY_WORD));
            mKataViewModel.insert(word);
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Word word) {
        mKataViewModel.getWordByKey(word.getWord()).observe(this, new Observer<List<Kata>>() {
            @Override
            public void onChanged(@Nullable final List<Kata> words) {
                adapterKata.setWords(words);
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}