//package com.kitHub.Facilities_info.domain.facility;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.kitHub.Facilities_info.domain.user.User;
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "detailed_location_reviews")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class DetailedLocationReview {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "detailed_location_id", nullable = false)
//    @JsonBackReference
//    private DetailedLocation detailedLocation;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    @JsonManagedReference
//    private User user;
//
//    @Column(nullable = false, length = 1000)
//    private String reviewComment;
//
//    @Column(nullable = false)
//    private LocalDateTime reviewDate;
//
//    @Column(nullable = false)
//    private int rating;
//
//    @Column(nullable = false)
//    private int likes;
//
//    @Column(nullable = false)
//    private int dislikes;
//
//    @Builder
//    public DetailedLocationReview(DetailedLocation detailedLocation, User user, String reviewComment, int rating, LocalDateTime reviewDate, int likes, int dislikes) {
//        this.detailedLocation = detailedLocation;
//        this.user = user;
//        this.reviewComment = reviewComment;
//        this.rating = rating;
//        this.reviewDate = reviewDate;
//        this.likes = likes;
//        this.dislikes = dislikes;
//    }
//
//    public DetailedLocationReview updateReviewComment(String reviewComment) {
//        this.reviewComment = reviewComment;
//        return this;
//    }
//
//    public DetailedLocationReview updateRating(int rating) {
//        this.rating = rating;
//        return this;
//    }
//
//    public DetailedLocationReview updateLikes(int likes) {
//        this.likes = likes;
//        return this;
//    }
//
//    public DetailedLocationReview updateDislikes(int dislikes) {
//        this.dislikes = dislikes;
//        return this;
//    }
//}
