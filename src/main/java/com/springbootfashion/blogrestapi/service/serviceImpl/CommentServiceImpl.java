package com.springbootfashion.blogrestapi.service.serviceImpl;

import com.springbootfashion.blogrestapi.entity.Comment;
import com.springbootfashion.blogrestapi.entity.Post;
import com.springbootfashion.blogrestapi.exception.BlogApiException;
import com.springbootfashion.blogrestapi.exception.ResourceNotFoundException;
import com.springbootfashion.blogrestapi.payload.CommentDto;
import com.springbootfashion.blogrestapi.repository.CommentRepository;
import com.springbootfashion.blogrestapi.repository.PostRepository;
import com.springbootfashion.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a new comment for the given post.
     *
     * @param postId      the ID of the post to add the comment to
     * @param commentDto  the DTO representing the comment to create
     * @return the DTO representing the newly created comment
     * @throws ResourceNotFoundException if the post with the given ID does not exist
     */
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        //set post to comment entity
        comment.setPost(post);
        //save comment entity to data base
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> findCommentPostById(long postId) {
        List<Comment> comments = commentRepository.findPostById(postId);
        return comments.stream().map(comment-> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve most entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        //retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comments","commentId",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updateComment = commentRepository.save(comment);

        return mapToDTO(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment deletedComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        if(!deletedComment.getPost().getId().equals(post.getId())){
            throw new ResourceNotFoundException("Comment","commentId",commentId);
        }
        commentRepository.delete(deletedComment);
    }


    //convert list of comment to entities to dto
    private CommentDto mapToDTO(Comment comment) {
//        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    //convert Dto to entity
    private Comment mapToEntity(CommentDto commentDto) {
//        Comment comment = mapper.map(commentDto, Comment.class);
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
