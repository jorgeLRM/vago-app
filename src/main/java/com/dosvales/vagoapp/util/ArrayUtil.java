package com.dosvales.vagoapp.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
	
	public static List<Integer> createArray(Integer min, Integer max) {
		List<Integer> array = new ArrayList<Integer>();
		for(int i = min; i<= max; i++) {
			array.add(i);
		}
		return array;
	}

}
