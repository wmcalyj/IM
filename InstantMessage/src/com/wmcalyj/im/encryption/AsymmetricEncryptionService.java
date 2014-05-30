package com.wmcalyj.im.encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;

public class AsymmetricEncryptionService {
	private static AsymmetricEncryptionService SINGLETON = new AsymmetricEncryptionService();
	private static KeyPair keyPair = null;

	private AsymmetricEncryptionService() {
		if (keyPair == null) {
			try {
				initKeyPair();
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static AsymmetricEncryptionService getService() {
		return SINGLETON;
	}

	private synchronized void initKeyPair() throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(InstantMessageConstants.KEYPAIRGENERATOR_INSTANCE);
		keyPairGenerator.initialize(1024);
		keyPair = keyPairGenerator.genKeyPair();
	}

	public PublicKey getPublicKey() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		if (keyPair == null) {
			initKeyPair();
		}
		return keyPair.getPublic();
	}

	public PrivateKey getPrivateKey() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		if (keyPair == null) {
			initKeyPair();
		}
		return keyPair.getPrivate();
	}

	public byte[] encryptWithPrivateKey(String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] toEncrypt = Serialize.serialize(message);
		return encryptWithPrivateKey(toEncrypt);

	}

	public byte[] encryptWithPublicKey(PublicKey publicKey, String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] toEncrypt = Serialize.serialize(message);
		return encryptWithPublicKey(publicKey, toEncrypt);
	}

	public byte[] decryptWithPrivateKey(String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] toDecrypt = Serialize.serialize(message);
		return decryptWithPrivateKey(toDecrypt);
	}

	public byte[] decryptWithPublicKey(PublicKey publicKey, String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		byte[] toDecrypt = Serialize.serialize(message);
		return decryptWithPublicKey(publicKey, toDecrypt);
	}

	public byte[] encryptWithPrivateKey(byte[] toEncrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
		byte[] cipherText = cipher.doFinal(toEncrypt);
		return cipherText;
	}

	public byte[] encryptWithPublicKey(PublicKey publicKey, byte[] toEncrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipheredText = cipher.doFinal(toEncrypt);
		return cipheredText;
	}

	public byte[] decryptWithPrivateKey(byte[] toDecrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		byte[] decryptedText = cipher.doFinal(toDecrypt);
		return decryptedText;
	}

	public byte[] decryptWithPublicKey(PublicKey publicKey, byte[] toDecrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptedText = cipher.doFinal(toDecrypt);
		return decryptedText;
	}

	public void testEncryption() throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		keyGenerator.init(128);
		Key blowfishKey = keyGenerator.generateKey();

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		keyPair = keyPairGenerator.genKeyPair();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

		byte[] blowfishKeyBytes = blowfishKey.getEncoded();
		System.out.println(new String(blowfishKeyBytes));
		byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
		System.out.println(new String(cipherText));
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

		byte[] decryptedKeyBytes = cipher.doFinal(cipherText);
		System.out.println(new String(decryptedKeyBytes));
		SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes,
				"Blowfish");
	}
}
