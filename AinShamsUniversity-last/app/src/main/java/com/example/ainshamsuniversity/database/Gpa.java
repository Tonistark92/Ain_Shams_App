package com.example.ainshamsuniversity.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@androidx.room.Entity(tableName = "gpa_table")
public class Gpa {
    String gpa;
    String term;
    String year;
    String gpaState;
    @PrimaryKey(autoGenerate = true)
    int gpaId;

//    public void setGpaState(String gpaState) {
//        this.gpaState = gpaState;
//    }

//    public String getGpaState() {
//        return gpaState;
//    }
//for query with year and term
//    public Gpa(String gpa, String term) {
//        this.gpa = gpa;
//        this.term = term;
//    }
//for set  gpa  data
    public Gpa(String gpa, String term, String year,String gpaState) {
        this.gpa = gpa;
        this.term = term;
        this.year = year;
        this.gpaState = gpaState;

    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

//    public void setTerm(String term) {
//        this.term = term;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }

    public void setGpaId(int gpaId) {
        this.gpaId = gpaId;
    }

    public String getGpa() {
        return gpa;
    }

    public String getTerm() {
        return term;
    }

    public String getYear() {
        return year;
    }


    public int getGpaId() {
        return gpaId;
    }
}
