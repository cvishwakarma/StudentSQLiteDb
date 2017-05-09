package com.csnetsoft.controller.studentsqlitedb;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.csnetsoft.model.studentsqlitedb.Student;

public class StudentDataSource {
	private SQLiteDatabase db;
	private StudentSQLiteHelper dbHelper;
	private String [] columns;
	
	/**
	 * Constructor
	 * @param context
	 */
	public StudentDataSource(Context context)
	{
		dbHelper = new StudentSQLiteHelper(context);
		columns = dbHelper.COLUMNS;
	}

	/**
	 * Open SQLite Database Connection
	 * @param writable whether is true or false it will open a DB connection on R+W or R mode
	 * throws SQLException
	 */
	public void open(boolean writable) throws SQLException
	{
		if(writable)
		{
			db = dbHelper.getWritableDatabase();
		}
		else
		{
			db = dbHelper.getReadableDatabase();
		}
	}
	/**
	 * Close dbHelper and SQLite database connection
	 * @throws SQLException
	 */
	
	public void close() throws SQLException
	{
		dbHelper.close();
		db.close();
	}
	
	/**
	 * Update a concrete Student Register into database
	 * @param student
	 * @return integer, numbers of lines updated, must be always 1
	 */
	public int updateStudent(Student student)
	{
	int updated = -1;
	if(studentExists(student)){
		ContentValues values = new ContentValues();
		values.put(columns[1], student.get_rollnumber());
		values.put(columns[2], student.get_name());
		values.put(columns[3],student.get_fathername());
		values.put(columns[4], student.get_phone());
		values.put(columns[5], student.get_email());
		String whereClause = columns[0] + " = '"+student.get_id()+"'";
		System.out.println(whereClause);
		updated = db.update(dbHelper.TABLENAME, values, whereClause, null);
		
	}
	return updated;
	}
   
	/**
	 * 
	 * @param student
	 * @return true if register exist
	 */
	private boolean studentExists(Student student) {
		String selection = columns[0]+"="+student.get_id()+"";
		Cursor cur = db.query(dbHelper.TABLENAME, null, selection, null, null, null, null);
		boolean exists = cur.moveToFirst();
		cur.close();
		return exists;
	}
	
	/**
	 * Adds an student register into database
	 * @param student
	 * @return long, if it's different than -1  it means that it has been inserted successfully.
	 */
	public long addStudent(Student student)
	{
		long inserted = -1; 
		if(!studentExists(student))
		{
			ContentValues values = new ContentValues();
			values.put(columns[1], student.get_rollnumber());
			values.put(columns[2], student.get_name());
			values.put(columns[3],student.get_fathername());
			values.put(columns[4], student.get_phone());
			values.put(columns[5], student.get_email());
			inserted = db.insert(dbHelper.TABLENAME, null, values);
			
		}
		return inserted;
		
	}
	
	/**
	 * Returns an Student object by passing its ID as parameter.
	 * @param id
	 * @return student, the whole object with database data
	 */
	
	public Student getStudent(int id)
	{
		Student student = new Student();
		String selection = columns[0]+"="+id+"";
		Cursor cur = db.query(dbHelper.TABLENAME, columns, selection, null, null, null, null);
		cur.moveToFirst();
		while(!cur.isAfterLast())
		{
			student.set_id(cur.getInt(0));
			student.set_rollnumber(cur.getString(1));
			student.set_name(cur.getString(2));
			student.set_fathername(cur.getString(3));
			student.set_phone(cur.getString(4));
			student.set_email(cur.getString(5));
			cur.moveToNext();
			
		}
		cur.close(); //!important to close cursor
		return student;
	}
	
	/**
	 * Delete student on the basis of student id
	 * @param student
	 * @return id of student in integer (On the basis of which you want to delete student records from the database)
	 */
	public int deleteStudent(Student student) {
		int deleted = db.delete(dbHelper.TABLENAME, "id = " + student.get_id(), null);
		return deleted;
	}
	
   /**Retrieve a list of students names
	 * 
	 * @return List <String> of name student values
	 */
	public List<String> getStudentNames()
	{
		List<String> students = new ArrayList<String>();
		String [] cols = {columns[1]}; //name
		Cursor cur = db.query(dbHelper.TABLENAME, cols, null, null, null, null, null);
		cur.moveToFirst();
		while(!cur.isAfterLast())
		{
			students.add(cur.getString(0));
			cur.moveToNext();
			
		}
		cur.close();  //!important to close cursor
		return students;
		
	}
	public List<Student> getStudents()
	{
		List<Student> students = new ArrayList<Student>();
		Cursor cur = db.query(dbHelper.TABLENAME, columns, null, null, null, null, null);
		cur.moveToFirst(); // need to start cursor first...
		while(!cur.isAfterLast()) //while not end of data stored in table...
		{
			Student stu = new Student();
			stu.set_id(cur.getInt(0));
			stu.set_rollnumber(cur.getString(1));
			stu.set_name(cur.getString(2));
			stu.set_fathername(cur.getString(3));
			stu.set_phone(cur.getString(4));
			stu.set_email(cur.getString(5));
		    students.add(stu);
		    cur.moveToNext();
		}
		cur.close(); // !important to close cursor
		return students;
		
	}
	
	
	
}
