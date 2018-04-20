package com.wyf.service;

import com.wyf.dao.CommentDAO;
import com.wyf.model.Comment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by w7397 on 2017/3/20.
 */
@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public List<Comment> getCommentsByEntity(int entityId, int entityType) {
		return commentDAO.selectByEntity(entityId, entityType);
	}

	public int addComment(Comment comment) {
		return commentDAO.addComment(comment);
	}

	public int getCommentCount(int entityId, int entityType) {
		return commentDAO.getCommentCount(entityId, entityType);
	}
}
