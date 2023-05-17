package com.springbootfashion.blogrestapi.service.serviceImpl;

import com.springbootfashion.blogrestapi.entity.Category;
import com.springbootfashion.blogrestapi.entity.Post;
import com.springbootfashion.blogrestapi.exception.ResourceNotFoundException;
import com.springbootfashion.blogrestapi.payload.CommentDto;
import com.springbootfashion.blogrestapi.payload.PostDto;
import com.springbootfashion.blogrestapi.payload.PostResponse;
import com.springbootfashion.blogrestapi.repository.CategoryRepository;
import com.springbootfashion.blogrestapi.repository.PostRepository;
import com.springbootfashion.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostPostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    public PostPostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category","id", postDto.getCategoryId()));
        //convert DTO to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToTDO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(post->mapToTDO(post)).collect(Collectors.toList());

          Pageable pageable = PageRequest.of(pageNo, pageSize, sort) ;
            Page<Post> posts = postRepository.findAll(pageable);
            //get content from page
            List<Post> ListOfPosts = posts.getContent();
            //constructing response

            List<PostDto> content = ListOfPosts.stream().map(this::mapToTDO).collect(Collectors.toList());
            PostResponse postResponse = new PostResponse();
            postResponse.setContent(content);
            postResponse.setPageNo(posts.getNumber());
            postResponse.setPageSize(posts.getSize());
            postResponse.setTotalElement((int) posts.getTotalElements());
            postResponse.setTotalPages(posts.getTotalPages());
            postResponse.setLast(postResponse.isLast());
            return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToTDO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post  = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost= postRepository.save(post);
        return mapToTDO(updatePost);
    }
    @Override
    public void delete(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("categoryId","id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> mapToTDO(post)).collect(Collectors.toList());
    }

    //convert entity dto to
    private PostDto mapToTDO(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
//        postDto.setComments(post.getComments().stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet()));
//        postDto.setComments(post.getComments().stream().map(comment -> {
//            CommentDto commentDto = new CommentDto();
//            commentDto.setId(comment.getId());
//            commentDto.setName(comment.getName());
//            commentDto.setEmail(comment.getEmail());
//            commentDto.setBody(comment.getBody());
//            return commentDto;
//        }).collect(Collectors.toSet()));

        return postDto;
    }
    //convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post =  modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
