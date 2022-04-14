package com.example.ainshamsuniversity.ui;


import com.example.ainshamsuniversity.R;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Gpa extends AppCompatActivity {

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

    ArrayList<Double> creditGroup;
    ArrayList<String> gradeGroup;
    double noOfHoursSGPA = 0.0;
    EditText Grade;
    EditText Credit;
    TextView gpa;
    ImageButton addCourse;
    Button getGPA;
    EditText current_cgpa;
    EditText NoOfHoursCGPA;
    TextView Cgpa;
    Button getCGPA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);


        creditGroup = new ArrayList<>();
        gradeGroup = new ArrayList<>();

        Grade = findViewById(R.id.grade);
        Credit = findViewById(R.id.credit);

        gpa = findViewById(R.id.GPA);
        addCourse = findViewById(R.id.adding);
        getGPA = findViewById(R.id.button);

        Cgpa = findViewById(R.id.CGPA);
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
                        Cgpa.setText(((noOfHoursSGPA * SGPA) + (noCGPA * cgpa) ) /  ((noOfHoursSGPA ) + (noCGPA)) + "");
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
                Cgpa.setText(gpa.getText());
                creditGroup.clear();
                gradeGroup.clear();

            }
        });
    }

}
