package learning.kafka.tutorial.pos.simulator.serializers;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> implements Serializer<T> {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> config, boolean isKey) {
		// Nothing to Configure
	}

	@Override
	public byte[] serialize(String topic, T data) {
		if (data != null) {
			try {
				return mapper.writeValueAsBytes(data);
			} catch (Exception e) {
				throw new SerializationException("error occured while serializing :-", e);
			}
		}
		return null;
	}

}
