package com.netmap.loan.date;

import java.util.Calendar;

public class Frequency {

	public static Frequency DAILY = new Frequency(Calendar.DAY_OF_YEAR, 1, "1D");

	public static Frequency WEEKLY = new Frequency(Calendar.DAY_OF_YEAR, 7, "1W");

	public static Frequency BI_WEEKLY = new Frequency(Calendar.DAY_OF_YEAR, 14, "2W");

	public static Frequency LUNAR_MONTHLY = new Frequency(Calendar.DAY_OF_YEAR, 28, "1L");

	public static Frequency MONTHLY = new Frequency(Calendar.MONTH, 1, "1M");

	public static Frequency BI_MONTHLY = new Frequency(Calendar.MONTH, 2, "2M");

	public static Frequency QUARTERLY = new Frequency(Calendar.MONTH, 3, "3M");

	public static Frequency SEMI_ANNUALLY = new Frequency(Calendar.MONTH, 6, "6M");

	public static Frequency ANNUALLY = new Frequency(Calendar.YEAR, 1, "1Y");

	public static Frequency ATMATURITY = new Frequency(Calendar.YEAR, 1, "1A");

	private int periodUnit;

	private int periodAmount;

	private String tenorDescriptor;

	private Frequency(int periodUnit, int periodAmount, String tenorDescriptor) {
		this.periodUnit = periodUnit;
		this.periodAmount = periodAmount;
		this.tenorDescriptor = tenorDescriptor;
	}

	public int getPeriodAmount() {
		return periodAmount;
	}

	public int getPeriodUnit() {
		return periodUnit;
	}

	public String getTenorDescriptor() {
		return tenorDescriptor;
	}
}