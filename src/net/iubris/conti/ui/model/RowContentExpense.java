package net.iubris.conti.ui.model;

import android.widget.EditText;

public class RowContentExpense extends AbstractRowContent {
	final EditText edittextWhere;
	public RowContentExpense(EditText edittextWhat, EditText edittextHowmuch, EditText edittextWhen, EditText edittextWhere) {
		super(edittextWhat, edittextHowmuch, edittextWhen);
		this.edittextWhere = edittextWhere;
		list.add(edittextWhere);
	}
	public EditText getEdittextWhere() {
		return edittextWhere;
	}
}