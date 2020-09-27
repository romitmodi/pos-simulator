package learning.kafka.tutorial.pos.simulator.deserializers;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;

public class JsonDeserializer<T> implements Deserializer<T> {

	public static final String KEY_CLASS_NAME_CONFIG = "key.class.name";
	public static final String VALUE_CLASS_NAME_CONFIG = "value.class.name";

	private ObjectMapper mapper = new ObjectMapper();
	private Class<T> classType;

	@SuppressWarnings("unchecked")
	public void configure(Map<String, ?> props, boolean isKey) {
		if (isKey)
			classType = (Class<T>) props.get(KEY_CLASS_NAME_CONFIG);
		else
			classType = (Class<T>) props.get(VALUE_CLASS_NAME_CONFIG);
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		if (data != null) {
			try {
				mapper.readValue(data, classType);
			} catch (IOException e) {
				throw new DeserializationException("error occured while serializing :-", e);
			}
		}
		return null;
	}

}
