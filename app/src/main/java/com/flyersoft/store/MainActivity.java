package com.flyersoft.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyersoft.moonreaderpj.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, com.flyersoft.sdk.widget.main.MainActivity.class);
        startActivity(intent);
    }
}
