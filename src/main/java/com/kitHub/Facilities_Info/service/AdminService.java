package com.kitHub.Facilities_info.service;

import com.kitHub.Facilities_info.domain.Report.Block;
import com.kitHub.Facilities_info.domain.Report.Report;
import com.kitHub.Facilities_info.domain.User;
import com.kitHub.Facilities_info.repository.BlockRepository;
import com.kitHub.Facilities_info.repository.ReportRepository;
import com.kitHub.Facilities_info.repository.UserReviewRepository;
import com.kitHub.Facilities_info.repository.UserRepository;
import com.kitHub.Facilities_info.repository.community.CommentRepository;
import com.kitHub.Facilities_info.repository.community.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Transactional
    public void deleteReview(Long reviewId) {
        userReviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public Block blockUser(Long userId) {
        User userToBlock = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        userToBlock.setBlocked(true);

        Set<Report> reports = new HashSet<>(reportRepository.findByReportedUserId(userId));

        Block block = Block.builder()
                .blockedUser(userToBlock)
                .reports(reports)
                .blockDate(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        blockRepository.save(block);

        userToBlock.setBlock(block);
        userRepository.save(userToBlock);

        return block;
    }

    @Transactional
    public void unblockUser(Long userId) {
        User userToUnblock = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        userToUnblock.setBlocked(false);

        Block block = blockRepository.findByBlockedUserId(userId);
        if (block != null) {
            blockRepository.delete(block);
        }

        userToUnblock.setBlock(null);
        userRepository.save(userToUnblock);
    }

    @Transactional(readOnly = true)
    public Block getUserBlockRecord(Long userId) {
        return blockRepository.findByBlockedUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Block> getAllBlockRecords() {
        return blockRepository.findAll();
    }
}
