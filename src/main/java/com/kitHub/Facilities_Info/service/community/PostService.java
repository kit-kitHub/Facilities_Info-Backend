package com.kitHub.Facilities_info.service.community;

import com.kitHub.Facilities_info.domain.User;
import com.kitHub.Facilities_info.domain.community.Post;
import com.kitHub.Facilities_info.dto.CreatePostRequest;
import com.kitHub.Facilities_info.dto.UpdatePostRequest;
import com.kitHub.Facilities_info.repository.UserRepository;
import com.kitHub.Facilities_info.repository.community.PostRepository;

import com.kitHub.Facilities_info.service.UserService;
import com.kitHub.Facilities_info.util.Authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserService userService;

    public Post createPost(CreatePostRequest request) {
        User user = authenticationProvider.getUserInfoFromSecurityContextHolder();
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .modifiedAt(LocalDateTime.now())
                .build();
        user = user.addPost(post); // 관계가 업데이트된 User 객체를 반환받아 저장
        userRepository.save(user);
        return postRepository.save(post);
    }

    public Post updatePost(Long id, UpdatePostRequest request) {
        Post foundPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Long authorUserId = foundPost.getUser().getId();
        User user = userService.getUserInfoIfMatchesAuthor(authorUserId);

        if (user != null) {
            foundPost.updatePost(request.getTitle(), request.getContent());
            user = user.updatePost(foundPost); // 관계가 업데이트된 User 객체를 반환받아 저장
            userRepository.save(user);
            return postRepository.save(foundPost);
        } else {
            throw new RuntimeException("작성자만 수정할 수 있습니다");
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
