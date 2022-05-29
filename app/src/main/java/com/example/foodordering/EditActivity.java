package com.example.foodordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private String dataKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        dataKey = intent.getStringExtra("Key");

        TextView label = (TextView) findViewById(R.id.editInputHint);
        label.setText("Please enter a new value for " + dataKey + ": ");
    }

    // Exit with returning new input data
    public void save(View view) {
        EditText editText = (EditText) findViewById(R.id.editInput);
        String input = editText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("Key", dataKey);
        intent.putExtra("Input", input);

        setResult(RESULT_OK, intent);
        finish();
    }

    // Exit without saving
    public void cancel(View view) {
        finish();
    }
}