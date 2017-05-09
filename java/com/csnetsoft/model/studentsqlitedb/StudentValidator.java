package com.csnetsoft.model.studentsqlitedb;


import android.content.Context;
import android.widget.EditText;

import com.csnetsoft.view.studentsqlitedb.R;

public class StudentValidator {

	Context _context;
	public StudentValidator(Context context)
	{
		this._context = context;
	}
	
	public void noticeInputValidation(EditText editText, boolean valid)
	{
		if(valid)
		{
			editText.setBackgroundColor(_context.getResources().getColor(android.R.color.holo_green_light));
			
		}
		else
		{
			editText.setBackgroundColor(_context.getResources().getColor(android.R.color.holo_red_light));
			editText.requestFocus();
			editText.setError("enter"+editText.getTag()+"is not valid");
		}
	}
	
	public boolean isValidText(EditText editText) {		
		
		if(editText.getText().toString().equals("")){
			noticeInputValidation(editText, false);
			return false;
		} 
		else
		{
			noticeInputValidation(editText, true);
		    return true;
		}
	}
}
