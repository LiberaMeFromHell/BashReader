package com.example.bashreader.service.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class BashQuote {

    public BashQuote(String quote) {
        this.quote = quote;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("elementPureHtml")
    @Expose
    private String quote;


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
