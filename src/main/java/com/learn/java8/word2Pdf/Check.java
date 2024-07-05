package com.learn.java8.word2Pdf;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * 校验相关方法
 *
 * @author Administrator
 */
public class Check {

	/**
	 * 判断字符串的长度
	 *
	 * @param value  字符串值
	 * @param length 限制的长度
	 * @return
	 */
	public static boolean checkLength(String value, Integer length) {
		if (StringUtils.isNotBlank(value) && value.trim().length() > length) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串的类型是否是数字，支持1,2,3这种方式校验
	 *
	 * @param value 字符串值
	 * @return
	 */
	public static boolean checkIsNumber(String value) {
		if (StringUtils.isNotBlank(value)) {
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			String[] values = value.trim().split(",");
			if (values.length > 1) {
				for (String number : values) {
					boolean bool = pattern.matcher(number).matches();
					if (!bool) {
						return false;
					}
				}
			} else {
				return pattern.matcher(value.trim()).matches();
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否日期格式
	 *
	 * @param value 字符串值
	 * @return
	 */
	public static boolean isValidDate(String value, String format) {
		boolean flag = true;
		if (StringUtils.isNotBlank(value)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				sdf.setLenient(false);
				sdf.parse(value.trim());
			} catch (ParseException e) {
				flag = false;
			}
		}
		return flag;
	}

	public static int strRegexType(String str) {
		int type = 0; //0-未知 1-纯中文 2-含有英文 3-含有数字 4-含有英文+数字
		String regex1 = "[\\u4e00-\\u9fa5]+"; //判断是否为纯中文，是返回true
		String regex2 = ".*[a-zA-z].*";//判断是否含有英文，是返回true
		String regex3 = ".*[0-9].*";//判断是否含有数字，是返回true
		if(str.matches(regex1)){
			type =1;
		}else if(str.matches(regex2) && !str.matches(regex3)){
			type =2;
		}else if(!str.matches(regex2) && str.matches(regex3)){
			type =3;
		}else if(str.matches(regex2) && str.matches(regex3)){
			type =4;
		}
		return type;
	}
}
