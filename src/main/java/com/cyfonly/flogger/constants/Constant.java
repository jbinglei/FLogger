package com.cyfonly.flogger.constants;
import java.util.HashMap;
import java.util.Map;
import com.cyfonly.flogger.utils.CommUtil;

/**
 * 常量
 * @author yunfeng.cheng
 * @version 2015/10/31
 */
public final class Constant {

	//------------------日志类型
	/** 调试信息  */
	public final static int DEBUG = 0;
	/** 普通信息 */
	public final static int INFO = 1;
	/** 警告信息 */	
	public final static int WARN = 2;
	/** 错误信息 */
	public final static int ERROR = 3;
	/** 严重错误信息 */
	public final static int FATAL = 4;
	
	/**日志级别*/
	public static String CFG_LOG_LEVEL = CommUtil.getConfigByString("LOG_LEVEL","0,1,2,3,4");
	
	/**是否输出到控制台*/
	public static boolean CONSOLE_PRINT = CommUtil.getConfigByBoolean("CONSOLE_PRINT",Boolean.FALSE);
	
	/**当前运行环境的字符集*/
	public static String CFG_CHARSET_NAME = CommUtil.getConfigByString("CHARSET_NAME","UTF-8");
	
	/**日志文件路径*/
	public static String CFG_LOG_PATH = CommUtil.getConfigByString("LOG_PATH","./log");
	
	/* 
	* @Description: 学习该写法 
	* @Author: jbinglei 
	* @Date: 23:01 2018/6/26 
	*/ 
	/** 日志类型描述map*/
	@SuppressWarnings("serial")
	public static Map<String, String> LOG_DESC_MAP = new HashMap<String, String>(){{
		put("0","DEBUG");
		put("1","INFO");
		put("2","WARN");
		put("3","ERROR");
		put("4","FATAL");
	}};
	
}