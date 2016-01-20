package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class ISDA30360 extends DaycountCalculator {

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		int dayOfMonth1 = fromDate.getDayOfMonth();
		int dayOfMonth2 = toDate.getDayOfMonth();
		int month1 = fromDate.getMonthOfYear();
		int month2 = toDate.getMonthOfYear();
		int year1 = fromDate.getYear();
		int year2 = toDate.getYear();

		boolean isLastDayMonth1 = dayOfMonth1 == fromDate.dayOfMonth().withMaximumValue().getDayOfMonth();
		boolean isLastDayOfFebruary2 = dayOfMonth2 == toDate.dayOfMonth().withMaximumValue().getDayOfMonth() && month2 == Calendar.FEBRUARY;

		if (isLastDayMonth1) {
			dayOfMonth1 = 30;
		}

		if (dayOfMonth2 == 31) {
			dayOfMonth2 = 30;
		}

		if (isLastDayOfFebruary2 && !isTerminationDate) {
			dayOfMonth2 = 30;
		}

		int numerator = 360 * (year2 - year1);
		numerator += 30 * (month2 - month1);
		numerator += dayOfMonth2 - dayOfMonth1;
		return numerator;
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