package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class IT30360 extends DaycountCalculator {

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		int dd1 = fromDate.getDayOfMonth();
		int dd2 = toDate.getDayOfMonth();
		int mm1 = fromDate.getMonthOfYear();
		int mm2 = toDate.getMonthOfYear();
		int yy1 = fromDate.getYear();
		int yy2 = toDate.getYear();

		if (mm1 == 2 && dd1 > 27)
			dd1 = 30;
		if (mm2 == 2 && dd2 > 27)
			dd2 = 30;

		int days = (yy2 - yy1) * 360 + (30 * (mm2 - mm1 - 1) + Math.max(0, 30 - dd1) + Math.min(30, dd2));
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