package com.product.oneforall.services;

import com.product.oneforall.models.Link;
import com.product.oneforall.models.User;

import java.util.List;

public interface LinkService {

    Link addLink(Link link);

    Link deleteLink(Link link);

    Link incrementRating(Link link);
    Link decrementRating(Link link);

    List<Link> getUserLinks(String id);

    List<Link> performSearch(String keyword);
}
