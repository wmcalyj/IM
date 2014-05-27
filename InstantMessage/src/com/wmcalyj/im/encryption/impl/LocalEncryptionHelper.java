package com.wmcalyj.im.encryption.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class LocalEncryptionHelper {
	public static byte[] loginEncryption(String password) {
		MessageDigest mda;
		try {
			mda = MessageDigest.getInstance("SHA-512");
			byte[] digest = mda.digest(password.getBytes());
			System.out.println(Hex.encodeHex(digest));
			return digest;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
