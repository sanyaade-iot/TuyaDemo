
package com.tuya.demo.kafka;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AESBase64Util {

	private static final String AES = "AES";

	public static String decrypt(String encryptedData, String secretKey) throws Exception {
		Key key = new SecretKeySpec(secretKey.getBytes(), AES);
		Cipher c = Cipher.getInstance(AES);
		c.init(2, key);
		byte[] decodedValue = Base64.decodeBase64(encryptedData);
		byte[] decValue = c.doFinal(decodedValue);
		String decryptedValue = StringUtils.newStringUtf8(decValue);
		return decryptedValue;
	}
}
