package com.kitHub.Facilities_info.service.community;

import com.kitHub.Facilities_info.domain.User;
import com.kitHub.Facilities_info.domain.community.Comment;
import com.kitHub.Facilities_info.dto.CreateCommentRequest;
import com.kitHub.Facilities_info.dto.UpdateCommentRequest;
import com.kitHub.Facilities_info.repository.UserRepository;
import com.kitHub.Facilities_info.repository.community.CommentRepository;
import com.kitHub.Facilities_info.repository.community.PostRepository;

import com.kitHub.Facilities_info.service.UserService;
import com.kitHub.Facilities_info.util.Authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserService userService;

    public Comment createComment(CreateCommentRequest request) {
        User user = authenticationProvider.getUserInfoFromSecurityContextHolder();
        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("Post not found")))
                .modifiedAt(LocalDateTime.now())
                .build();
        user = user.addComment(comment); // 관계가 업데이트된 User 객체를 반환받아 저장
        userRepository.save(user);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, UpdateCommentRequest request) {
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Long authorUserId = foundComment.getUser().getId();
        User user = userService.getUserInfoIfMatchesAuthor(authorUserId);

        if (user != null) {
            foundComment.updateContent(request.getContent());
            user = user.updateComment(foundComment); // 관계가 업데이트된 User 객체를 반환받아 저장
            userRepository.save(user);
            return commentRepository.save(foundComment);
        } else {
            throw new RuntimeException("작성자만 수정할 수 있습니다");
        }
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
