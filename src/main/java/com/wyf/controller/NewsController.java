package com.wyf.controller;

import com.wyf.aspect.LogAspect;
import com.wyf.model.Comment;
import com.wyf.model.EntityType;
import com.wyf.model.HostHolder;
import com.wyf.model.News;
import com.wyf.model.ViewObject;
import com.wyf.service.CommentService;
import com.wyf.service.LikeService;
import com.wyf.service.NewsService;
import com.wyf.service.QiniuService;
import com.wyf.service.UserService;
import com.wyf.util.HeadlineUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by w7397 on 2017/3/19.
 */
@Controller
public class NewsController {

	@Autowired
	NewsService newsService;

	@Autowired
	QiniuService qiniuService;

	@Autowired
	UserService userService;

	@Autowired
	HostHolder hostHolder;

	@Autowired
	CommentService commentService;

	@Autowired
	LikeService likeService;

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
	public String newsDetail(@PathVariable("newsId") int newsId, Model model) {
		News news = newsService.getById(newsId);
		if (news != null) {
			int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
			if (localUserId != 0) {
				model.addAttribute("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
			} else {
				model.addAttribute("like", 0);
			}
			//评论
			List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
			List<ViewObject> commentVOs = new ArrayList<ViewObject>();
			for (Comment comment : comments) {
				ViewObject vo = new ViewObject();
				vo.set("comment", comment);
				vo.set("user", userService.getUser(comment.getUserId()));
				commentVOs.add(vo);
			}
			model.addAttribute("comments", commentVOs);
		}
		model.addAttribute("news", news);
		model.addAttribute("owner", userService.getUser(news.getUserId()));
		return "detail";
	}

	@RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
	public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content) {

		try {
			//过滤敏感词
			Comment comment = new Comment();
			comment.setUserId(hostHolder.getUser().getId());
			comment.setContent(content);
			comment.setEntityId(newsId);
			comment.setEntityType(EntityType.ENTITY_NEWS);
			comment.setCreateDate(new Date());
			comment.setStatus(0);

			commentService.addComment(comment);
			//更新news的评论数量
			int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
			newsService.updateCommentCount(comment.getEntityId(), count);
			//怎么异步化
		} catch (Exception e) {
			logger.error("增加评论失败" + e.getMessage());
		}
		return "redirect:/news/" + String.valueOf(newsId);
	}


	@RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
	@ResponseBody
	public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");
			StreamUtils.copy(new FileInputStream(new File(HeadlineUtil.IMAGE_DIR + imageName)), response.getOutputStream());
		} catch (Exception e) {
			logger.error("读取图片错误" + e.getMessage());
		}

	}

	@RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
	@ResponseBody
	public String addNews(@RequestParam("image") String image, @RequestParam("title") String title, @RequestParam("link") String link) {
		try {
			News news = new News();
			if (hostHolder.getUser() != null) {
				news.setUserId(hostHolder.getUser().getId());
			} else {
				//匿名id
				news.setUserId(HeadlineUtil.ANONYMOUS_ID);
			}
			news.setImage(image);
			news.setCreatedDate(new Date());
			news.setTitle(title);
			news.setLink(link);
			newsService.addNews(news);
			return HeadlineUtil.getJSONString(0);

		} catch (Exception e) {
			logger.error("添加资源错误", e.getMessage());
			return HeadlineUtil.getJSONString(1, "发布失败");
		}
	}

	@RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
	@ResponseBody
	public String uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			//String fileUrl = newsService.saveImage(file);
			String fileUrl = qiniuService.saveImage(file);
			if (fileUrl == null) {
				return HeadlineUtil.getJSONString(1, "上传图片失败");
			}
			return HeadlineUtil.getJSONString(0, fileUrl);
		} catch (Exception e) {
			logger.error("上传图片失败" + e.getMessage());
			return HeadlineUtil.getJSONString(1, "上传失败");
		}
	}

}
