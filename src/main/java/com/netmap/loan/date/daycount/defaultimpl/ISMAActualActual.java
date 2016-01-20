package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.netmap.loan.date.daycount.DaycountCalculator;

public class ISMAActualActual extends DaycountCalculator {

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

		LocalDate refPeriodStart = (notionalStartDate != null ? notionalStartDate : startDate);
		LocalDate refPeriodEnd = (notionalEndDate != null ? notionalEndDate : endDate);

		if (!(refPeriodEnd.isAfter(refPeriodStart) && refPeriodEnd.isAfter(startDate))) {
			throw new Exception("invalid reference period: " + "date 1: " + startDate.toString() + ", date 2: " + endDate.toString() + ", reference period start: " + refPeriodStart.toString() + ", reference period end: " + refPeriodEnd.toString());
		}

		int days = daysBetween(refPeriodStart, refPeriodEnd, false);
		double monthsEstimate = days * (12.0d / 365.0d);
		int months = (int) Math.round(monthsEstimate);

		if (months == 0) {
			refPeriodStart = startDate;
			refPeriodEnd = new LocalDate(startDate);
			refPeriodEnd = refPeriodEnd.plusYears(1);
			months = 12;
		}

		if (endDate.isBefore(refPeriodEnd) || endDate.equals(refPeriodEnd)) {
			if (startDate.isAfter(refPeriodStart) || startDate.equals(refPeriodStart)) {
				int numerator = daysBetween(startDate, endDate, false);
				int denominator = daysBetween(refPeriodStart, refPeriodEnd, false);
				String[] segment = new String[4];
				segment[0] = String.valueOf(numerator);
				segment[1] = String.valueOf(denominator * piPeriods);
				segment[2] = startDate.toString();
				segment[3] = endDate.toString();
				list.add(segment);
				return list;
			} else {
				LocalDate previousRef = new LocalDate(startDate);

				if (endDate.isAfter(refPeriodStart)) {
					list.addAll(getSegmentsInfo(startDate, refPeriodStart, piPeriods, previousRef, refPeriodStart));
					list.addAll(getSegmentsInfo(refPeriodStart, endDate, piPeriods, refPeriodStart, refPeriodEnd));
					return list;
				} else {
					return getSegmentsInfo(startDate, endDate, piPeriods, previousRef, refPeriodStart);
				}
			}
		} else {
			if (!(refPeriodStart.isBefore(startDate) || refPeriodStart.equals(startDate))) {
				throw new Exception("invalid dates: d1 < refPeriodStart < refPeriodEnd < d2");
			}

			list.addAll(getSegmentsInfo(startDate, refPeriodEnd, piPeriods, refPeriodStart, refPeriodEnd));

			int i = 0;
			LocalDate newRefStart, newRefEnd;
			do {
				newRefStart = new LocalDate(refPeriodEnd);
				newRefStart = newRefStart.plusMonths(months * i);
				newRefEnd = new LocalDate(refPeriodEnd);
				newRefEnd = newRefEnd.plusMonths((months * (i + 1)));
				if (endDate.isBefore(newRefEnd)) {
					break;
				} else {
					String[] segment = new String[4];
					int numerator = daysBetween(newRefStart, newRefEnd, false);
					segment[0] = String.valueOf(numerator);
					segment[1] = String.valueOf(numerator * piPeriods);
					segment[2] = newRefStart.toString();
					segment[3] = newRefEnd.toString();
					list.add(segment);
					i++;
				}
			} while (true);
			list.addAll(getSegmentsInfo(newRefStart, endDate, piPeriods, newRefStart, newRefEnd));
			return list;
		}
	}
}