package net.iubris.conti.model;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractContent implements Serializable {

	private static final long serialVersionUID = 2617694184764002585L;
	
	private String what;
	private double howmuch;
	private Date when;
	
	public AbstractContent(String what, double howmuch, Date when) {
		this.what = what;
		this.howmuch = howmuch;
		this.when = when;
	}
	
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public double getHowmuch() {
		return howmuch;
	}
	public void setHowmuch(double howmuch) {
		this.howmuch = howmuch;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	
}
