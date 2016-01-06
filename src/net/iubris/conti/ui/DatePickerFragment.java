package net.iubris.conti.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
//	private final Calendar myCalendar = Calendar.getInstance();
	private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";
	private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
	
	private EditText when;
	
	public DatePickerFragment(EditText when) {
		this.when = when;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH); 

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/*private DatePickerDialog.OnDateSetListener buildDatePicker(final EditText editText) {
		return new DatePickerDialog.OnDateSetListener() {
		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//		        updateLabel(editText);
		    }
		};
	}*/ 

//	private void updateLabel(EditText editText) {
//		editText.setText(sdf.format(myCalendar.getTime()));
//	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String formattedDate = sdf.format(c.getTime());
		when.setText( sdf.format(c.getTime()) );
	}
}
