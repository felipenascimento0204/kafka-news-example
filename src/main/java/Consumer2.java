import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer2 {

    public static void main(String[] args) {

	Properties properties = new Properties();
	properties.put("bootstrap.servers", KafkaConfig.BROKER);
        properties.put("group.id", KafkaConfig.GROUP_ID);
	properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

	KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
	List<String> topics = new ArrayList<String>();
	topics.add(KafkaConfig.TOPIC);
	kafkaConsumer.subscribe(topics);
	try{
	    while (true){
		ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
		for (ConsumerRecord<String, String> record: records){
		    System.out.println(String.format("CONSUMER 2: Topic - %s, Partition - %d, Message: %s", record.topic(), record.partition(), record.value()));
		}
	    }
	}catch (Exception e){
	    System.out.println(e.getMessage());
	}finally {
	    kafkaConsumer.close();
	}
    }

}
