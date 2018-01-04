package com.zjy.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 瀹炵幇ApplicationContextAware鎺ュ彛鐨刢ontext娉ㄥ叆鍑芥暟, 灏嗗叾瀛樺叆闈欐�佸彉閲�.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 鍙栧緱瀛樺偍鍦ㄩ潤鎬佸彉閲忎腑鐨凙pplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 浠庨潤鎬佸彉閲廇pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 浠庨潤鎬佸彉閲廇pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBean(clazz);
	}

	/**
	 * 娓呴櫎applicationContext闈欐�佸彉閲�.
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext鏈敞鍏�,璇峰湪applicationContext.xml涓畾涔塖pringContextHolder");
		}
	}

}
