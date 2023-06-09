package com.springbootfashion.blogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message="Name should not be null")
    private String name;
    @NotEmpty(message="Email should not be null")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "comment body must be minimum 10 characters")
    private String body;
}
