package net.iubris.conti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContentList<T> implements Serializable {

	private static final long serialVersionUID = 1043721881452618012L;
	
	public static final String EXTRA_CONTENT_EXPENSES = "EXTRA_CONTENT_EXPENSES";
	public static final String EXTRA_CONTENT_CREDITS = "EXTRA_CONTENT_CREDITS";
	
	private final List<T> list = new ArrayList<>();
	
	public void add(T t) {
		list.add(t);
	}
	public ContentList<T> addAll(List<T> ts) {
		list.addAll(ts);
		return this;
	}
	
	public List<T> getAll() {
		return list;
	}
}
