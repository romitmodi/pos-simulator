package learning.kafka.tutorial.pos.simulator.config;

public class AppConfig {
	public static String APPLICATION_ID = "POS-SIMULATOR";
	public static String BOOTSTRAP_SERVER = "localhost:9092, localhost:9093, localhost:9094";
	public static String INVOICE_TOPIC = "INVOICE-TOPIC";
	public static String OFFSET_STRATEGY_EARLIEST = "earliest";
	public static String GROUP_ID = "POS-CONSUMER-GROUP";
	public static String[] DATA_ELEMENT = { "data/NSE05NOV2018BHAV.csv", "data/NSE06NOV2018BHAV.csv" };
	public static String[] CONSUMER_TOPICS = { INVOICE_TOPIC };
}
