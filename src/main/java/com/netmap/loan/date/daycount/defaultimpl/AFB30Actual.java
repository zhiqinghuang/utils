package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class AFB30Actual extends DaycountCalculator {

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

		if (startDate.isAfter(endDate)) {
			LocalDate holdCalendar = startDate;
			startDate = endDate;
			endDate = holdCalendar;
		}

		if (startDate.equals(endDate)) {
			return list;
		}

		LocalDate newD2 = endDate;
		LocalDate temp = new LocalDate(endDate);
		while (temp.isAfter(startDate)) {
			temp = new LocalDate(newD2);
			temp = temp.minusYears(1);
			if (temp.getDayOfMonth() == 28 && temp.getMonthOfYear() == Calendar.FEBRUARY && temp.dayOfYear().withMaximumValue().getDayOfYear() == 366) {
				temp.plusYears(1);
			}
			if (temp.isAfter(startDate) || temp.equals(startDate)) {
				String[] segment = new String[4];
				segment[0] = String.valueOf(360);
				segment[1] = String.valueOf(temp.dayOfYear().withMaximumValue().getDayOfYear());
				LocalDate ldTempDate = new LocalDate(temp.getYear(), 1, 1);
				segment[2] = ldTempDate.toString();
				list.add(segment);
				newD2 = temp;
			}
		}
		String[] segment = new String[4];
		segment[1] = String.valueOf(365);

		if (newD2.dayOfYear().withMaximumValue().getDayOfYear() == 366) {
			temp = new LocalDate(newD2.getYear(), 2, 29);
			if (newD2.isAfter(temp) && (startDate.isBefore(temp) || startDate.equals(temp))) {
				segment[1] = String.valueOf(366);
			}
		} else if (startDate.dayOfYear().withMaximumValue().getDayOfYear() == 366) {
			temp = new LocalDate(startDate.getYear(), 2 ,29);
			if (newD2.isAfter(temp) && (startDate.isBefore(temp) || startDate.equals(temp))) {
				segment[1] = String.valueOf(366);
			}
		}
		segment[0] = String.valueOf(daysBetween(startDate, newD2, false));
		segment[2] = startDate.toString();
		segment[3] = newD2.toString();
		list.add(segment);
		return list;
	}
}