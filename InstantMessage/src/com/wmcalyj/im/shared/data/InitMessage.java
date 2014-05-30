package com.wmcalyj.im.shared.data;

import java.security.PublicKey;

public class InitMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7202600817128178514L;
	private final PublicKey publicKey;

	public InitMessage(String sourceID, PublicKey publicKey) {
		this.publicKey = publicKey;
		super.setInitMessage(true);
		super.setSourceID(sourceID);
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

}
