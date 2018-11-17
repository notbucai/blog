package cc.ncgame.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cc.ncgame.bean.Article;
import cc.ncgame.bean.Tag;
import cc.ncgame.bean.TagMap;
import cc.ncgame.dao.impl.ArticleDaoImpl;
import cc.ncgame.dao.impl.TagDaoImpl;
import cc.ncgame.dao.impl.TagMapDaoImpl;
import cc.ncgame.service.IArticleService;

public class ArticleServiceImpl implements IArticleService {

	@Override
	public String queryAllArticle() {

		ArticleDaoImpl artcleDaoImpl = new ArticleDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "Unknown Error";

		try {

			List<Map<String, Object>> queryAllArticle = artcleDaoImpl.queryAllArticle();

			if (queryAllArticle == null) {
				code = 1;
				message = "查询失败";
			} else if (queryAllArticle.size() <= 0) {
				code = 0;
				message = "木有数据";
			} else {
				code = 0;
				message = "成功";
				jsonObject.put("list", queryAllArticle);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	@Override
	public String queryArticlePage(int limit, int offset) {
		ArticleDaoImpl artcleDaoImpl = new ArticleDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "Unknown Error";
		try {

			List<Map<String, Object>> queryPageArticle = artcleDaoImpl.queryArticlePage(limit, offset);
			System.out.println(queryPageArticle);
			int count = artcleDaoImpl.queryArticleCount();

			if (queryPageArticle == null) {
				code = 1;
				message = "查询失败";
			} else {
				code = 0;
				message = "成功";
				jsonObject.put("total", count);
				jsonObject.put("list", queryPageArticle);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	public String queryOneArticle(int id) {

		ArticleDaoImpl artcleDaoImpl = new ArticleDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "Unknown Error";
		try {

			HashMap<String, Object> resMap = new HashMap<>();
			Map<String, Object> queryOneArticle = artcleDaoImpl.queryOneArticle(id);
			System.out.println(queryOneArticle);
			int count = artcleDaoImpl.queryArticleCount();

			if (queryOneArticle == null) {
				code = 1;
				message = "查询失败";
				throw new Exception("不存在");
			}

			TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();
			TagDaoImpl tagDaoImpl = new TagDaoImpl();
			List<Map<String, Object>> tagIds = tagMapDaoImpl.getTagIds(id);
			ArrayList<Map<String, Object>> tags = new ArrayList<>();
			if (tagIds.size() > 0) {

				for (Map<String, Object> map : tagIds) {
					int tID = Integer.parseInt(map.get("tID").toString());
					String queryTag = tagDaoImpl.queryTag(tID);

					if (queryTag != null) {
						Map<String, Object> tag = new HashMap<String, Object>();
						tag.put("id", tID);
						tag.put("name", queryTag);
						tags.add(tag);
					}

				}

			}
			resMap.put("tags", tags);
			resMap.put("article", queryOneArticle);

			code = 0;
			message = "成功";
			jsonObject.put("total", count);
			jsonObject.put("result", resMap);
			artcleDaoImpl.updateActicleHits(id);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	@Override
	public String queryYearArticles() {

		ArticleDaoImpl artcleDaoImpl = new ArticleDaoImpl();
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "Unknown Error";

		try {

			List<Map<String, Object>> queryActiclesYear = artcleDaoImpl.queryActiclesYear();

			if (queryActiclesYear == null) {
				code = 1;
				message = "查询失败";
				throw new Exception("查询失败");
			}

			ArrayList<HashMap<String, Object>> list = new ArrayList<>();

			for (Map<String, Object> map : queryActiclesYear) {

				try {
					HashMap<String, Object> yearMap = new HashMap<>();
					System.out.println(map.get("count").getClass().getName());
					int count = Integer.parseInt(map.get("count").toString());
					int year = Integer.parseInt(map.get("year").toString());

					List<Map<String, Object>> queryYearToPigeonhole = artcleDaoImpl.queryYearToPigeonhole(year);
//					queryYearToPigeonhole

					yearMap.put("year", year);
					yearMap.put("count", count);
					yearMap.put("list", queryYearToPigeonhole);

					list.add(yearMap);

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}

			if (queryActiclesYear.size() <= 0) {
				code = 0;
				message = "木有数据";
			} else {
				code = 0;
				message = "成功";
				jsonObject.put("result", list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		System.out.println(jsonObject.toString());

		return jsonObject.toString();

	}

	@Override
	public String addArticle(String title, String info, String content, String[] tags) {
		JSONObject jsonObject = new JSONObject();
		int code = -1;
		String message = "错误";
		try {

			HashMap<String, Object> hashMap = new HashMap<>();
			Article article = new Article(title, content, info);
			ArticleDaoImpl articleDaoImpl = new ArticleDaoImpl();

			int aID = articleDaoImpl.addArticle(article);

			if (aID > 0) {
				ArrayList<String> arrayList = new ArrayList<>();

				TagDaoImpl tagDaoImpl = new TagDaoImpl();

				for (String tag_name : tags) {
					try {
						int tagId = tagDaoImpl.getTagId(tag_name);
						TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();

						if (tagId == 0) {
							Tag tag = new Tag(tag_name);
							tagId = tagDaoImpl.insertTag(tag);
						}

						TagMap tagMap = new TagMap(aID, tagId);

						tagMapDaoImpl.insertTagMap(tagMap);

						arrayList.add(tag_name);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				code = 0;
				message = "成功";
				hashMap.put("id", aID);
				hashMap.put("title", title);
				hashMap.put("info", info);
				hashMap.put("content", content);
				hashMap.put("tags", arrayList);
				jsonObject.put("result", hashMap);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		return jsonObject.toString();
	}
}
