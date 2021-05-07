package com.example.travel;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PublicKey;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.crypto.spec.DESedeKeySpec;

public class Helpers {

    public static ArrayList<Destination> data = initDestinations();


    public static ArrayList<Destination> initDestinations() {

        if(data == null){
            //Destination d1 = new Destination(0, "Split", "Croatia", "Test", false, "18.6.2008", null);
            //Destination d2 = new Destination(1, "Split2", "Croatia", "Test", false, "18.6.2008", null);
            //Destination d3 = new Destination(2, "Split3", "Croatia", "Test", false, "18.6.2008", null);
            //Destination d4 = new Destination(3, "Split4", "Croatia", "Test", false, "18.6.2008", null);
            //Destination d5 = new Destination(4, "Split5", "Croatia", "Test", false, "18.6.2008", null);

            ArrayList<Destination> list = new ArrayList<Destination>();
            return list;
        }else {
            return data;
        }


    }

    static class MyAdapter extends ArrayAdapter<Destination> {

        Context context;
        List<Destination> destinations;


        MyAdapter (Context c, List<Destination> destinations) {
            super(c, R.layout.row, R.id.destination_title, destinations);
            this.context = c;
            this.destinations = destinations;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myTitle = row.findViewById(R.id.destination_title);
            TextView myDescription = row.findViewById(R.id.destination_subtitle);

            // now set our resources on views
            myTitle.setText(destinations.get(position).name);
            myDescription.setText(destinations.get(position).country);

            return row;
        }
    }

    public static Destination getDestinationById(int id){
        Destination dest = null;

        for (Destination d : data){
            if (d.id == id){
                dest = d;
            }
        }
        return dest;
    }

    public static void saveDestination(Destination destination){
        data.add(destination);
    }

    public static void deleteDestination(Destination destination){
        data.remove(destination);
    }

    public static Integer getDestinationId() {

        Integer lastId = 0;
        if(data.size() > 0){
            Destination last = data.get(data.size() - 1);
            lastId = last.id;
        }

        return lastId;

    }
}

