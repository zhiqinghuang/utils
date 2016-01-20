package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class Bond30360 extends DaycountCalculator {

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		int dayOfMonth1 = fromDate.getDayOfMonth();
		int dayOfMonth2 = toDate.getDayOfMonth();
		int month1 = fromDate.getMonthOfYear();
		int month2 = toDate.getMonthOfYear();
		int year1 = fromDate.getYear();
		int year2 = toDate.getYear();

		int days = (year2 - year1) * 360 + (month2 - month1) * 30 + (dayOfMonth2 - dayOfMonth1);
		return days;
	}

	public List<String[]> getSegmentsInfo(LocalDate startDate, LocalDate endDate, int piPeriods, LocalDate notionalStartDate, LocalDate notionalEndDate) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		String[] segment = new String[4];
		if (startDate.isAfter(endDate)) {
			LocalDate holdCalendar = startDate;
			startDate = endDate;
			endDate = holdCalendar;
		}
		if (startDate.equals(endDate)) {
			return list;
		}

		segment[0] = String.valueOf(daysBetween(startDate, endDate, false));
		segment[1] = String.valueOf(360);
		segment[2] = startDate.toString();
		segment[3] = endDate.toString();

		list.add(segment);
		return list;
	}
}