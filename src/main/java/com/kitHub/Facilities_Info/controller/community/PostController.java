package com.kitHub.Facilities_info.controller.community;

import com.kitHub.Facilities_info.domain.community.Post;
import com.kitHub.Facilities_info.dto.CreatePostRequest;
import com.kitHub.Facilities_info.dto.UpdatePostRequest;
import com.kitHub.Facilities_info.service.community.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        return postService.updatePost(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }
}
