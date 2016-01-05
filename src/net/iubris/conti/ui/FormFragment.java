package net.iubris.conti.ui;

import java.util.ArrayList;
import java.util.List;

import net.iubris.conti.R;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	@BindDrawable(R.drawable.cell_shape)
	Drawable cellContentBackground;

	private final List<RowContentExpense> rowsContentExpense = new ArrayList<>();
	private final List<RowContentCredit> rowsContentCredit = new ArrayList<>();
	private Resources resources;
	
	public FormFragment() {
		Log.d("FormFragment","constructor");
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);

		ButterKnife.bind(this, rootView);
		addInitialRows(rootView);
		
		return rootView;
	}
	private void addInitialRows(View rootView) {
		rowsContentExpense.add(
			new RowContentExpense(
			(EditText)rootView.findViewById(R.id.cell_expense_what_0), 
			(EditText)rootView.findViewById(R.id.cell_expense_howmuch_0),
			(EditText)rootView.findViewById(R.id.cell_expense_when_0),
			(EditText)rootView.findViewById(R.id.cell_expense_where_0) ) );
		rowsContentCredit.add(
			new RowContentCredit(
			(EditText)rootView.findViewById(R.id.cell_credit_what_0), 
			(EditText)rootView.findViewById(R.id.cell_credit_howmuch_0),
			(EditText)rootView.findViewById(R.id.cell_credit_when_0) ) );
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
		
//		addExpenseHeaderAndRow();
//		addCreditHeaderAndRow();
		
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
	}
	
//	@OnClick(R.id.button_add_expense)
	public void addExpenseHeaderAndRow() {
		addRowHeader(/*cellHeaderBackground,*/ tablelayoutExpense, /*activity,*/ what, howmuch, when, where);

		RowContentExpense rowContentExpense = new RowContentExpense(buildEditText(cellContentBackground/*, activity*/), 
				buildEditText(cellContentBackground/*, activity*/), 
				buildEditText(cellContentBackground/*, activity*/),
				buildEditText(cellContentBackground/*, activity*/) );
		TableRow rowContent = buildRowContent(cellContentBackground, /*activity,*/ rowContentExpense.getEditTexts() );
		tablelayoutExpense.addView(rowContent/*, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)*/);
		rowsContentExpense.add(rowContentExpense);
		
		Log.d("FormFragment","rowsContentExpense: "+rowsContentExpense.size());
	}
//	@OnClick(R.id.button_add_credit)
	public void addCreditHeaderAndRow() {
		addRowHeader(/*cellContentBackground,*/ tablelayoutCredit, /*activity,*/ what, howmuch, when);

		RowContentCredit rowContentCredit = new RowContentCredit(buildEditText(cellContentBackground/*, activity*/), 
				buildEditText(cellContentBackground/*, activity*/), 
				buildEditText(cellContentBackground/*, activity*/) );
		TableRow rowContent = buildRowContent(cellContentBackground, /*activity,*/ rowContentCredit.getEditTexts() );
		tablelayoutCredit.addView(rowContent/*, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)*/);
		rowsContentCredit.add(rowContentCredit);
		
		Log.d("FormFragment","rowsContentCredit: "+rowsContentCredit.size());
	}
	
	private abstract class RowContent {
		protected final EditText edittextWhat;
		protected final EditText edittextHowmuch;
		protected final EditText edittextWhen;
		protected List<EditText> list = new ArrayList<>();
		public RowContent(EditText edittextWhat, EditText edittextHowmuch, EditText edittextWhen) {
			this.edittextWhat = edittextWhat;
			list.add(edittextWhat);
			this.edittextHowmuch = edittextHowmuch;
			list.add(edittextHowmuch);
			this.edittextWhen = edittextWhen;
			list.add(edittextWhen);
		}
		public List<EditText> getEditTexts() {
			return list;
		}
	}

	private class RowContentExpense extends RowContent {
		private final EditText edittextWhere;
		public RowContentExpense(EditText edittextWhat, EditText edittextHowmuch, EditText edittextWhen, EditText edittextWhere) {
			super(edittextWhat, edittextHowmuch, edittextWhen);
			this.edittextWhere = edittextWhere;
			list.add(edittextWhere);
		}
	}
	private class RowContentCredit extends RowContent {
		public RowContentCredit(EditText edittextWhat, EditText edittextHowmuch, EditText edittextWhen) {
			super(edittextWhat, edittextHowmuch, edittextWhen);
		}
	}
	
	private void addRowHeader(/*Drawable cellHeaderBackground,*/ TableLayout tableLayout, /*Activity activity,*/ String... strings) {
		TableRow rowHeader = buildRowHeader(/*cellHeaderBackground,*/ /*activity,*/ strings);
		tableLayout.addView(rowHeader/*, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)*/);
	}
	/*private static void addRowContent() {
		RowContentExpense rowContentExpense = buildRowContentExpense();
		TableRow rowContent = buildRowContent(cellContentBackground, activity, rowContentExpense.getTextViews() );
		tablelayoutExpense.addView(rowContent);
		rowsContentExpense.add(rowContentExpense);
	}*/
	
	private TableRow buildRowHeader(/*Drawable cellHeaderBackground,*/ /*Activity activity,*/ String... strings) {
		final TableRow tableRow = new TableRow(activity);
		for (String s: strings) {
			TextView textView = buildTextView(s, cellHeaderBackground/*, activity*/);
			tableRow.addView(textView, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		return tableRow;
	}
	private TableRow buildRowContent(Drawable cellContentBackground, /*Activity activity,*/ List<EditText> editTexts) {
		final TableRow tableRow = new TableRow(activity);
		for (EditText editText: editTexts) {
			editText.setBackgroundDrawable(cellContentBackground);
			tableRow.addView(editText/*, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)*/);
		}
		return tableRow;
	}
	
	private TextView buildTextView(String cellText, Drawable backgroundResourceId/*, Activity activity*/) {
		final TextView textView = new TextView(activity);
		textView.setBackgroundDrawable(backgroundResourceId);
		textView.setText(cellText);
//		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		int px = dpiToPx(CELL_PADDING_IN_DPI);
		textView.setPadding(px, px, px, px);
		return textView;
	}
	private EditText buildEditText(Drawable backgroundResourceId/*, Activity activity*/) {
		final EditText editText = new EditText(activity);
		editText.setBackgroundDrawable(backgroundResourceId);
//		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		int px = dpiToPx(CELL_PADDING_IN_DPI);
		editText.setPadding(px, px, px, px);		
		return editText;
	}
	private int dpiToPx(int dpi) {
		int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, resources.getDisplayMetrics());
//		Log.d("dpiToPx",""+px);
		return px;
	}

}