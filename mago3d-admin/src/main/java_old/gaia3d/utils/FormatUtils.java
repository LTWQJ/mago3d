package gaia3d.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理数据格式
 * @author jeongdae
 *
 */
public class FormatUtils {
	
	public static final String VIEW_YEAR_MONTH_DAY_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String YEAR_MONTH_DAY = "yyyyMMdd";
	public static final String YEAR_MONTH_DAY_TIME14 = "yyyyMMddHHmmss";
	public static final String YEAR_MONTH_DAY_TIME12 = "yyyyMMddHHmm";
	
	public static final SimpleDateFormat viewDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 显示至年月日时间的秒
	 * @param date
	 * @return
	 */
	public static String getDayFormat(Date date) {
		return getDayFormat(date, VIEW_YEAR_MONTH_DAY_TIME);
	}

	/**
	 * 以输入接收模式显示
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDayFormat(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	public static String getViewDateyyyyMMddHHmmss(Date date) {
		return viewDateFormat.format(date);
		
	}
}
