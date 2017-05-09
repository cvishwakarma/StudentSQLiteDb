package com.csnetsoft.view.studentsqlitedb;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import com.csnetsoft.controller.studentsqlitedb.StudentDataSource;
import com.csnetsoft.model.studentsqlitedb.Student;
import com.csnetsoft.model.studentsqlitedb.StudentAdapter;

import java.util.List;

public class StudentDetail extends AppCompatActivity {

    Student student;
    StudentDataSource studentDS = null;
    List<Student> students = null;
    StudentAdapter adapter = null;
    int studentId;
    private TextView tvId, tvName, tvRollnumber, tvFathername, tvEmail, tvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);



        Bundle bundle = getIntent().getExtras();
        studentId = bundle.getInt("student_id");

        tvId = (TextView) findViewById(R.id.detail_id);
        tvRollnumber = (TextView) findViewById(R.id.detail_rollnum);
        tvName = (TextView) findViewById(R.id.detail_name);
        tvFathername = (TextView) findViewById(R.id.detail_fathername);
        tvEmail = (TextView) findViewById(R.id.detail_email);
        tvPhone = (TextView) findViewById(R.id.detail_phone);

        studentDS = new StudentDataSource(this);
        studentDS.open(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        student = studentDS.getStudent(studentId);
        fillStudentDetailView(student);
    }

    /**
     * @param stu, Student object
     */
    private void fillStudentDetailView(Student stu) {

        tvId.setText(Integer.toString(stu.get_id()));
        tvRollnumber.setText(stu.get_rollnumber());
        tvName.setText(stu.get_name());
        tvFathername.setText(stu.get_fathername());
        tvEmail.setText(stu.get_email());
        tvPhone.setText(stu.get_phone());
    }

    /**
     * need to override onDestroy in order to close DB connection
     */
    @Override
    protected void onDestroy() {
        studentDS.close(); // close DB connection, before calling super.onDestroy()
        super.onDestroy();

    }

    public void onBackPressed()
    {   super.onBackPressed();
        Intent intent = new Intent(StudentDetail.this,MainActivity.class);
        startActivity(intent);

    }


}
