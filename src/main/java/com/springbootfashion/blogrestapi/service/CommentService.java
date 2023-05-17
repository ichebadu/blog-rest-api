package com.springbootfashion.blogrestapi.service;

import com.springbootfashion.blogrestapi.entity.Comment;
import com.springbootfashion.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long Post, CommentDto commentDto);
    List<CommentDto> findCommentPostById(long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);
    void deleteComment(Long postId, Long comment);

}
