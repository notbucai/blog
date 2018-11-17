package cc.ncgame.service;

import java.util.ArrayList;

public interface ITagService {

	ArrayList<Integer> addTagToCurrentArticle(int aID, String[] tag_names);
	
	ArrayList<String> getCurrtentTags(int aID);
	
	String getTags();
	String getCurrtentArticles(int tID);
}
