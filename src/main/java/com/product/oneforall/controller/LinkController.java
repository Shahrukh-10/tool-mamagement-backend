package com.product.oneforall.controller;

import com.product.oneforall.models.Link;
import com.product.oneforall.models.User;
import com.product.oneforall.repository.LinkRepository;
import com.product.oneforall.repository.UserRepository;
import com.product.oneforall.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.product.oneforall.constants.OneForAllConstants.MESSAGE;

@RestController
@RequestMapping("/link")
@CrossOrigin(origins = "*")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addLink")
    public ResponseEntity<Object> addLink(@RequestBody Link link) {
        HashMap<String, Object> response = new HashMap<>();
        if (link.getLinkValue() != null) {
            Link link1 = linkService.addLink(link);
            response.put(MESSAGE, "Added Link Successfully.");
            response.put("link", link1);
        } else {
            response.put(MESSAGE, "link is null , use Genuine link");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLink")
    public ResponseEntity<Object> deleteLink(@RequestBody Link link) {
        HashMap<String, Object> response = new HashMap<>();
        if (link.getId() == null || link.getUsername() == null) {
            response.put("message", "some error occured not deleted..");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Link link1 = linkService.deleteLink(link);
        response.put("link", link1);
        response.put("message", "link deleted successfully..");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/incrementRating")
    public ResponseEntity<Object> incrementRating(@RequestBody Link link) {
        HashMap<String, Object> response = new HashMap<>();
        if (link.getId() == null && link.getUsername() == null) {
            response.put(MESSAGE, "Username or link is null ...");
        }
        Link link1 = linkService.incrementRating(link);
        response.put(MESSAGE, "Rating incremented");
        response.put("link", link1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decrementRating")
    public ResponseEntity<Object> decrementRating(@RequestBody Link link) {
        HashMap<String, Object> response = new HashMap<>();
        if (link.getId() == null && link.getUsername() == null) {
            response.put(MESSAGE, "Username or link is null ...");
        }
        Link link1 = linkService.decrementRating(link);
        response.put(MESSAGE, "Rating incremented");
        response.put("link", link1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Link>> getAllLink() {
        return new ResponseEntity<>(linkRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/getUserLinks")
    public ResponseEntity<Object> getUserLinks(@RequestBody User user) {
        HashMap<String, Object> hs = new HashMap<>();
        List<Link> userlink = linkService.getUserLinks(user.getId());
        System.out.println("jkj" + userlink);
        if (user.getId() == null) {
            hs.put("msg", "invalid_user");
            hs.put("link", userlink);
        } else {
            hs.put("msg", "fetcsuccess");
            hs.put("link", userlink);
        }
        return new ResponseEntity<>(hs, HttpStatus.OK);
    }

    @GetMapping("/query/{keyword}")
    public ResponseEntity<Object> searchLinks(@PathVariable("keyword") String keyword) {
        List<Link> lst = null;
        HashMap<String, Object> hs = new HashMap<>();
            lst = linkService.performSearch(keyword);
        if (keyword.length() > 0 && lst!=null) {
            hs.put("message", "query_success");
            hs.put("links", lst);
            return new ResponseEntity<>(hs, HttpStatus.OK);
        }
        hs.put("message", "query_not_found");
        hs.put("links", null);
        return new ResponseEntity<>(hs, HttpStatus.OK);

    }
}


