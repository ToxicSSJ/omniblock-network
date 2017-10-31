/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.utils;

import java.util.Calendar;

public class CalendarUtils {

	public enum WeekDay {
		DOMINGO(1), LUNES(2), MARTES(3), MIERCOLES(4), JUEVES(5), VIERNES(6), SABADO(7);

		private int index = 1;

		WeekDay(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	public static WeekDay getWeekDay(Calendar calendar) {
		return getWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
	}

	public static WeekDay getWeekDay(int index) {
		for (WeekDay wd : WeekDay.values()) {
			if (index == wd.index) {
				return wd;
			}
		}
		return WeekDay.DOMINGO;
	}

}
