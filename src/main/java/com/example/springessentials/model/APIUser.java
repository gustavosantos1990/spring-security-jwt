package com.example.springessentials.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection = "user")
@Data
public class APIUser {

    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Indexed(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private boolean admin;

}
