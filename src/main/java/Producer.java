import com.google.gson.Gson;
import news.Article;
import news.News;
import news.NewsService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args){

        Properties properties = new Properties();
        properties.put("bootstrap.servers", NewsConfig.BROKER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer kafkaProducer = new KafkaProducer(properties);

        NewsService newsService = new NewsService();
        News news = newsService.getNews();
        Gson gson = new Gson();

        try{
            int key = 1;
            for(Article article : news.getArticles()){
                Thread.sleep(2000);
                kafkaProducer.send(new ProducerRecord(NewsConfig.TOPIC, Integer.toString(key), gson.toJson(article)));
                System.out.println("Send article: " + key);
                key++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaProducer.close();
        }
    }
}
