package com.product.oneforall.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    @DBRef
    private List<Link> links;
    private String username;
    private String email;
    private String password;

    private String role;
    private String status;

}
