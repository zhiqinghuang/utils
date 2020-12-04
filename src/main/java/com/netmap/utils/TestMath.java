package com.netmap.utils;

import java.math.BigDecimal;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import com.numericalmethod.suanshu.number.big.BigDecimalUtils;

public class TestMath {

	public static void main(String[] args) {
		Apfloat a = new Apfloat("2.0125", 16);
		Apfloat b = new Apfloat("4", 16);
		System.out.println(a);
		System.out.println(ApfloatMath.pow(a, b).toString(true));
		Apfloat x = new Apfloat(1);
		Apfloat y = new Apfloat("10", Apfloat.DEFAULT, 16);
		System.out.println(x.add(y.toRadix(10)).toString(true));
		double dd = 1.0125;
		double ee = 4.0;
		System.out.println(Math.pow(dd, ee));
		
		BigDecimal abc = new BigDecimal(String.format("%s", ApfloatMath.pow(new Apfloat(new BigDecimal("2.0"), 16), new Apfloat(new BigDecimal("3.0"), 16))));
		System.out.println(abc);

		Apfloat abcd = ApfloatMath.pow(new Apfloat("2", 16), new Apfloat("4", 16));
		abcd = ApfloatMath.pow(new Apfloat("2", 16), 4);
		System.out.println(abcd);
		
		Apcomplex apcomplexa = new Apcomplex("2");
		Apcomplex apcomplexb = new Apcomplex("3");
		Apcomplex apcomplexc = ApcomplexMath.pow(apcomplexa, apcomplexb);
		System.out.println(apcomplexc);
		System.out.println(BigDecimalUtils.pow(new BigDecimal("2"), new BigDecimal("3")));
	}
}
