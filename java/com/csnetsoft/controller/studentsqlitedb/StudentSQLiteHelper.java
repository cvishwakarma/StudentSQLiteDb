package com.csnetsoft.controller.studentsqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentSQLiteHelper extends SQLiteOpenHelper {

	private static final String DB = "DBSTUDENTS";
	private static final int DBVERSION = 1;
	public static final String TABLENAME="STUDENTS";
	public static final String[] COLUMNS  ={"id","rollnumber","name","fathername","phone","email"};

	public StudentSQLiteHelper(Context context) {
		super(context, DB, null, DBVERSION);
	}
	
	public StudentSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DB, null, DBVERSION);
	}
    
	// Create table SQL statement
	private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "+ TABLENAME + "(" +
			COLUMNS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
			COLUMNS[1] + " TEXT NOT NULL , " +
			COLUMNS[2] + " TEXT NOT NULL , " +
			COLUMNS[3] + " TEXT NOT NULL , " +
			COLUMNS[4] + " TEXT NOT NULL , " +
			COLUMNS[5] + " TEXT NOT NULL  " +
			");";

	// Drop table SQL statement
	private static final String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS " + TABLENAME;
			
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("Student sqlite HELPER","DB " + DB +" CREATED");
	    db.execSQL(CREATE_TABLE_STUDENTS);
	    Log.e("Student sqlite HELPER","TABLE " + TABLENAME +" CREATED");
	  //insertDefaultData(db); // insert default student data
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE_STUDENTS);
		onCreate(db);
		Log.e("Student sqlite HELPER","DB UPDATED!");
		
		
	}

}
