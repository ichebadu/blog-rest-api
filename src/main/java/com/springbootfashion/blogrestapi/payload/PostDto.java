package com.springbootfashion.blogrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;
    @Schema(
            description = " Blog Post Title"
    )
    @NotEmpty
    @Size(min = 3, message = "title must have  at least three characters")
    private String title;
    @Schema(
            description = " Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "description must have  at least 10 characters")
    private String description;
    @Schema(
            description = " Blog Post Content"
    )
    @NotEmpty
    @Size(min = 3, message = "title must have  at least three characters")
    private String content;
    @Schema(
            description = " Blog Post Comments"
    )
    @Size(min = 3, message = "title must have  at least three characters")
    private Set<CommentDto> comments;
    @Schema(
            description = " Blog Post Category"
    )
    private Long categoryId;


//    public PostDto() {
//        this.comments = new HashSet<>();
//    }
}
