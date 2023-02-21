package com.product.oneforall.repository;

import com.product.oneforall.models.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends MongoRepository<Link,String> {


}
