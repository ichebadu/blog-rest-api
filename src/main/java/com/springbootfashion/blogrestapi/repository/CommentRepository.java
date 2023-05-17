package com.springbootfashion.blogrestapi.repository;

import com.springbootfashion.blogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>  {
    List<Comment> findPostById(long postId);
}
