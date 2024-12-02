package com.kitHub.Facilities_info.controller.community;

import com.kitHub.Facilities_info.domain.community.Comment;
import com.kitHub.Facilities_info.dto.CreateCommentRequest;
import com.kitHub.Facilities_info.dto.UpdateCommentRequest;
import com.kitHub.Facilities_info.service.community.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@RequestBody CreateCommentRequest request) {
        return commentService.createComment(request);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) {
        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }
}
