package cc.ncgame.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cc.ncgame.bean.Article;

public interface IArticleDao {
	/**
	 * 增加文章
	 * 
	 * @param article
	 * @return 文章id
	 * @throws SQLException
	 */
	public int addArticle(Article article) throws SQLException;

	/**
	 * 删除指定文章
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteArticle(int id) throws SQLException;

	/**
	 * 查询所有数据
	 * 
	 * @return 所有文章数据
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryAllArticle() throws SQLException;

	/**
	 * 修改文章
	 * 
	 * @param article
	 * @return
	 * @throws SQLException
	 */
	public boolean updateArticle(Article article) throws SQLException;

	/**
	 * 分页查询
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryArticlePage(int limit, int offset) throws SQLException;

	/**
	 * 文章总数
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int queryArticleCount() throws SQLException;
	/**
	 * 查询单个
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> queryOneArticle(int id) throws SQLException;
	/**
	 * 查询单个的简介信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> queryOneArticleInfo(int id) throws SQLException;
	/**
	 * 查询所有文章的所有年份
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> queryYearToPigeonhole(int year) throws SQLException;
	/**
	 * 查询指定年份的文章
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> queryActiclesYear() throws SQLException;
	
	boolean updateActicleHits(int id) throws SQLException;
	
}
