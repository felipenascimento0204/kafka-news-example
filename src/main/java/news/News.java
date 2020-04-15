package news;

import java.util.ArrayList;
import java.util.List;

public class News {

    private int total;
    private List<Article> articles = new ArrayList<>();

    public List<Article> getArticles() {
	return articles;
    }

    public void setArticles(List<Article> articles) {
	this.articles = articles;
    }

    public int getTotal() {
	return total;
    }

    public void setTotal(int total) {
	this.total = total;
    }
}
