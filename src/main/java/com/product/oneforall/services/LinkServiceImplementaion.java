package com.product.oneforall.services;

import com.product.oneforall.models.Link;
import com.product.oneforall.models.User;
import com.product.oneforall.repository.LinkRepository;
import com.product.oneforall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkServiceImplementaion implements LinkService {
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Link addLink(Link link) {
        Criteria criteria = Criteria.where("linkValue").is(link.getLinkValue());
        Query query = new Query(criteria);
        if (mongoTemplate.exists(query, Link.class)) {
            throw new RuntimeException("Someone has already added this link ...");
        } else {
            Link link1 = Link.builder()
                    .linkValue(link.getLinkValue())
                    .title(link.getTitle())
                    .description(link.getDescription())
                    .rating(3000)
                    .username(link.getUsername())
                    .build();
            linkRepository.save(link1);
            User user = userRepository.findByUsername(link.getUsername());
            System.out.println(link.getUsername());
            if (user.getLinks() == null) {
                List<Link> linksToAddInUser = new ArrayList<Link>();
                linksToAddInUser.add(link1);
                user.setLinks(linksToAddInUser);
            } else {
                List<Link> addedLink = user.getLinks();
                addedLink.add(link1);
                user.setLinks(addedLink);
            }
            System.out.println(user);
            User user1 = userRepository.save(user);
            if (!user1.getLinks().contains(link1)) {
                throw new RuntimeException("Some error occurred .. try again");
            }
            return link1;
        }
    }

    @Override
    public Link deleteLink(Link link) {
        if (!linkRepository.findById(link.getId()).isPresent()) {
            throw new RuntimeException("Link not present in db");
        }
        linkRepository.deleteById(link.getId());
        User user = userRepository.findByUsername(link.getUsername());
        List<Link> userLinks = user.getLinks();
        List<Link> updatedLinks = userLinks
                .stream()
                .filter(obj -> !obj.getId().equals(link.getId()))
                .collect(Collectors.toList());
        user.setLinks(updatedLinks);
        userRepository.save(user);
        return link;
    }

    @Override
    public Link incrementRating(Link link) {
        Criteria criteria = Criteria.where("id").is(link.getId());
        Query query = new Query(criteria);
        if (!mongoTemplate.exists(query, Link.class)) {
            throw new RuntimeException("No link with this id...");
        }
        Link link1 = mongoTemplate.findOne(query, Link.class);
        link1.setRating(link1.getRating() + 1);
        link1.setIncremented(true);
        System.out.println(link1);
        Link updatedLink = linkRepository.save(link1);
        User user = userRepository.findByUsername(link.getUsername());
        if (user == null) {
            throw new RuntimeException("No user with this id");
        }
        List<Link> userLinks = user.getLinks();
        for (Link l : userLinks) {
            if (link.getId().equals(l.getId())) {
                l = updatedLink;
                break;
            }
        }
        user.setLinks(userLinks);
        userRepository.save(user);
        return updatedLink;
    }

    @Override
    public Link decrementRating(Link link) {
        Criteria criteria = Criteria.where("id").is(link.getId());
        Query query = new Query(criteria);
        if (!mongoTemplate.exists(query, Link.class)) {
            throw new RuntimeException("No link with this id...");
        }
        Link link1 = mongoTemplate.findOne(query, Link.class);
        link1.setRating(link1.getRating() - 1);
        System.out.println(link1);
        link1.setIncremented(false);
        Link updatedLink = linkRepository.save(link1);
        User user = userRepository.findByUsername(link.getUsername());
        if (user == null) {
            throw new RuntimeException("No user with this id");
        }
        List<Link> userLinks = user.getLinks();
        for (Link l : userLinks) {
            if (link.getId().equals(l.getId())) {
                l = updatedLink;
                break;
            }
        }
        user.setLinks(userLinks);
        userRepository.save(user);
        return updatedLink;
    }

    @Override
    public List<Link> getUserLinks(String id) {
        List<Link> lst = new ArrayList<>();
        if (id == null || id.length() == 0) {
            System.out.println("id");
            return lst;
        }
        Optional<User> user = userRepository.findById(id);
        System.out.println(user.get().getLinks());
        if (user.get().getLinks() != null) {
            List<Link> ls = user.get().getLinks();
            lst.addAll(ls);
            return lst;
        }
        return lst;
    }

    @Override
    public List<Link> performSearch(String keyword) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("title").regex(keyword,"i"),
                Criteria.where("description").regex(keyword,"i"),
                Criteria.where("linkValue").regex(keyword,"i")
        );
        Query query = new Query(criteria);
        if (mongoTemplate.exists(query,Link.class)){
            List<Link> searchedLinks = mongoTemplate.find(query,Link.class);
            return searchedLinks;
        }else {
            return null;
        }
    }


}
