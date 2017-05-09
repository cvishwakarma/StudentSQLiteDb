package com.csnetsoft.view.studentsqlitedb;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.csnetsoft.controller.studentsqlitedb.StudentDataSource;
import com.csnetsoft.model.studentsqlitedb.Student;
import com.csnetsoft.model.studentsqlitedb.StudentAdapter;

import java.util.List;

public class MainActivity extends ListActivity {
    StudentAdapter adapter = null;
    List<Student> students = null;
    StudentDataSource studentDS = null;
    TextView records_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        records_tv = (TextView)findViewById(R.id.records_found_tv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentCreateIntent = new Intent(MainActivity.this, StudentCreate.class);
                startActivity(studentCreateIntent);

            }
        });
        studentDS = new StudentDataSource(this);
        studentDS.open(true); // open DB connection in writable mode, because of Delete option
    }
    @Override
    protected void onResume() {
        super.onResume();
        students = studentDS.getStudents(); // retrieve data from DB
        adapter = new StudentAdapter(this, students, studentDS);
        adapter.notifyDataSetChanged();
        this.setListAdapter(adapter);
        if(adapter==null && studentDS == null)
        {
            records_tv.setText("Records Not Found");
        }
        // register the listview for context menu
        this.registerForContextMenu(this.getListView());
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        this.getMenuInflater().inflate(R.menu.student_records_context_menu, menu);

        if (v.getId() == this.getListView().getId()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Student stu = (Student) getListView().getItemAtPosition(info.position);
            menu.setHeaderTitle(stu.get_name());
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Student stu = (Student) getListView().getItemAtPosition(info.position);



        switch (item.getItemId()) {
            case R.id.context_updateStudent:
                Intent intent = new Intent(MainActivity.this, StudentUpdate.class);
                intent.putExtra("student_id", stu.get_id());
                this.startActivity(intent);
                return true;

            // delete employee
            case R.id.context_deleteStudent:
                // build a confirmation dialog
                new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle(R.string.remove_student)
                        .setMessage(this.getResources().getString(R.string.remove_confirm_message) + " " + stu.get_name()+" ?")
                        .setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,android.R.style.Theme_Material_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Deleting");
                                progressDialog.show();

                                new android.os.Handler().postDelayed(new Runnable()
                                {

                                    @Override
                                    public void run() {
                                        int deleted = studentDS.deleteStudent(stu);
                                        if(deleted != 0) {
                                            students.remove(info.position);
                                            adapter.refresh();
                                            progressDialog.cancel();
                                        }
                                        progressDialog.dismiss();


                                    }

                                },1000);
                            }
                        }).setNegativeButton(R.string.cancel, null).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }



    @Override
    protected void onDestroy() {
        studentDS.close(); // close DB connection, before calling super.onDestroy()
        super.onDestroy();
    }

}
