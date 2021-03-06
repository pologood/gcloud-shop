package com.gcloud.shop.cache;

import java.io.Serializable;

/**
 * @author ChenJin
 * @version V1.0
 * @Title: MemSession
 * @Package com.gcloud.api
 * @Description: 地址Service
 * @date 2016/6/1 13:53
 */
public class CacheConfig implements Serializable {

	private static final long serialVersionUID = -5769223121872969139L;

	/**
	 * 默认的Session过期时间，单位：秒，0表示永不过期
	 */
	private int defaultExpiryTime = 0;

	/**
	 * 默认的Session中的Attribute过期时间，单位：秒，0表示永不过期
	 */
	private int defaultAttributeExpiryTime = 3600;

	/**
	 * 当调用getAttribtue时，是否需要touch一下属性，以便延长有效期时间
	 */
	private boolean get2touch = false;

	/**
	 * 写入登录会话的主站地址
	 */
	private String sessionRootHost = "/";

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getDefaultExpiryTime() {
		return defaultExpiryTime;
	}

	public void setDefaultExpiryTime(int defaultExpiryTime) {
		this.defaultExpiryTime = defaultExpiryTime;
	}

	public int getDefaultAttributeExpiryTime() {
		return defaultAttributeExpiryTime;
	}

	public void setDefaultAttributeExpiryTime(int defaultAttributeExpiryTime) {
		this.defaultAttributeExpiryTime = defaultAttributeExpiryTime;
	}

	public boolean isGet2touch() {
		return get2touch;
	}

	public void setGet2touch(boolean get2touch) {
		this.get2touch = get2touch;
	}

	public String getSessionRootHost() {
		return sessionRootHost;
	}

	public void setSessionRootHost(String sessionRootHost) {
		this.sessionRootHost = sessionRootHost;
	}
}
