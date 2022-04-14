package com.example.ainshamsuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.database.GpaDataBase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Gpa extends AppCompatActivity {
    ArrayList<Double> creditGroup;
    ArrayList<String> gradeGroup;
    double noOfHoursSGPA = 0.0;
    EditText Grade;
    EditText Credit;
    TextView gpa;
    Button addCourse;
    Button getGPA;
    EditText current_cgpa;
    EditText NoOfHoursCGPA;
    //TextView Cgpa;
    Button getCGPA;
    EditText year,term;
    // room///////////////////////

    RecyclerView gpaRecyclerCiew;
    GpaAdapter gpaAdapter;
//////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        //room
        gpaRecyclerCiew =findViewById(R.id.recyclerview);
        gpaAdapter=new GpaAdapter();
        gpaRecyclerCiew.setAdapter(gpaAdapter);
        final GpaDataBase gpaDataBase =GpaDataBase.getInstance(this);
        gpaDataBase.GpaDao().insertGpa(new com.example.ainshamsuniversity.database.Gpa("2.2","1","2","trakomy"))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        gpaDataBase.GpaDao().getGpas()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<com.example.ainshamsuniversity.database.Gpa>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<com.example.ainshamsuniversity.database.Gpa> gpas) {
                        gpaAdapter.setList(gpas);
                        gpaAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
////////////////////////////////////////////////////
        year=findViewById(R.id.yearid);
        term=findViewById(R.id.termid);
        creditGroup = new ArrayList<>();
        gradeGroup = new ArrayList<>();

        Grade = findViewById(R.id.grade);
        Credit = findViewById(R.id.credit);

        //gpa = findViewById(R.id.GPA);
        addCourse = findViewById(R.id.adding);
        getGPA = findViewById(R.id.getgpa);

        //Cgpa = findViewById(R.id.CGPA);
        current_cgpa = findViewById(R.id.Current_CGPA);
        NoOfHoursCGPA = findViewById(R.id.noOfHoursCGPA);


        getCGPA = findViewById(R.id.getCGPA);

        Toast warningGrade;
        warningGrade = Toast.makeText(this, "Enter the correct grade", Toast.LENGTH_SHORT);

        Toast warning;
        warning = Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT);

        Toast warningCGPA;
        warningCGPA = Toast.makeText(this, "Calculate first the SGPA", Toast.LENGTH_SHORT);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    creditGroup.add(Double.parseDouble(String.valueOf(Credit.getText())));
                    if(getValueOfGrade(String.valueOf(Grade.getText())) == -1 ){
                        warning.show();
                        return;
                    }
                    gradeGroup.add(String.valueOf(Grade.getText()));

                    Credit.setText("");
                    Grade.setText("");
                }
                catch (Exception ex){
                    warning.show();

                }
            }
        });
        getCGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gpa.getText().length() == 0  ) {
                    warningCGPA.show();
                    return;
                }
                else{
                    try {
                        double SGPA =  Double.parseDouble(gpa.getText().toString());
                        double noCGPA =  Double.parseDouble(NoOfHoursCGPA.getText().toString());
                        double cgpa =  Double.parseDouble(current_cgpa.getText().toString());
                        int yearint=1;
                        int termint=1;
                         yearint=Integer.parseInt(year.getText().toString());
                         termint=Integer.parseInt(term.getText().toString());
                        String gpa=String.valueOf(((noOfHoursSGPA * SGPA) + (noCGPA * cgpa) ) /  ((noOfHoursSGPA ) + (noCGPA)));
                        if(yearint!=0&& termint!=0) {
                            gpaDataBase.GpaDao().insertGpa(new com.example.ainshamsuniversity.database.Gpa(gpa, String.valueOf(termint), String.valueOf(yearint), "trakomy"))
                                    .subscribeOn(Schedulers.computation())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }

                                    });
                            gpaDataBase.GpaDao().getGpas()
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<List<com.example.ainshamsuniversity.database.Gpa>>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(@NonNull List<com.example.ainshamsuniversity.database.Gpa> gpas) {
                                            gpaAdapter.setList(gpas);
                                            gpaAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }
                                    });

//                            gpaDataBase.GpaDao().select_1_gpa(String.valueOf(yearint), String.valueOf(termint))
//                                    .subscribeOn(Schedulers.computation())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new SingleObserver<List<com.example.ainshamsuniversity.database.Gpa>>() {
//                                        @Override
//                                        public void onSubscribe(@NonNull Disposable d) {
//
//                                        }
//
//                                        @Override
//                                        public void onSuccess(@NonNull List<com.example.ainshamsuniversity.database.Gpa> gpas) {
//                                            gpaAdapter.setList(gpas);
//                                            gpaAdapter.notifyDataSetChanged();
//                                        }
//
//                                        @Override
//                                        public void onError(@NonNull Throwable e) {
//
//                                        }
//                                    });


                        }


                        //Cgpa.setText(((noOfHoursSGPA * SGPA) + (noCGPA * cgpa) ) /  ((noOfHoursSGPA ) + (noCGPA)) + "");
                        NoOfHoursCGPA.setText("");
                        current_cgpa.setText("");
                    }
                    catch (Exception ex){
                        warning.show();
                    }

                }
            }
        });
        getGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double GPA = 0.0;
                double noOfHours = 0.0;
                if(creditGroup.size() == 0 || gradeGroup.size() == 0  ){
                    warning.show();return;
                }
                for (int i = 0; i < creditGroup.size(); i++) {
                    if (getValueOfGrade(gradeGroup.get(i)) == -1)//if the input is out of range of the grade
                        warningGrade.show();
                    else{
                        GPA += creditGroup.get(i) * getValueOfGrade(gradeGroup.get(i));
                        noOfHours += creditGroup.get(i);
                    }

                }
                noOfHoursSGPA = noOfHours;
                gpa.setText((GPA / noOfHours) +"");
                //Cgpa.setText(gpa.getText());
                creditGroup.clear();
                gradeGroup.clear();

            }
        });
    }


    public double getValueOfGrade(String g) {
        if (g.equalsIgnoreCase("A"))
            return 4;
        if (g.equalsIgnoreCase("A-"))
            return 3.67;
        if (g.equalsIgnoreCase("B+"))
            return 3.33;
        if (g.equalsIgnoreCase("B"))
            return 3;
        if (g.equalsIgnoreCase("C+"))
            return 2.67;
        if (g.equalsIgnoreCase("C"))
            return 2.33;
        if (g.equalsIgnoreCase("D"))
            return 2;
        if (g.equalsIgnoreCase("F"))
            return 0;
        else
            return -1;
    }












}