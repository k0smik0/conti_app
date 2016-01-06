package net.iubris.conti.model;

import java.util.Date;

public class ContentExpense extends AbstractContent {

	private static final long serialVersionUID = -565103865551158279L;
	
	private String where;

	public ContentExpense(String what, double howmuch, Date when, String where) {
		super(what, howmuch, when);
		this.where = where;
	}
	
	public ContentExpense() {
		super("", 0, null);
	}

	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
}
