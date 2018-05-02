package com.wyf.async;

/**
 * Created by w7397 on 2017/3/23.
 */
public enum EventType {
	LIKE(0),
	COMMENT(1),
	LOGIN(2),
	MAIL(3),
	REGISTER(4);

	private int value;

	EventType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
