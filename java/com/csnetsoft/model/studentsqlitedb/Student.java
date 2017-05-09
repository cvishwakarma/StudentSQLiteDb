package com.csnetsoft.model.studentsqlitedb;

public class Student {

	private int _id;
	private String _name;
	private String _fathername;
	private String _rollnumber;
	private String _phone;
	private String _email;



	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_fathername() {
		return _fathername;
	}
	public void set_fathername(String _fathername) {
		this._fathername = _fathername;
	}
	public String get_rollnumber() {
		return _rollnumber;
	}
	public void set_rollnumber(String _rollnumber) {
		this._rollnumber = _rollnumber;
	}
	
	public String get_phone() {
		return _phone;
	}
	public void set_phone(String _phone) {
		this._phone = _phone;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	
}
