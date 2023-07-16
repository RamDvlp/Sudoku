package com.example.sudoku;

import java.util.TreeSet;

public class Cell {
	
	private TreeSet<Integer> allowedValues;
	private int value;

	public Cell() {
		this.allowedValues = new TreeSet<Integer>();
		value=0;
	}
	
	public void removeAllowed(int x) {
		allowedValues.remove(x);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public TreeSet<Integer> getAllowedValues() {
		return allowedValues;
	}
	
	public boolean addAllowedValue(int x) {
		return allowedValues.add(x);
	}

	
	
	
	
	
	
	

}
