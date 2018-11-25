package cc.ncgame.service;

public interface ILinkService {

	String getLinks();

	String getRandLinks();

	String addLink(String title, String imgPath, String info, String link_url);
	String updateLink(int id,String title, String imgPath, String info, String link_url);
	
	String deleteLink(int id);
}
