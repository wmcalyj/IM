package com.wmcalyj.im.encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.wmcalyj.PrintUtils.PrintUtils;
import com.wmcalyj.im.client.data.FriendsTable;
import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.InstantMessageConstants;

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

	public ArrayList<byte[]> encrypt(String message, String destinationID)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		// Divide original message
		ArrayList<byte[]> dividedMessage = divideMessage(message);
		PrintUtils.printByte("Divided original message: ", dividedMessage);
		ArrayList<byte[]> signedMessage = encryptWithPrivateKey(dividedMessage);
		PrintUtils.printByte("Signed message: ", signedMessage);
		Cipher cipher = Cipher
				.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.DECRYPT_MODE, this.getPublicKey());
		PrintUtils.printByte("Verified message: ",
				Arrays.asList(cipher.doFinal(signedMessage.get(0))));
		ArrayList<byte[]> dividedSignedMessage = divideSignedMessage(signedMessage);
		PrintUtils.printByte("Divided message: ", dividedSignedMessage);
		PublicKey key = FriendsTable.getTable().getPublicKeyForContact(
				destinationID);
		if (key != null) {
			return encryptWithPublicKey(key, dividedSignedMessage);
		}
		return null;
	}

	private ArrayList<byte[]> divideSignedMessage(
			ArrayList<byte[]> signedMessage) {
		ArrayList<byte[]> encryptedDividedMessage = new ArrayList<byte[]>();
		for (int i = 0, j = signedMessage.size(); i < j; i++) {
			byte[] message = signedMessage.get(i);
			int size = signedMessage.get(i).length;
			encryptedDividedMessage.add(Arrays
					.copyOfRange(message, 0, size / 2));
			encryptedDividedMessage.add(Arrays.copyOfRange(message, size / 2,
					size));
		}
		return encryptedDividedMessage;
	}

	private ArrayList<byte[]> divideMessage(String message) {
		try {
			return Serialize.serialize(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt(ArrayList<byte[]> message, String source)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		// Decrypt message first
		ArrayList<byte[]> decryptedMessage = decryptWithPrivateKey(message);
		PrintUtils.printByte("Decrypted message: ", decryptedMessage);
		ArrayList<byte[]> mergedSignedMessage = mergeDecryptedMessage(decryptedMessage);
		PrintUtils.printByte("Merged decrypted message", mergedSignedMessage);
		PublicKey key = FriendsTable.getTable().getPublicKeyForContact(source);
		if (key != null) {
			return decryptWithPublicKey(key, mergedSignedMessage);
		}
		return null;

	}

	private ArrayList<byte[]> mergeDecryptedMessage(
			ArrayList<byte[]> decryptedMessage) {
		// Regroup the message together to get signed message
		ArrayList<byte[]> regroupedDecryptedMessage = new ArrayList<byte[]>();
		for (int i = 0, j = decryptedMessage.size(); i < j; i += 2) {
			byte[] m1 = decryptedMessage.get(i);
			byte[] m2 = decryptedMessage.get(i + 1);
			byte[] c = new byte[m1.length + m2.length];
			System.arraycopy(m1, 0, c, 0, m1.length);
			System.arraycopy(m2, 0, c, m1.length, m2.length);
			regroupedDecryptedMessage.add(c);
		}

		return regroupedDecryptedMessage;

	}

	public ArrayList<byte[]> encryptWithPrivateKey(String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		ArrayList<byte[]> toEncrypt = Serialize.serialize(message);
		return encryptWithPrivateKey(toEncrypt);

	}

	public ArrayList<byte[]> encryptWithPublicKey(PublicKey publicKey,
			String message) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		ArrayList<byte[]> toEncrypt = Serialize.serialize(message);
		return encryptWithPublicKey(publicKey, toEncrypt);
	}

	public ArrayList<byte[]> decryptWithPrivateKey(String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		ArrayList<byte[]> toDecrypt = Serialize.serialize(message);
		return decryptWithPrivateKey(toDecrypt);
	}

	public byte[] decryptWithPublicKey(PublicKey publicKey, String message)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		ArrayList<byte[]> toDecrypt = Serialize.serialize(message);
		return decryptWithPublicKey(publicKey, toDecrypt);
	}

	public ArrayList<byte[]> encryptWithPrivateKey(ArrayList<byte[]> toEncrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher
				.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
		ArrayList<byte[]> encryptedMessage = new ArrayList<byte[]>();
		// The encrypted message has a fixed size of 128 for each block. Divide
		// them by 2 and put it into a new arraylist to return
		for (int i = 0, j = toEncrypt.size(); i < j; i++) {
			encryptedMessage.add(cipher.doFinal(toEncrypt.get(i)));
		}
		return encryptedMessage;

	}

	public ArrayList<byte[]> encryptWithPublicKey(PublicKey publicKey,
			ArrayList<byte[]> toEncrypt) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher
				.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		ArrayList<byte[]> encryptedMessage = new ArrayList<byte[]>();
		for (int i = 0, j = toEncrypt.size(); i < j; i++) {
			encryptedMessage.add(cipher.doFinal(toEncrypt.get(i)));
		}
		return encryptedMessage;
	}

	public ArrayList<byte[]> decryptWithPrivateKey(ArrayList<byte[]> toDecrypt)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher
				.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		ArrayList<byte[]> decryptedMessage = new ArrayList<byte[]>();
		// Decrypt each block and then, regroup them together
		for (int i = 0, j = toDecrypt.size(); i < j; i++) {
			decryptedMessage.add(cipher.doFinal(toDecrypt.get(i)));
		}
		return decryptedMessage;

	}

	public byte[] decryptWithPublicKey(PublicKey publicKey,
			ArrayList<byte[]> toDecrypt) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher
				.getInstance(InstantMessageConstants.CIPHER_INSTANCE);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		int size = 0;
		ArrayList<byte[]> verifiedMessage = new ArrayList<byte[]>();
		for (int i = 0, j = toDecrypt.size(); i < j; i++) {
			byte[] message = toDecrypt.get(i);
			size += message.length;
			verifiedMessage.add(cipher.doFinal(message));
		}
		byte[] decryptedText = new byte[size];
		int pos = 0;
		for (int i = 0, j = verifiedMessage.size(); i < j; i++) {
			byte[] m = verifiedMessage.get(i);
			int lenght = m.length;
			System.arraycopy(m, 0, decryptedText, pos, lenght);
			pos += lenght;
		}

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
