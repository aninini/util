package com.example.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtil {

	/**
	 *
	 * 获取一段时间内天数集合 yyyy-MM-dd
	 *
	 * @author lizhishen
	 *
	 * @param localDateTime		选择日期
	 * @param name	字段名称
	 * @param pattern		时间格式
	 * @param type		日期范围类型
	 * @return
	 */
	public static Map<String, Map> getDateRange(LocalDateTime localDateTime, String name, String pattern, Integer type) {

		//默认本周
		LocalDateTime start = localDateTime.with(DayOfWeek.MONDAY);
		LocalDateTime end = localDateTime.with(DayOfWeek.SUNDAY);

		switch (type) {
			case 2: // 本月
				start = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
				end = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
				break;
			case 3: //本年
				start = localDateTime.with(TemporalAdjusters.firstDayOfYear());
				end = localDateTime.with(TemporalAdjusters.lastDayOfYear());
				break;
			case 4: //本季度
				Month startMonth = Month.of(localDateTime.getMonth().firstMonthOfQuarter().getValue());
				Month endMonth = Month.of(localDateTime.getMonth().firstMonthOfQuarter().getValue()).plus(2L);
				start = LocalDateTime.of(LocalDate.of(localDateTime.getYear(), startMonth, 1), LocalTime.MIN);
				end = LocalDateTime.of(LocalDate.of(localDateTime.getYear(), endMonth, endMonth.length(localDateTime.toLocalDate().isLeapYear())), LocalTime.MAX);
				break;
		}

		Map<String, Map> map = new LinkedHashMap<>();
		while (start.isBefore(end) || start.equals(end)) {
			String format = start.format(DateTimeFormatter.ofPattern(pattern));
			HashMap<Object, Object> hashMap = new HashMap<>();
			hashMap.put(name, format);
			map.put(format, hashMap);
			start = start.plus(1, ChronoUnit.DAYS);
		}
		return map;
	}



}
