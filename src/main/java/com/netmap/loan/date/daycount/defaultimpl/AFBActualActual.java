package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class AFBActualActual extends DaycountCalculator {

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		return Days.daysBetween(toDate, fromDate).getDays();
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
			if (temp.getDayOfYear() == 28 && temp.getMonthOfYear() == 2 && temp.dayOfYear().withMaximumValue().getDayOfYear() == 366) {
				temp = temp.plusDays(1);
			}
			if (temp.isAfter(startDate) || temp.equals(startDate)) {
				String[] segment = new String[4];
				segment[0] = String.valueOf(temp.dayOfYear().withMaximumValue().getDayOfYear());
				segment[1] = segment[0];

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
			temp = new LocalDate(startDate.getYear(), 2, 29);
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