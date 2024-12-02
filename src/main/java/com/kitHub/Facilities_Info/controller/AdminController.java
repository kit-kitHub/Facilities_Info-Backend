package com.kitHub.Facilities_info.controller;

import com.kitHub.Facilities_info.domain.Report.Block;
import com.kitHub.Facilities_info.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @DeleteMapping("/delete/review/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        adminService.deleteReview(reviewId);
    }

    @DeleteMapping("/delete/post/{postId}")
    public void deletePost(@PathVariable Long postId) {
        adminService.deletePost(postId);
    }

    @DeleteMapping("/delete/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        adminService.deleteComment(commentId);
    }

    @PostMapping("/block/{userId}")
    public Block blockUser(@PathVariable Long userId) {
        return adminService.blockUser(userId);
    }

    @PostMapping("/unblock/{userId}")
    public void unblockUser(@PathVariable Long userId) {
        adminService.unblockUser(userId);
    }

    @GetMapping("/user-block/{userId}")
    public Block getUserBlockRecord(@PathVariable Long userId) {
        return adminService.getUserBlockRecord(userId);
    }

    @GetMapping("/blocks")
    public List<Block> getAllBlockRecords() {
        return adminService.getAllBlockRecords();
    }
}
