package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView destinations;

    TextView hint;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Destination> data = Helpers.data;

        destinations = findViewById(R.id.destinations_list);

        hint = findViewById(R.id.textView_hint);

        add = findViewById(R.id.button_add);

        Helpers.MyAdapter adapter = new Helpers.MyAdapter(this, data);

        destinations.setAdapter(adapter);


        if(destinations.getCount() > 0)
        {
            hint.setVisibility(View.VISIBLE);
        }
        destinations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DestinationActivity.class);
                intent.putExtra("pos", i);
                startActivity(intent);
                finish();

            }
        });

        destinations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Warning");
                builder.setMessage("Are you sure? All data will be lost!");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Helpers.deleteDestination((Destination) destinations.getItemAtPosition(pos));
                        finish();
                        startActivity(getIntent());

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDestinationActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}