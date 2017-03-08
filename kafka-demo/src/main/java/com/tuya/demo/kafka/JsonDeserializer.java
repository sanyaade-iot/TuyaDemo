
package com.tuya.demo.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class JsonDeserializer<T> implements Deserializer<T> {

	protected static Logger logger = LoggerFactory.getLogger(JsonDeserializer.class);

	public void configure(Map<String, ?> configs, boolean b) {
	}

	public T deserialize(String topic, byte[] data) {
		try {
			T result = (T) JSON.parse(data);
			return result;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public void close() {
	}
}
