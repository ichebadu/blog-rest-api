package com.springbootfashion.blogrestapi.controller;

import com.springbootfashion.blogrestapi.payload.PostDto;
import com.springbootfashion.blogrestapi.payload.PostResponse;
import com.springbootfashion.blogrestapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springbootfashion.blogrestapi.utility.AppConstant.*;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name=" CRUD REST APIs for Post Resource"
)
public class  PostController {
    private PostService postService;
    public PostController (PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto createdPost = postService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API is used to get all post from the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping()
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required =false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required =false) String sortDir
            ){
        return postService.getAllPost(pageNo, pageSize,sortBy,sortDir);

    }
//    @GetMapping()
//    public ResponseEntity<List<PostDto>> getAllPost(){
//        List<PostDto> postDtos = postService.getAllPost();
//        return new ResponseEntity<>(postDtos, HttpStatus.OK);
//    }
    @Operation(
            summary = "Get Post REST API",
            description = "Get Post REST API is used to get a particular post from the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name ="id") Long postId){
        PostDto postDto1 = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long postId){
        postService.delete(postId);
        return new ResponseEntity<>("post deleted successfully", HttpStatus.OK);
    }
    @Operation(
            summary = "Get Post By Category REST API",
            description = "Get Post By Category REST API is used to get a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    //Build Get posts by category REST API
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable(name="id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}