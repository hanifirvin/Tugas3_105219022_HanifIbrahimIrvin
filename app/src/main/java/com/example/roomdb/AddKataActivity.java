package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddKataActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    public static final String EXTRA_REPLY_WORD =
            "REPLY_WORD";

    private EditText mEditWordView, editWordSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kata);

        setContentView(R.layout.activity_add_kata);
        mEditWordView = findViewById(R.id.edit_kata);
        editWordSelected = findViewById(R.id.edit_kata_word);

        final Button button = findViewById(R.id.button_save_kata);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText()) || TextUtils.isEmpty(editWordSelected.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String kata = mEditWordView.getText().toString();
                    String word = editWordSelected.getText().toString();

                    replyIntent.putExtra(EXTRA_REPLY, kata);
                    replyIntent.putExtra(EXTRA_REPLY_WORD, word);

                    setResult(2, replyIntent);
                }
                finish();
            }
        });
    }
}