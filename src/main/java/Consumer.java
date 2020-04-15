import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {

	Properties properties = new Properties();
	properties.put("bootstrap.servers", NewsConfig.BROKER);
        properties.put("group.id", NewsConfig.GROUP_ID);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

	KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
	List<String> topics = new ArrayList<String>();
	topics.add(NewsConfig.TOPIC);
	kafkaConsumer.subscribe(topics);
	try{
	    while (true){
		ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
		for (ConsumerRecord<String, String> record: records){
		    System.out.println(String.format("CONSUMER 1: Topic - %s, Partition - %d, Message: %s", record.topic(), record.partition(), record.value()));
		}
	    }
	}catch (Exception e){
	    System.out.println(e.getMessage());
	}finally {
	    kafkaConsumer.close();
	}
    }

}
