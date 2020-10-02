package learning.kafka.tutorial.pos.simulator.defaults.serializer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learning.kafka.tutorial.pos.simulator.config.AppConfig;

public class InvoiceProducer {
	private static Logger logger = LoggerFactory.getLogger(InvoiceProducer.class);

	public static void main(String[] args) {

		/**
		 * Kafka base configuration to kick-start kafka Producer
		 */
		logger.info("producer config is started");
		Properties properties = new Properties();
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		logger.info("producer config is completed");

		// Kafka Producer is created
		KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<Integer, String>(properties);

		ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(AppConfig.INVOICE_TOPIC,
				"First Invoice-1");
		logger.info("record is created:-" + record.toString());

		kafkaProducer.send(record);
		logger.info("message is published");

		kafkaProducer.close();
		logger.info("producer is closed");

	}

}
