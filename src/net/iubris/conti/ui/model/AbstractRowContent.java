package net.iubris.conti.ui.model;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;

public abstract class AbstractRowContent {
	protected EditText edittextWhat;
	protected EditText edittextHowmuch;
	protected EditText edittextWhen;
	protected List<EditText> list = new ArrayList<>();
	public AbstractRowContent(EditText edittextWhat, EditText edittextHowmuch, EditText edittextWhen) {
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
	public EditText getEdittextWhat() {
		return edittextWhat;
	}
		public EditText getEdittextHowmuch() {
		return edittextHowmuch;
	}	
	public EditText getEdittextWhen() {
		return edittextWhen;
	}	
}