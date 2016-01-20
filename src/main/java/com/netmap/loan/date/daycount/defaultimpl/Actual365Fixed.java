package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class Actual365Fixed extends DaycountCalculator {

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		return Days.daysBetween(toDate, fromDate).getDays();
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
		segment[1] = String.valueOf(365);
		segment[2] = startDate.toString();
		segment[3] = endDate.toString();
		list.add(segment);
		return list;
	}
}