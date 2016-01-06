package net.iubris.conti.model;

import java.util.Date;

public class ContentCredit extends AbstractContent {

	private static final long serialVersionUID = -941176708354970748L;
	
	public ContentCredit(String what, double howmuch, Date when) {
		super(what, howmuch, when);
	}
	public ContentCredit() {
		super("", 0, null);
	}
}
