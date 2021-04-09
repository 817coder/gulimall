/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.wang.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static com.wang.common.utils.R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static com.wang.common.utils.R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static com.wang.common.utils.R error(int code, String msg) {
		com.wang.common.utils.R r = new com.wang.common.utils.R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static com.wang.common.utils.R ok(String msg) {
		com.wang.common.utils.R r = new com.wang.common.utils.R();
		r.put("msg", msg);
		return r;
	}
	
	public static com.wang.common.utils.R ok(Map<String, Object> map) {
		com.wang.common.utils.R r = new com.wang.common.utils.R();
		r.putAll(map);
		return r;
	}
	
	public static com.wang.common.utils.R ok() {
		return new com.wang.common.utils.R();
	}

	public com.wang.common.utils.R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public Integer getCode() {

		return (Integer) this.get("code");
	}

}
