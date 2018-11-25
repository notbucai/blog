package cc.ncgame.service;

public interface IArticleService {
	public String queryAllArticle();

	public String queryLatelyArticle();

	public String queryArticlePage(int limit, int offset);

	public String queryOneArticle(int id);

	public String queryYearArticles();

	public String addArticle(String title, String info, String content, String[] tags);

	String updateArticle(int id, String title, String info, String content, String[] tags);

	String deleteArticle(int id);
}
