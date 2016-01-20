package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.date.Period;
import com.netmap.loan.date.daycount.DaycountCalculator;

public class ISDA30Actual extends DaycountCalculator {

	public List<Period> getPeriods(LocalDate startDate, LocalDate endDate) throws Exception {
		LocalDate hold = new LocalDate(startDate);
		LocalDate end = new LocalDate(endDate);

		List<Period> periods = new ArrayList<Period>();

		while (hold.getYear() < end.getYear()) {
			LocalDate holdEnd = new LocalDate(hold.getYear() + 1, 1, 1);
			periods.add(new Period(new LocalDate(hold), holdEnd));
			hold = new LocalDate(holdEnd);
		}
		periods.add(new Period(hold, end));
		return periods;
	}

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
		List<String[]> listSegments = new ArrayList<String[]>();
		List<Period> subPeriods = getPeriods(startDate, endDate);
		for (int i = 0; i < subPeriods.size(); i++) {
			Period period = (Period) subPeriods.get(i);
			startDate = period.getStartDate();
			endDate = period.getEndDate();
			String[] segment = new String[4];
			segment[0] = String.valueOf(daysBetween(startDate, endDate, false));
			segment[1] = String.valueOf(startDate.dayOfYear().withMaximumValue().getDayOfYear());
			segment[2] = startDate.toString();
			segment[3] = endDate.toString();
			listSegments.add(segment);
		}
		return listSegments;
	}
}