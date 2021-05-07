package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DestinationActivity extends AppCompatActivity {

    TextView name;
    TextView country;
    TextView description;
    TextView date;
    ImageView image;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destnation);

        name = findViewById(R.id.destination_name);
        country = findViewById(R.id.destination_country);
        description = findViewById(R.id.destination_description);
        date = findViewById(R.id.destination_date);
        image = findViewById(R.id.destination_image);

        edit = findViewById(R.id.button_edit);

        Intent intent = getIntent();
        Integer pos = intent.getIntExtra("pos", 0);
        Destination d = Helpers.data.get(pos);

        name.setText(d.name);
        country.setText(d.country);
        description.setText(d.description);
        description.setMovementMethod(new ScrollingMovementMethod());



        if(d.visited){
            if(d.image != null) {
                name.setBackgroundResource(R.color.textBg);
                country.setBackgroundResource(R.color.textBg);
                if(description.length()>0) description.setBackgroundResource(R.color.textBg);
                if(date.length() > 0)date.setBackgroundResource(R.color.textBg);

            }

            date.setVisibility(View.VISIBLE);
            date.setText(d.dateVisited);
            image.setVisibility(View.VISIBLE);
            Uri img = d.image;
            image.setImageURI(img);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDestinationActivity.class);
                intent.putExtra("id", d.id);
                intent.putExtra("edit",true);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    };
}