package com.dosvales.vagoapp.util;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ColorBank {
	
	private static final String[] COLORS = {"155, 89, 182",
									"241, 196, 15",
									"46, 204, 113",
									"230, 126, 34",
									"22, 160, 133",
									"231, 76, 60",
									"52, 73, 94",
									"26, 188, 156",
									"142, 68, 173",
									"39, 174, 96",
									"211, 84, 0",
									"149, 165, 166",
									"41, 128, 185",
									"52, 152, 219",
									"243, 156, 18",
									"253, 255, 169",
									"241, 0, 134",
									"187, 100, 100"};
	
	private static int position = 0;
	
	public static void resetPosition() {
		position = 0;
	}
	
	public static String getColor() {
		if (position >= COLORS.length) {
			resetPosition();
		}
		return COLORS[position++];
	}
	
}
