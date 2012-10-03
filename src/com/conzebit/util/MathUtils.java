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

public class MathUtils {

	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
	    double radlat1 = Math.PI * lat1 / 180;
    	double radlat2 = Math.PI * lat2 / 180;
    	double theta = lon1 - lon2;
    	double radtheta = Math.PI * theta / 180;
    	double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
    	dist = Math.acos(dist);
    	dist = dist * 180 / Math.PI;
    	dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // metrical
        dist = dist * 1000; // meters
    	return dist;
    }

}
