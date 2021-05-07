package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDestinationActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText name;
    EditText country;
    EditText description;
    EditText date;
    ImageView image;
    Switch visited;
    Button save;
    Button addImage;

    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        name = findViewById(R.id.add_destination_name);
        country = findViewById(R.id.add_destination_country);
        description = findViewById(R.id.add_destination_description);
        date = findViewById(R.id.add_destination_date);
        date.setInputType(InputType.TYPE_NULL);
        visited = findViewById(R.id.add_destination_visited);
        image = findViewById(R.id.add_destination_imageView);
        addImage = findViewById(R.id.button_add_image);

        Boolean edit = getIntent().getBooleanExtra("edit", false);
        if(edit){
            Destination d = Helpers.getDestinationById(getIntent().getIntExtra("id", 0));
            name.setText(d.name);
            country.setText(d.country);
            description.setText(d.description);
            date.setText(d.dateVisited);
            visited.setChecked(d.visited);
            if (d.visited){
                date.setVisibility(View.VISIBLE);
                addImage.setVisibility(View.VISIBLE);
            }

            image.setImageURI(d.image);

            if(d.image != null){
                name.setBackgroundResource(R.color.textBg);
                country.setBackgroundResource(R.color.textBg);
                description.setBackgroundResource(R.color.textBg);
                date.setBackgroundResource(R.color.textBg);
            }
        }


        save = findViewById(R.id.button_add);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateCheck = null;
                if(date != null){
                    dateCheck = date.getText().toString();
                }

                Uri imgCheck = null;
                if(image.getTag() != null){
                    imgCheck = Uri.parse(image.getTag().toString());
                }

                if(!edit){

                    Destination d = new Destination(
                            Helpers.getDestinationId(),
                            name.getText().toString(),
                            country.getText().toString(),
                            description.getText().toString(),
                            visited.isChecked(),
                            dateCheck,
                            imgCheck
                    );
                    if(validate(d)){
                        Helpers.saveDestination(d);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Please fill in name and country", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Destination d = Helpers.getDestinationById(getIntent().getIntExtra("id", 0));
                    d.name = name.getText().toString();
                    d.country = country.getText().toString();
                    d.description = description.getText().toString();
                    d.visited = visited.isChecked();
                    d.dateVisited = dateCheck;
                    d.image = imgCheck;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddDestinationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year + ".");
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        visited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    date.setVisibility(View.VISIBLE);
                    addImage.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                }else {
                    //image.setImageURI(null);
                    image.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    addImage.setVisibility(View.INVISIBLE);
                }

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }

            }

        });


    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDestinationActivity.this);

        builder.setTitle("Warning");
        builder.setMessage("Are you sure? All data will be lost!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){

            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            image.setImageURI(data.getData());
            image.setTag(data.getData().toString());
            name.setBackgroundResource(R.color.textBg);
            country.setBackgroundResource(R.color.textBg);
            description.setBackgroundResource(R.color.textBg);
            date.setBackgroundResource(R.color.textBg);
        }
    }

    private static boolean validate(Destination d){
        if(d.name.length() > 0 && d.country.length() > 0){
            return true;
        }else{
            return false;
        }
    }
}