package com.springbootfashion.blogrestapi.service;

import com.springbootfashion.blogrestapi.payload.PostDto;
import com.springbootfashion.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir   );
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void delete(long id);
    List<PostDto> getPostsByCategory(Long category);


}
