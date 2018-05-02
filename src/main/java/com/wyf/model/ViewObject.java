package com.wyf.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by w7397 on 17/3/18.
 */
public class ViewObject {

	private Map<String, Object> objs = new HashMap<String, Object>();

	public void set(String key, Object value) {
		objs.put(key, value);
	}

	public Object get(String key) {
		return objs.get(key);
	}
}
