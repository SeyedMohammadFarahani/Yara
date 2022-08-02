package com.ansarbank.room.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ansarbank.room.R;
import com.ansarbank.room.widget.Btn;

public class IntroActivity extends AppCompatActivity {

    Btn button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        button = findViewById(R.id.introButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
                startActivity(intent);
                IntroActivity.this.finish();
            }
        });

    }

}
