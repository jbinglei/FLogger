package com.cyfonly.flogger;

import com.cyfonly.flogger.constants.Constant;
import com.cyfonly.flogger.strategy.LogManager;
import com.cyfonly.flogger.utils.CommUtil;
import com.cyfonly.flogger.utils.TimeUtil;

/**
 * 日志工具类
 * @author yunfeng.cheng
 * @version 2015/10/31
 */
public class FLogger {
	
	private static FLogger instance;
	private static LogManager logManager;

	/*
	* @Description: https://www.cnblogs.com/jiangyi666/p/5665130.html
	* 注意静态代码块和静态方法的区别
	* 有些代码必须在项目启动的时候就执行的时候,需要使用静态代码块,这种代码是主动执行的
	* @Author: jbinglei
	* @Date: 22:42 2018/6/26
	*/
	static {
		logManager = LogManager.getInstance();
	}

	/*
	* @Description:https://blog.csdn.net/wgw335363240/article/details/5854402
	* 当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子
	* @Author: jbinglei
	* @Date: 22:39 2018/6/26
	*/
	public FLogger(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				close();
			}
		}));
	}

	/* 
	* @Description: https://www.cnblogs.com/shipengzhi/articles/2223100.html
	*  注意区分 synchronized 和 static synchronized
	*  synchronized是对类的当前实例进行加锁，
	*  static synchronized是限制线程同时访问jvm中该类的所有实例同时访问对应的代码快。
	* @Author: jbinglei 
	* @Date: 22:35 2018/6/26
	*/ 
	public static synchronized FLogger getInstance(){
		if(instance == null){
			instance = new FLogger();
		}
		return instance;
	}
	
	/**
	 * 写调试日志
	 * @param logMsg 日志内容
	 */
	public void debug(String logMsg){
		writeLog("debug",Constant.DEBUG,logMsg);
	}
	
	/**
	 * 写普通日志
	 * @param logMsg 日志内容
	 */
	public void info(String logMsg){
		writeLog("info",Constant.INFO,logMsg);
	}
	
	/**
	 * 写警告日志
	 * @param logMsg 日志内容
	 */
	public void warn(String logMsg){
		writeLog("warn",Constant.WARN,logMsg);
	}
	
	/**
	 * 写错误日志
	 * @param logMsg 日志内容
	 */
	public void error(String logMsg){
		writeLog("error",Constant.ERROR,logMsg);
	}
	
	/**
	 * 写严重错误日志
	 * @param logMsg 日志内容
	 */
	public void fatal(String logMsg){
		writeLog("fatal",Constant.FATAL,logMsg);
	}
	
	/**
	 * 写系统日志
	 * @param level 日志级别
	 * @param logMsg 日志内容
	 */
	public void writeLog(int level,String logMsg){
		writeLog(Constant.LOG_DESC_MAP.get(String.valueOf(level)).toLowerCase(),level,logMsg);
	}
	
	/**
	 * 写日志
	 * @param logFileName 日志文件名
	 * @param level 日志级别
	 * @param logMsg 日志内容
	 */
	public void writeLog(String logFileName, int level, String logMsg){
		if(logMsg != null && Constant.CFG_LOG_LEVEL.indexOf(""+level) >= 0){
			StringBuffer sb = new StringBuffer(logMsg.length() + 100);
			sb.append("[");
			sb.append(Constant.LOG_DESC_MAP.get(String.valueOf(level)));
			sb.append("] ");
			sb.append(TimeUtil.getFullDateTime());
			sb.append(" [");
			sb.append(Thread.currentThread().getName());
			sb.append("] ");
			sb.append(logMsg);
			sb.append("\n");
			//关键步骤
			logManager.addLog(logFileName, sb);
			
			//错误信息强制打印到控制台；若 CONSOLE_PRINT 配置为 true，也将日志打印到控制台
			if(Constant.ERROR == level || Constant.FATAL == level || Constant.CONSOLE_PRINT){
				try{
					System.out.print(new String(sb.toString().getBytes(Constant.CFG_CHARSET_NAME),Constant.CFG_CHARSET_NAME));
				}catch(Exception e){
					System.out.print(CommUtil.getExpStack(e));
				}
			}
		}
	}
	
	/**
	 * 优雅关闭
	 */
	private void close(){
		logManager.close();
	}
	
}
