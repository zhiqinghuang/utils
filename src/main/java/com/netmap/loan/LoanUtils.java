package com.netmap.loan;

import java.math.BigDecimal;

public class LoanUtils {
	public static BigDecimal caculateInterestSegmente(BigDecimal principalRemain, BigDecimal yearRate, BigDecimal discount, int segmentDays){
		BigDecimal interestSegmentMonth = principalRemain.multiply(yearRate).multiply(discount).multiply(new BigDecimal(segmentDays)).divide(new BigDecimal("30"), 8, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
		return interestSegmentMonth;
	}
}