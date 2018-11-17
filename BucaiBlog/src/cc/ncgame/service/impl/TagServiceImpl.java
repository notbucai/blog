package cc.ncgame.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cc.ncgame.bean.Tag;
import cc.ncgame.bean.TagMap;
import cc.ncgame.dao.impl.ArticleDaoImpl;
import cc.ncgame.dao.impl.TagDaoImpl;
import cc.ncgame.dao.impl.TagMapDaoImpl;
import cc.ncgame.service.ITagService;

public class TagServiceImpl implements ITagService {

	@Override
	public ArrayList<Integer> addTagToCurrentArticle(int aID, String[] tag_names) {
		ArrayList<Integer> tagIds = new ArrayList<>();
		for (String tag_name : tag_names) {
			int tagId = addOneTag(aID, tag_name);
			if (tagId > 0) {
				tagIds.add(tagId);
			}
		}

		return tagIds;
	}

	private int addOneTag(int aID, String tag_name) {
		Tag tag = new Tag(tag_name);

		try {
			TagDaoImpl tagDaoImpl = new TagDaoImpl();
			boolean tagExists;
//			tagExists = tagDaoImpl.isTagExists(tag.getTag_name());
			tagExists = false;

			if (tagExists) {
				int insertTag = tagDaoImpl.insertTag(tag);
				tag.setId(insertTag);
			}

			if (tag.getId() == 0) {
				return 0;
			}
			TagMap tagMap = new TagMap(aID, tag.getId());
			TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();

			int insertTagMap = tagMapDaoImpl.insertTagMap(tagMap);

			if (insertTagMap > 0) {
				return tag.getId();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public ArrayList<String> getCurrtentTags(int aID) {

		try {
			TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();
			TagDaoImpl tagDaoImpl = new TagDaoImpl();
			List<Map<String, Object>> tagIds = tagMapDaoImpl.getTagIds(aID);

			ArrayList<String> list = new ArrayList<>();

			for (Map<String, Object> map : tagIds) {

				try {

					int tID = Integer.parseInt(map.get("tID").toString());
					String queryTag = tagDaoImpl.queryTag(tID);

					if (queryTag != null) {
						list.add(queryTag);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getCurrtentArticles(int tID) {

		JSONObject jsonObject = new JSONObject();

		int code = -1;
		String message = "错误";

		try {
			TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();
			
			TagDaoImpl tagDaoImpl = new TagDaoImpl();
			
			ArticleDaoImpl articleDaoImpl = new ArticleDaoImpl();
			List<Map<String, Object>> arcicleIds = tagMapDaoImpl.getArticleIds(tID);

			ArrayList<Map<String, Object>> list = new ArrayList<>();
			
			
			for (Map<String, Object> arcicleId : arcicleIds) {

				int aID = Integer.parseInt(arcicleId.get("aID").toString());

				Map<String, Object> queryOneArticle = articleDaoImpl.queryOneArticleInfo(aID);

				if (queryOneArticle != null) {
					list.add(queryOneArticle);
				}

			}
			code = 0;
			message = "成功";
			
			String tNmae = tagDaoImpl.queryTag(tID);
			
			HashMap<String, Object> result = new HashMap<>();
			result.put("list", list);
			result.put("t_name", tNmae);
			jsonObject.put("result", result);

		} catch (SQLException e) {
			e.printStackTrace();
			code = 101;
			message = "SQL 错误";
		} catch (Exception e) {
			e.printStackTrace();
			code = 102;
			message = "错误";

		}

		jsonObject.put("code", code);
		jsonObject.put("message", message);

		return jsonObject.toString();
	}

	@Override
	public String getTags() {
		JSONObject jsonObject = new JSONObject();

		int code = -1;
		String message = "错误";
		try {

			List<Map<String, Object>> list = new ArrayList<>();

			TagMapDaoImpl tagMapDaoImpl = new TagMapDaoImpl();
			TagDaoImpl tagDaoImpl = new TagDaoImpl();

			List<Map<String, Object>> countTagIds = tagMapDaoImpl.countTagIds();

			for (Map<String, Object> tagMap : countTagIds) {

				int tID = Integer.parseInt(tagMap.get("tID").toString());
				int count = Integer.parseInt(tagMap.get("count").toString());

				try {

					String tag_name = tagDaoImpl.queryTag(tID);

					if (tag_name == null) {
						continue;
					}
					HashMap<String, Object> tag = new HashMap<>();

					tag.put("tID", tID);
					tag.put("count", count);
					tag.put("name", tag_name);

					list.add(tag);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			jsonObject.put("result", list);
			code = 0;
			message = "成功";
		} catch (SQLException e) {

			code = 101;
			message = "SQL错误";
			e.printStackTrace();
			// TODO: handle exception
		}
		jsonObject.put("code", code);
		jsonObject.put("message", message);

		return jsonObject.toString();
	}

}
