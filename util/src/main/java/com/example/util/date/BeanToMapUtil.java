package com.example.util.date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanToMapUtil {

	/**
	 * fastJson 方式
	 *
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> fastJsonBean2Map(Object bean) {
		return JSON.parseObject(JSON.toJSONString(bean),
				new TypeReference<Map<String, Object>>() {
				});
	}

	/**
	 * commons-beanutils 方式
	 *
	 * @param bean
	 * @return
	 */
	public static Map<String, String> bean2Map(Object bean) {
		try {
			return BeanUtils.describe(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}

	/**
	 * java 反射方式
	 *
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> javaBean2Map(Object bean) {

		Map<String, Object> map = new HashMap<>();
		try {
			// 获取JavaBean的描述器
			BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
			// 获取属性描述器
			PropertyDescriptor[] pds = b.getPropertyDescriptors();
			// 对属性迭代
			for (PropertyDescriptor pd : pds) {
				// 属性名称
				String propertyName = pd.getName();
				// 属性值,用getter方法获取
				Method m = pd.getReadMethod();
				// 用对象执行getter方法获得属性值
				Object properValue = m.invoke(bean);
				// 把属性名-属性值 存到Map中
				map.put(propertyName, properValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
