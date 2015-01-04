package com.luckysun.paycode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;

public class StringUtils {

	@SuppressLint("NewApi")
	public static boolean isEmpty(String str) {
		return (str == null) || (str.equals(""));
	}

	/**
	 *  身份证号码是否有效
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isIdentityCard(String identity) {
		if (TextUtils.isEmpty(identity)) {
			return false;
		}
		String phone = identity.trim();
		if (phone.length() == 15 || phone.length() == 18) {
			String regExp = "^(\\d{14}|\\d{17})(\\d|[xX])$"; 
			Pattern p = Pattern.compile(regExp);  
			Matcher m = p.matcher(phone); 
			return m.matches();
		} else {
			return false;
		}
	}
	
	/**
	 * 验证码是否有效(6位数字)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValideVerify(String s) {
		return s.matches("^\\d{6}$");
	}

	/**
	 * 验证密码是否有效(6-12位字符)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValidePwd(String s) {
		return s.matches("^\\w{6,16}$");
	}
	/**
	 * 昵称是否有效(4-12位字符)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValideNickName(String s) {
		return s.matches("^\\w{2,20}$");
	}

	/**
	 * 判断手机号是否合法
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
　　	 * 联通：130、131、132、152、155、156、185、186
　　  * 电信：133、153、180、189、（1349卫通）
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isMobileLawful(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber)) {
			return false;
		}
		String phone = phoneNumber.trim();
		if (TextUtils.isDigitsOnly(phone) && phone.length() == 11) {
			String regExp = "^(13[0-9]|15[01]|15[2-9]|18[0-9]|17[6-8])\\d{8}$"; 
			Pattern p = Pattern.compile(regExp);  
			Matcher m = p.matcher(phone); 
			return m.matches();
		} else {
			return false;
		}
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
	/**
	 * 使用正则表达式讲会员卡号加入空格
	 * 
	 * @param s
	 * @return
	 */
	public static String addSpaceToMemberCardNumber(String s) {
		if (!TextUtils.isEmpty(s)) {
			StringBuffer strBuffer = new StringBuffer();
//			s = s.replaceAll(".{4}(?!$)", "$0 ");
			char[] bytes = s.toCharArray();
			for (int i = 0; i < bytes.length; i++) {
				if(i == 4 || i==8 || i==12){
					strBuffer.append("  ");
				}
				strBuffer.append(bytes[i]);
			}
			return strBuffer.toString();
//			return s;
		}else{
			return s;
		}
		
	}

	/**
	 * Double类型保留一位小数，返回String类型（注意四舍五入的影响）
	 */
	public static String formatDoubleToString(double d) {
		DecimalFormat df = new DecimalFormat("#0.0");
		return df.format(d);
	}

	/**
	 * Double类型保留一位小数，返回double类型（四舍五入）
	 */
	public static double formatDouble(double d) {
		return formatDouble(d,1);
	}
	/**
	 * Double类型保留指定位数的小数，返回double类型（四舍五入）
	 * newScale 为指定的位数
	 */
	public static double formatDouble(double d,int newScale) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String formatString(String d1, int newScale){
		DecimalFormat f = null;
		double d = Double.valueOf(d1);
		switch (newScale) {
		case 0:
			f = new DecimalFormat("###0");
			break;
		case 1:
			d = formatDouble(d,1);
			f = new DecimalFormat("###0.0");
			break;
		case 2:
			d = formatDouble(d,2);
			f = new DecimalFormat("###0.00");
			break;
		default:
			break;
		}
		return (f.format(d));
	}
	
	/**
	 * 保留小数（注意四舍五入的影响）
	 * 
	 * @param d , decimal
	 * @return
	 */
	public static String getDecimal(double d, int decimal) {
		DecimalFormat f = null;
		switch (decimal) {
		case 0:
			f = new DecimalFormat("###0");
			break;
		case 1:
			d = formatDouble(d,1);
			f = new DecimalFormat("###0.0");
			break;
		case 2:
			d = formatDouble(d,2);
			f = new DecimalFormat("###0.00");
			break;
		default:
			break;
		}
		return (f.format(d));
	}

	/**
	 * http://10.10.90.96/appdownload/ca.htm?table_number=A108&shop_id=1003
	 * 从URL中获取指定参数的值
	 * 
	 * @param url
	 * @param paramName
	 * @return
	 */
	public static String getParamFromURL(String url, String paramName) {
		String value = "";
		if (url != null && !"".equals(url.trim()) && url.contains(paramName)) {
			int start = url.indexOf("?");
			String t = url.substring(start + 1);
			String[] s = t.split("&");
			if (s != null && s.length > 0) {
				for (int i = 0; i < s.length; i++) {
					if (s[i].contains(paramName) && s[i].contains("=")) {
						value = s[i].substring(s[i].indexOf("=") + 1);
						break;
					}
				}
			}
		}
		return value.trim();
	}

	/**
	 * 生成URL
	 * 
	 * @param maps
	 * @return
	 */
	public static String getUrlParamsFromHashMap(HashMap<String, Object> maps) {
		StringBuilder urlParams = new StringBuilder();
		if (maps != null && maps.size() > 0) {
			urlParams.append("?");
			for (java.util.Map.Entry<String, Object> entry : maps.entrySet()) {
				urlParams.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
			if (urlParams.toString().endsWith("&")) {
				urlParams.deleteCharAt(urlParams.length() - 1);
			}
		}
		return urlParams.toString();
	}

	/**
	 * 获取AppKey
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

}
