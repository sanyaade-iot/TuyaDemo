
package com.tuya.demo.kafka;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

import javax.security.auth.login.Configuration;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class KafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	public static void main(String[] args) {
		String appKey = "";//填APPKEY
		org.apache.kafka.clients.consumer.KafkaConsumer<String, JSONObject> consumer = null;
		Configuration configuration = Configuration.getConfiguration();
		Configuration.setConfiguration(null);
		try {
			java.security.Security.setProperty("login.configuration.provider", "com.tuya.demo.kafka.SaslConfiguration");

			Properties props = new Properties();
            //根据不同区域填写，不同区域URL参考文档  https://docs.tuya.com/cn/cloudapi/cloud_access/#kafka
			props.put("bootstrap.servers", "kafka.cloud.tuyacn.com:8092");
			props.put("group.id", "");//填申请的时候颁发的GROUP

			InetAddress netAddress = InetAddress.getLocalHost();
			String clientId = "cloud_" + appKey + "_" + netAddress.getHostAddress();
			props.put("client.id", clientId);
			props.put("enable.auto.commit", "true");
			props.put("auto.commit.interval.ms", "1000");
			props.put("session.timeout.ms", "30000");
			props.put("max.poll.records", 1000);
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			//可以自定义JSON格式的序列化
			props.put("value.deserializer", "com.tuya.demo.kafka.JsonDeserializer");

			props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
			props.put("sasl.mechanism", "PLAIN");

			consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, JSONObject>(props);
			consumer.subscribe(Arrays.asList("device-cloud-out-" + appKey));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			Configuration.setConfiguration(configuration);
		}
		if (consumer != null) {
			while (true) {
				ConsumerRecords<String, JSONObject> records = consumer.poll(1000);
				for (ConsumerRecord<String, JSONObject> record : records) {
					logger.info("Received message: (" + record.key() + ", " + record.value() + ") at offset "
							+ record.offset());
				}
			}
		}
	}
}
