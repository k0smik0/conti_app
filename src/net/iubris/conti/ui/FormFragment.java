package net.iubris.conti.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.iubris.conti.R;
import net.iubris.conti.model.ContentCredit;
import net.iubris.conti.model.ContentExpense;
import net.iubris.conti.model.ContentList;
import net.iubris.conti.service.ContiService;
import net.iubris.conti.ui.model.AbstractRowContent;
import net.iubris.conti.ui.model.RowContentCredit;
import net.iubris.conti.ui.model.RowContentExpense;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class FormFragment extends /*Robo*/Fragment {
	
	public static final String TAG = "FormFragment";
	private static final int CELL_PADDING_IN_DPI = 5;
//	@InjectView private 
	@Bind(R.id.table_expense)
	TableLayout tablelayoutExpense;
//	@InjectView private 
	@Bind(R.id.table_credit)
	TableLayout tablelayoutCredit;

//	@InjectView private 
	@Bind(R.id.button_add_expense)
	Button buttonAddExpense;
//	@InjectView private 
	@Bind(R.id.button_add_credit)
	Button buttonAddCredit;
	
	@Bind(R.id.button_submit)
	Button buttonSubmit;
	
	private Activity activity;
	
//	@InjectResource
	@BindString
	(R.string.what) String what;
//	@InjectResource
	@BindString
	(R.string.howmuch) String howmuch;
//	@InjectResource
	@BindString
	(R.string.when) String when;
//	@InjectResource
	@BindString
	(R.string.where) String where;
	
//	@InjectResource(R.drawable.table_row_header) private 
	@BindDrawable(R.drawable.table_row_header)
	Drawable cellHeaderBackground;
	
//	@InjectResource(R.drawable.cell_shape) private 
	@BindDrawable(R.drawable.cell_border)
	Drawable cellContentBackground;

	private final List<RowContentExpense> rowsContentExpense = new ArrayList<>();
	private final List<RowContentCredit> rowsContentCredit = new ArrayList<>();
	private Resources resources;
	
//	private final Calendar myCalendar = Calendar.getInstance();
	private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";
	private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);

		ButterKnife.bind(this, rootView);
		addInitialRows(rootView);
		
		return rootView;
	}
	private void addInitialRows(View rootView) {
		final EditText expenseWhen = (EditText)rootView.findViewById(R.id.cell_expense_when_0);
		expenseWhen.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	            new DatePickerDialog(activity, buildDatePicker(when), myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        	DialogFragment picker = new DatePickerFragment(expenseWhen);
	        	picker.show(getFragmentManager(), "datePicker");
	        }
	    });
		rowsContentExpense.add(
			new RowContentExpense(
			(EditText)rootView.findViewById(R.id.cell_expense_what_0), 
			(EditText)rootView.findViewById(R.id.cell_expense_howmuch_0),
			expenseWhen,
			(EditText)rootView.findViewById(R.id.cell_expense_where_0) ) );
		
		final EditText creditWhen = (EditText)rootView.findViewById(R.id.cell_credit_when_0);
		creditWhen.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	            new DatePickerDialog(activity, buildDatePicker(when), myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        	DialogFragment picker = new DatePickerFragment(creditWhen);
	        	picker.show(getFragmentManager(), "datePicker");
	        }
	    });
		rowsContentCredit.add(
			new RowContentCredit(
			(EditText)rootView.findViewById(R.id.cell_credit_what_0), 
			(EditText)rootView.findViewById(R.id.cell_credit_howmuch_0),
			creditWhen ) );
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		resources = activity.getResources();
		
		buttonAddExpense.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FormFragment.this.addExpenseHeaderAndRow();
			}
		});
		buttonAddCredit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FormFragment.this.addCreditHeaderAndRow();
			}
		});
		buttonSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					FormFragment.this.submit();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
//	@OnClick(R.id.button_add_expense)
	public void addExpenseHeaderAndRow() {
		addRowHeader(tablelayoutExpense, what, howmuch, when, where);

		EditText howmuch = buildEdittextHowmuch();
		final EditText when = buildEditextWhen();
		RowContentExpense rowContentExpense = new RowContentExpense(buildEditTextForContent(), howmuch, when, buildEditTextForContent() );
//		TableRow rowContent = buildRowContent(rowContentExpense.getEditTexts() );
//		tablelayoutExpense.addView(rowContent);
//		rowsContentExpense.add(rowContentExpense);
		addRowContent(rowContentExpense, tablelayoutExpense, rowsContentExpense);
		
		Log.d("FormFragment","rowsContentExpense: "+rowsContentExpense.size());
	}
//	@OnClick(R.id.button_add_credit)
	public void addCreditHeaderAndRow() {
		addRowHeader(tablelayoutCredit, what, howmuch, when);

		EditText howmuch = buildEdittextHowmuch();
		final EditText when = buildEditextWhen();
		RowContentCredit rowContentCredit = new RowContentCredit(buildEditTextForContent(), howmuch, when );
//		TableRow tablerowContentCredit = buildRowContent(rowContentCredit.getEditTexts() );
//		tablelayoutCredit.addView(tablerowContentCredit);
//		rowsContentCredit.add(rowContentCredit);
		addRowContent(rowContentCredit, tablelayoutCredit, rowsContentCredit);
		
		Log.d("FormFragment","rowsContentCredit: "+rowsContentCredit.size());
	}
	public EditText buildEdittextHowmuch() {
		EditText howmuch = buildEditTextForContent();
		howmuch.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		return howmuch;
	}
	public <RC extends AbstractRowContent> void addRowContent(RC rowContent, TableLayout tableLayout, List<RC> list) {
		TableRow tablerow = buildRowContent(rowContent.getEditTexts() );
		tableLayout.addView(tablerow);
		list.add(rowContent);
	}
	
	public void submit() throws ParseException {
		Log.d(TAG,"submit");
		List<ContentExpense> contentExpenses = new ArrayList<>();
		List<ContentCredit> contentCredits = new ArrayList<>();
		for (RowContentExpense rce: rowsContentExpense) {
			String what = rce.getEdittextWhat().getText().toString();
			double howmuch = Double.parseDouble(rce.getEdittextHowmuch().getText().toString());
			String when = rce.getEdittextWhen().getText().toString();
			String where = rce.getEdittextWhere().getText().toString();
			
			contentExpenses.add( new ContentExpense(what, howmuch, sdf.parse(when), where) );
		}
		for (RowContentCredit rcc: rowsContentCredit) {
			String what = rcc.getEdittextWhat().getText().toString();
			double howmuch = Double.parseDouble(rcc.getEdittextHowmuch().getText().toString());
			String when = rcc.getEdittextWhen().getText().toString();
			
			contentCredits.add( new ContentCredit(what, howmuch, sdf.parse(when)) );
		}
		Log.d(TAG, "passing "+contentExpenses.size()+" contentExpenses, "+contentCredits.size()+" contentCredits");
		
		Intent intent = new Intent(activity, ContiService.class);
		intent.putExtra(ContentList.EXTRA_CONTENT_EXPENSES, new ContentList<ContentExpense>().addAll(contentExpenses));
		intent.putExtra(ContentList.EXTRA_CONTENT_CREDITS, new ContentList<ContentCredit>().addAll(contentCredits));
		activity.startService(intent);
	}
	
	private void addRowHeader(TableLayout tableLayout,  String... strings) {
		TableRow rowHeader = buildRowHeader(strings);
		tableLayout.addView(rowHeader);
	}
	
	private EditText buildEditextWhen() {
		final EditText when = buildEditTextForContent();
		when.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
		when.setFocusable(false);
		when.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	            new DatePickerDialog(activity, buildDatePicker(when), myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	        	DialogFragment picker = new DatePickerFragment(when);
	        	picker.show(getFragmentManager(), "datePicker");
	        }
	    });
		return when;
	}
	
	private TableRow buildRowHeader(String... strings) {
		final TableRow tableRow = new TableRow(activity);
		for (String s: strings) {
			TextView textView = buildTextViewForHeader(s);
			tableRow.addView(textView, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		return tableRow;
	}
	@SuppressWarnings("deprecation")
	private TableRow buildRowContent(List<EditText> editTexts) {
		final TableRow tableRow = new TableRow(activity);
		for (EditText editText: editTexts) {
			editText.setBackgroundDrawable(cellContentBackground);
			tableRow.addView(editText);
		}
		return tableRow;
	}
	
	@SuppressWarnings("deprecation")
	private TextView buildTextViewForHeader(String cellText) {
		final TextView textView = new TextView(activity);
		textView.setBackgroundDrawable(cellHeaderBackground);
		textView.setText(cellText);
		int px = dpiToPx(CELL_PADDING_IN_DPI);
		textView.setPadding(px, px, px, px);
		return textView;
	}
	@SuppressWarnings("deprecation")
	private EditText buildEditTextForContent() {
		final EditText editText = new EditText(activity);
		editText.setBackgroundDrawable(cellContentBackground);
		int px = dpiToPx(CELL_PADDING_IN_DPI);
		editText.setPadding(px, px, px, px);		
		return editText;
	}
	private int dpiToPx(int dpi) {
		int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, resources.getDisplayMetrics());
//		Log.d("dpiToPx",""+px);
		return px;
	}
	

	
	/*private DatePickerDialog.OnDateSetListener buildDatePicker(final EditText editText) {
		return new DatePickerDialog.OnDateSetListener() {
		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel(editText);
		    }
		};
	} 

	private void updateLabel(EditText editText) {
		editText.setText(sdf.format(myCalendar.getTime()));
	}*/

}