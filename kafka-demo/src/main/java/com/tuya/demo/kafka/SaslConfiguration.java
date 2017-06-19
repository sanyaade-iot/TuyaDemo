
package com.tuya.demo.kafka;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

import org.apache.commons.codec.digest.DigestUtils;

public class SaslConfiguration extends Configuration {

	public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
		String appKey = "";//填APP KEY
		String secretKey = "";//APP SECRET
		Map<String, String> options = new HashMap<String, String>();
		options.put("username", appKey);//APP KEY
		options.put("password", DigestUtils.md5Hex(appKey + DigestUtils.md5Hex(secretKey)).substring(8, 24));//MD5(APP KEY+MD5(云端APP SECRET))后，取中间16位
		AppConfigurationEntry entry = new AppConfigurationEntry(
				"org.apache.kafka.common.security.plain.PlainLoginModule",
				AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
		AppConfigurationEntry[] configurationEntries = new AppConfigurationEntry[1];
		configurationEntries[0] = entry;
		return configurationEntries;
	}
}
