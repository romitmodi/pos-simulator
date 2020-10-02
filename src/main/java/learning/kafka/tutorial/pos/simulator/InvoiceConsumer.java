package learning.kafka.tutorial.pos.simulator;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learning.kafka.tutorial.pos.simulator.config.AppConfig;
import learning.kafka.tutorial.pos.simulator.deserializers.JsonDeserializer;
import learning.kafka.tutorial.pos.simulator.model.PosInvoice;

public class InvoiceConsumer {

	private static Logger logger = LoggerFactory.getLogger(InvoiceConsumer.class);

	public static void main(String[] args) {

		logger.info("create invoice consumer configuration ");
		Properties properties = new Properties();
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, AppConfig.APPLICATION_ID);
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfig.BOOTSTRAP_SERVER);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		// this is needed for utilising generic json deserialiser
		properties.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, PosInvoice.class);

		// To use the group management or offset commit APIs, you must provide a valid
		// group.id in the consumer configuration.
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, AppConfig.GROUP_ID);

		// Configuring offSet property is very critical in consumer, as it define from
		// when message can be consumed by consumer in your system. For example, suppose
		// while producer was publishing the message at that point in time consumer was
		// not up & running. If OFFSET was not configured then all message published
		// will be lost as by default offset only consider latest messages. Thus to
		// cater this scenario OFFSET strategy should be chosen as EARLIEST.
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AppConfig.OFFSET_STRATEGY_EARLIEST);

		KafkaConsumer<String, PosInvoice> consumer = new KafkaConsumer<String, PosInvoice>(properties);
		consumer.subscribe(Arrays.asList(AppConfig.CONSUMER_TOPICS));

		try {
			while (true) {
				ConsumerRecords<String, PosInvoice> records = consumer.poll(Duration.ofMillis(100));
				records.forEach(a -> {
					PosInvoice invoiceData = a.value();
					logger.info(invoiceData.getInvoiceNumber() + "----" + invoiceData.toString());
				});
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
		} finally {
			consumer.close();
		}

	}

}
