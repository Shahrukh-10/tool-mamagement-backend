package com.product.oneforall.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "link")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Link {

    @Id
    private String id;
    private String linkValue;
    private int rating;
    private String title;
    private String description;
    private String username;
    private boolean incremented;
}
