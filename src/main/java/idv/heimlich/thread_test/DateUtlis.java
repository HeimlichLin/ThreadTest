package idv.heimlich.thread_test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtlis {

	/**
	 * 取得今天日期
	 */
	public static String today() {
		final SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyyMMdd");
		final Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 取得現在時間
	 */
	public static String nowTime() {
		final SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();
		return sdf.format(date);
	}

}