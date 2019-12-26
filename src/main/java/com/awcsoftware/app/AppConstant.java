package com.awcsoftware.app;

import java.time.LocalDateTime;

public class AppConstant {

	public enum TIME_CARD_STATUS {
		Draft, Pending, Reject, Approved,
	}

	public enum RECORD_TYPE {
		Discard, Active,
	}

	public enum TIME_FORMAT_CONST {
		TIME_24_HOUR_FORMAT("HH:mm:ss"), DATE_FORMAT("yyyy-MM-dd"), TIME_ZONE("UTC"),
		DATETIME_FORMAT("yyyy-MM-dd HH:mm:ss");

		private final String workTimeFormat;

		private TIME_FORMAT_CONST(String workTimeFormatParam) {
			workTimeFormat = workTimeFormatParam;
		}

		public String getValue() {
			return workTimeFormat;
		}

		public static LocalDateTime DATETIME_FORMAT(LocalDateTime now) {
			return now;
		}

	}

	public enum WORKING_HOURS {
		Zero(0), One(1), Two(2), Three(3), four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10),
		Number_of_future_Days_allowed(14), Leave_Activity(18), TwentyFour(24);

		private final int workingHour;

		private WORKING_HOURS(int workingHourParam) {
			workingHour = workingHourParam;
		}

		public int getValue() {
			return workingHour;
		}
	}

}
