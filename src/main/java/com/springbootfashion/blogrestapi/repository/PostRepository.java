package com.springbootfashion.blogrestapi.repository;

import com.springbootfashion.blogrestapi.entity.Comment;
import com.springbootfashion.blogrestapi.entity.Post;
import com.springbootfashion.blogrestapi.payload.CommentDto;
import com.springbootfashion.blogrestapi.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long category);
    

}
