package com.wyf.dao;

import com.wyf.model.News;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by w7397 on 2017/3/18.
 */
@Mapper
public interface NewsDAO {

	String TABLE_NAME = "news";
	String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
	String SELECT_FIELDS = " id, " + INSERT_FIELDS;

	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
	int addNews(News news);

	@Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
	int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);

	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
	News getById(int id);

	@Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
	int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

	List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
}
