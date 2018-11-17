package cc.ncgame.service;

public interface ICommentService {

	String queryCurrentComments(int bid);

	String addComment(int bId, int rId, String name, String email, String content, boolean is_email, String ip);

}
