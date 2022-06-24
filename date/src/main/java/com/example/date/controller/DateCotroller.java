package com.example.date.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.date.entity.Date;
import com.example.date.mapper.DateMapper;
import com.example.util.date.BeanToMapUtil;
import com.example.util.date.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DateCotroller {

	@Resource
	private DateMapper dateMapper;

	@GetMapping(path = "/test")
	public Map test() {
		HashMap<String, Object> hashMap = new HashMap<>();
		List<Date> dates = dateMapper.selectList(new QueryWrapper<Date>());
		Map<String, Map> map = DateUtil.getDateRange(LocalDateTime.now(), "date", "yyyy-MM-dd", 4);
		dates.forEach(x -> {
			if (map.containsKey(x.getDate())) {
				Map<String, Object> stringObjectMap = BeanToMapUtil.fastJsonBean2Map(x);
				map.put(x.getDate(), stringObjectMap);
			}
		});
		List<Map> collect = map.values().stream().sorted(new Comparator<Map>() {
			@Override
			public int compare(Map o1, Map o2) {
				LocalDate o1parse = LocalDate.parse(o1.get("date").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalDate o2parse = LocalDate.parse(o2.get("date").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				return o1parse.isBefore(o2parse) ? -1 : 1;
			}
		}).collect(Collectors.toList());
		hashMap.put("data", collect);
		hashMap.put("success", 200);
		hashMap.put("message", "OK");
		return hashMap;
	}

	@GetMapping(path = "/testMap")
	public Map testMap() {
		List<Date> dates = dateMapper.selectList(new QueryWrapper<Date>());
		long timeMillis = System.currentTimeMillis();
		List<Map<String, Object>> list = dates.stream().map(x -> {
			Map<String, Object> map = BeanToMapUtil.javaBean2Map(x);
			return map;
		}).collect(Collectors.toList());
		long timeMillis1 = System.currentTimeMillis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("time", timeMillis1 - timeMillis);
		return hashMap;
	}

	@GetMapping(path = "/testMap2")
	public Map testMap2() {
		List<Date> dates = dateMapper.selectList(new QueryWrapper<Date>());
		long timeMillis = System.currentTimeMillis();
		List<Map<String, String>> list = dates.stream().map(x -> {
			Map<String, String> map = BeanToMapUtil.bean2Map(x);
			return map;
		}).collect(Collectors.toList());
		long timeMillis1 = System.currentTimeMillis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("time", timeMillis1 - timeMillis);
		return hashMap;
	}

	@GetMapping(path = "/testMap3")
	public Map testMap3() {
		List<Date> dates = dateMapper.selectList(new QueryWrapper<Date>());
		long timeMillis = System.currentTimeMillis();
		List<Map<String, Object>> list = dates.stream().map(x -> {
			Map<String, Object> map = BeanToMapUtil.fastJsonBean2Map(x);
			return map;
		}).collect(Collectors.toList());
		long timeMillis1 = System.currentTimeMillis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("time", timeMillis1 - timeMillis);
		hashMap.put("date", list);
		return hashMap;
	}

	public static void main(String[] args) {
		System.out.println(2);
	}


}
