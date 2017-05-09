package com.csnetsoft.model.studentsqlitedb;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csnetsoft.controller.studentsqlitedb.StudentDataSource;
import com.csnetsoft.view.studentsqlitedb.R;
import com.csnetsoft.view.studentsqlitedb.StudentDetail;

public class StudentAdapter extends BaseAdapter  {

	Context _context;
	List<Student> _students;
	StudentDataSource _studentDS;
	

	public StudentAdapter(Context context, List<Student> students, StudentDataSource studentDS) {
		this._studentDS = studentDS;
		this._context = context;
		this._students = students;
	}
	

	public int getCount() {
		return _students.size();
	}


	public Object getItem(int location) {
		return _students.get(location);
	}


	public long getItemId(int location) {
		return _students.get(location).get_id();
	}

	
	@SuppressLint("InflateParams")
	public View getView(final int location, View convertView, ViewGroup arg2) {
		View customView = null;
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) 
					_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // got inflater service
			customView = inflater.inflate(R.layout.student_listitem, null);
		} else {
			customView = convertView;
		}
		
		// Set TextView's values
		TextView tvName = (TextView) customView.findViewById(R.id.tv_name);
		TextView tvEmail = (TextView) customView.findViewById(R.id.tv_email);
		TextView tvPhone = (TextView) customView.findViewById(R.id.tv_phone);
		
		tvName.setText(_students.get(location).get_name() + " (" +"MCA"+ ")");
		tvEmail.setText(_students.get(location).get_email());
		tvPhone.setText(_students.get(location).get_phone());
		
		// add onClickListener to view,
		// will start a new activity for showing all Student details
		customView.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {

				Student selected = _students.get(location);
				Intent intent = new Intent(_context, StudentDetail.class);
				intent.putExtra("student_id", selected.get_id());
				_context.startActivity(intent);
			}
		});
		
		// add onLongClickListener to view,
		// shows a ContextMenu (defined in MainActivity) to remove an student from DB
		customView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				return false;
			}}); 
		return customView;
	}
	
	public void refresh(){
		this.notifyDataSetChanged();
	};

	
}
