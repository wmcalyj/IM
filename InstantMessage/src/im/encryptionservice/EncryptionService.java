package im.encryptionservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 
 * @author mengchaow
 * 
 */

public class EncryptionService {

	// Use SHA-512 to hash the password & salt
	// SHA-512 is a one-way hash, it cannot be used in encryption

	// We will use AES to do the encryption according to the following article
	// http://www.javamex.com/tutorials/cryptography/ciphers.shtml

	// We will use an asymmetric-encryption method to exchange key so each party
	// can encrypt/decrypt without sharing keys
	// Diffie–Hellman key exchange
	// This step will be done in webservice and send back to client
	// http://www.cs.ucf.edu/~dmarino/ucf/cis3362/progs/DiffieHellmanBigInt.java

	public void testEncrypt(String s) {
		try {
			// String s = "Hello there. How are you? Have a nice day.";

			// Generate key
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey aesKey = kgen.generateKey();

			// Encrypt cipher
			Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);

			// Encrypt
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(
					outputStream, encryptCipher);
			cipherOutputStream.write(s.getBytes());
			cipherOutputStream.flush();
			cipherOutputStream.close();
			byte[] encryptedBytes = outputStream.toByteArray();
			System.out.println("Encrypt: " + new String(encryptedBytes));

			cipherOutputStream.close();
			byte[] iv = encryptCipher.getIV();
			System.out.println("iv: " + new String(iv));

			// Decrypt cipher
			Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

			// Decrypt
			outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(
					encryptedBytes);
			CipherInputStream cipherInputStream = new CipherInputStream(
					inStream, decryptCipher);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
			}

			System.out.println("Result: "
					+ new String(outputStream.toByteArray()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void decrypt(byte[] iv, byte[] encryptedBytes, SecretKey aesKey) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

			// Decrypt
			outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(
					encryptedBytes);
			CipherInputStream cipherInputStream = new CipherInputStream(
					inStream, decryptCipher);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Result: " + new String(outputStream.toByteArray()));
	}
	// public byte[] encrypt(String text) {
	// MessageDigest digest = MessageDigest.getInstance("SHA-512");
	// byte[] output = digest.digest(text.getBytes());
	//
	// digest.update(salt);
	// digest.update(output);
	// return new BigInteger(1, digest.digest());
	// }
	
	public void myOwnEncrypt()
	{
		Security.addProvider(new BouncyCastleProvider());
	}

}
