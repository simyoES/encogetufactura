/*
 * This file is part of myPlan.
 *
 * Plan is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Plan is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with myPlan.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.conzebit.util;

public class StringUtil {

	
	public static boolean isEmpty(final String string) {
		return (string == null || "".equals(string));
	}
	
	public static boolean isNotEmpty(final String string) {
		return (string != null && !"".equals(string));
	}

	public static boolean isBlank(String string) {
		return (string == null || "".equals(string.trim()));
	}
	
	public static boolean isNotBlank(String string) {
		return (string != null && !"".equals(string.trim()));
	}
	
	public static String trim(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}
	
	public static String toLowerCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toLowerCase();
		}
	}
	
	public static boolean equals(CharSequence a, CharSequence b) {
		boolean ret = true;
		if (a == null) {
			ret = (b == null);
		} else {
			if (b == null) {
				ret = false;
			} else {
				ret = a.equals(b);
			}
		}
		return ret;
	}

}
