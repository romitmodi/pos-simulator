package learning.kafka.tutorial.pos.simulator;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learning.kafka.tutorial.pos.simulator.config.AppConfig;
import learning.kafka.tutorial.pos.simulator.model.LineItem;
import learning.kafka.tutorial.pos.simulator.model.PosInvoice;
import learning.kafka.tutorial.pos.simulator.serializers.JsonSerializer;

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
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		logger.info("producer config is completed");

		// Kafka Producer is created
		KafkaProducer<String, PosInvoice> kafkaProducer = new KafkaProducer<String, PosInvoice>(properties);

		ProducerRecord<String, PosInvoice> record = new ProducerRecord<String, PosInvoice>(AppConfig.INVOICE_TOPIC,
				testData());
		logger.info("record is created:-" + record.toString());

		kafkaProducer.send(record);
		logger.info("message is published");

		kafkaProducer.close();
		logger.info("producer is closed");

	}

	private static PosInvoice testData() {
		PosInvoice invoice = new PosInvoice();
		invoice.setCashierID("Casher-1");
		invoice.setCustomerType("Regular CUstomer");
		invoice.setInvoiceNumber("IN-01");
		invoice.setInvoiceLineItems(new ArrayList<LineItem>());
		LineItem item = new LineItem();
		item.setItemCode("IC-01");
		invoice.getInvoiceLineItems().add(item);
		return invoice;
	}

}
