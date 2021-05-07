package com.example.travel;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.Date;

public class Destination {

    public Integer id;
    public String name;
    public String country;
    public String description;
    public Boolean visited;
    @Nullable
    public String dateVisited;
    Uri image;

    public Destination(Integer id, String name, String country, String description, Boolean visited, String dateVisited, Uri image){
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.visited = visited;
        this.dateVisited = dateVisited;
        this.image = image;
    }



}