package ch.zhaw.tools;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.zhaw.warranty.R;

public class DatePicker extends Activity {
	 
	 private int myYear, myMonth, myDay;
	 static final int ID_DATEPICKER = 0;

	 
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_card);
	        
	        EditText createdAT = (EditText) findViewById(R.id.card_TBcreatedAt);
	        createdAT.setOnClickListener(createATOnClickListener);
	        System.out.println(this.getClass().getName());
	    }
	    
	   private EditText.OnClickListener createATOnClickListener = new EditText.OnClickListener(){

	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    final Calendar c = Calendar.getInstance();
	    myYear = c.get(Calendar.YEAR);
	    myMonth = c.get(Calendar.MONTH);
	    myDay = c.get(Calendar.DAY_OF_MONTH);
	    showDialog(ID_DATEPICKER);
	    }
	    };

	 @Override
	 protected Dialog onCreateDialog(int id) {
	  // TODO Auto-generated method stub
	  switch(id){
	   case ID_DATEPICKER:
	    Toast.makeText(DatePicker.this, 
	      "- onCreateDialog -", 
	      Toast.LENGTH_LONG).show();
	    return new DatePickerDialog(this, myDateSetListener, myYear, myMonth, myDay);
	   default:
	    return null;
	  }
	 }
	    
	 private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener(){
	    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			    // TODO Auto-generated method stub
			    String date = "Year: " + String.valueOf(year) + "\n"
			     + "Month: " + String.valueOf(monthOfYear+1) + "\n"
			     + "Day: " + String.valueOf(dayOfMonth);
			    Toast.makeText(DatePicker.this, date, 
			      Toast.LENGTH_LONG).show();
			   }
	};
}