package com.csnetsoft.view.studentsqlitedb;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csnetsoft.controller.studentsqlitedb.StudentDataSource;
import com.csnetsoft.model.studentsqlitedb.Student;
import com.csnetsoft.model.studentsqlitedb.StudentValidator;

public class StudentUpdate extends AppCompatActivity implements OnClickListener{

    private static final String Tag = "StudentUpdateAcvtivity";
    TextView tvTitle;
    EditText etName, etRollnum, etFatherName, etPhone, etEmail;
    Button btnSave;
    int studentId;
    Student student = null;
    StudentDataSource studentDS = null;
    StudentValidator validator = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);

        // capture Student id from EmployeeDetails to load its data
        Bundle bundle = getIntent().getExtras();
        studentId = bundle.getInt("student_id");

        studentDS = new StudentDataSource(this);
        studentDS.open(true); // open DB connection in writable mode, because of Delete option
        // get Student object by ID
        student = studentDS.getStudent(studentId);

        fillStudentData(student);
        validator = new StudentValidator(this);
    }

    private void fillStudentData(Student student) {
        tvTitle = (TextView) findViewById(R.id.update_student_title);
        tvTitle.setText(getResources().getString(R.string.update)+ " " + student.get_name());

        etName = (EditText) findViewById(R.id.update_etName);
        etName.setTag(getResources().getString(R.string.name));
        etName.setText(student.get_name());

        etRollnum = (EditText) findViewById(R.id.update_etRollnum);
        etRollnum.setTag(getResources().getString(R.string.rollnumber));
        etRollnum.setText(student.get_rollnumber());

        etFatherName = (EditText) findViewById(R.id.update_etFatherName);
        etFatherName.setTag(getResources().getString(R.string.fathername));
        etFatherName.setText(student.get_fathername());

        etPhone = (EditText) findViewById(R.id.update_etPhone);
        etPhone.setTag(getResources().getString(R.string.phone));
        etPhone.setText(student.get_phone());

        etEmail = (EditText) findViewById(R.id.update_etEmail);
        etEmail.setTag(getResources().getString(R.string.email));
        etEmail.setText(student.get_email());

        btnSave = (Button) findViewById(R.id.update_btnSave);
        btnSave.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.equals(btnSave)) {
            updateStudent();
        }
    }

    private void updateStudent() {
        Log.d(Tag, "UpdateStudent");
        if(!validate())
        {
            studentUpdateFailed();
            return;
        }
        else
        {

            if(validator.isValidText(etName) && validator.isValidText(etRollnum) && validator.isValidText(etFatherName)
                    && validator.isValidText(etPhone) && validator.isValidText(etEmail)) {
                student.set_name(etName.getText().toString());
                student.set_rollnumber(etRollnum.getText().toString());
                student.set_fathername(etFatherName.getText().toString());
                student.set_phone(etPhone.getText().toString());
                student.set_email(etEmail.getText().toString());
                updateStudent(student);
            }
        }

    }
    @SuppressLint("InlinedApi")
    private void studentUpdateFailed() {

        new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert).setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Please fill all the blanks field")
                .setMessage(R.string.not_added_student_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int which) {

                    }

                })
                .show();


    }

    private boolean validate() {
        boolean valid = true;
        String name = etName.getText().toString();
        String rollnumber = etRollnum.getText().toString();
        String fathername = etFatherName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String patternNameValidation = "[A-Z][a-z]+( [A-Z][a-z]+)";

        if(rollnumber.isEmpty())
        {
            etRollnum.setError("enter rollnumber");
            return false;
        }
        if(name.isEmpty())
        {
            etName.setError("enter full name");
            return false;
        }

        else if(!java.util.regex.Pattern.matches(patternNameValidation, name))
        {
            etName.setError("enter valid full name ");
            return false;
        }

        if(fathername.isEmpty()||fathername.length()<5)
        {
            etFatherName.setError("enter father name");
            return false;
        }
        else if(!java.util.regex.Pattern.matches(patternNameValidation, fathername))
        {
            etFatherName.setError("enter valid father name");
            return false;
        }

        if(phone.isEmpty())
        {
            etPhone.setError("enter phone number");
            return false;
        }
        else if(!android.util.Patterns.PHONE.matcher(phone).matches())
        {
            etPhone.setError("enter valid phone number");
            return false;
        }
        if(email.isEmpty())
        {
            etEmail.setError("enter email address");
            return false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("enter valid email address");
            return false;
        }

        return valid;
    }



    @SuppressLint("InlinedApi")
    private void updateStudent(Student student) {
        int updated = studentDS.updateStudent(student);
        String alertMessage = "";
        if (updated != -1) {
            alertMessage = getResources().getString(R.string.upated_successfully);
        } else {
            alertMessage = getResources().getString(R.string.updated_fail);
        }
        new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.update_student)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);

                    }
                }).setNegativeButton("Cancle", null)
                .show();

    }


    public void onBackPressed()
    {   super.onBackPressed();
        Intent intent = new Intent(StudentUpdate.this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * need to override onDestroy in order to close DB connection
     */
    @Override
    protected void onDestroy() {
        studentDS.close(); // close DB connection, before calling super.onDestroy()
        super.onDestroy();

    }


}
