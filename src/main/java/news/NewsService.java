package news;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewsService {

    private static final String API_NEWS_KEY = "2c8aa54585714edcb2614d10357dc3df";
    private static final String API_NEWS_ENDPOINT = "http://newsapi.org/v2/top-headlines?country=br&apiKey=";
    private static final String PLAIN_TEXT_FILE_PATH = "plain_text_news.txt";

    public News getNews(){

        String newsResponseBody = "";

        try{
            final String uri = API_NEWS_ENDPOINT+API_NEWS_KEY;
            RestTemplate restTemplate = new RestTemplate();
            newsResponseBody = restTemplate.getForObject(uri, String.class);
        } catch (Exception e){
            System.out.println("Error in get news: " + e.getMessage());
            System.out.println("Read news from plain text...");
            newsResponseBody = getNewsFromPLainTextFile();
        }
        return parseResponse(newsResponseBody);
    }

    private String getNewsFromPLainTextFile() {
        try {
            return new String(Files.readAllBytes(Paths.get(PLAIN_TEXT_FILE_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private News parseResponse(String response){
        Gson gson = new Gson();
        return gson.fromJson(response, News.class);
    }
}
