package com.wmcalyj.im.shared.data;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -591804385684943848L;
	private String from;
	private String to;
	private String message;

	public Message(String from, String to, String message) {
		this.from = from;
		this.to = to;
		this.message = message;
	}

	public Message(String from, String message) {
		this.from = from;
		this.message = message;
	}

	public Message(String from) {
		this.from = from;
	}

	public Message() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
