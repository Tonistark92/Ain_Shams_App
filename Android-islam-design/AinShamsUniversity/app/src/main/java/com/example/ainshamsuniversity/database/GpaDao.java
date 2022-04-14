package com.example.ainshamsuniversity.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
@androidx.room.Dao
public interface GpaDao {
    @Insert()
    Completable insertGpa(Gpa gpa);

    @Query("select* from gpa_table")
    Single<List<Gpa>> getGpas();

//    @Query("delete from gpa_table where year=:yearid and term =:termid")
//    void delet_1_gpa(int yearid,int termid);

//    @Query("select * from gpa_table where year=:yearid and term =:termid")
//    Single<List<Gpa>> select_1_gpa(String yearid,String termid);

}
