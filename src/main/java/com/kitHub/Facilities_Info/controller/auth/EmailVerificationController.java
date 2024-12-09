package com.kitHub.Facilities_info.controller.auth;

import com.kitHub.Facilities_info.service.auth.EmailVerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.MailException;

@RestController
@RequestMapping("/api")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/emailVerify/{email}")
    public ResponseEntity<String> sendVerificationEmail(@PathVariable String email) {
        try {
            emailVerificationService.sendVerificationEmail(email);
            return ResponseEntity.ok("Verification email sent successfully");
        } catch (MailException e) {
            // 메일 전송 실패 처리
            return ResponseEntity.status(500).body("Failed to send verification email. Please try again later.");
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }

    @GetMapping("/verifyEmail/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        try {
            boolean isValid = emailVerificationService.verifyToken(token);
            if (isValid) {
                return ResponseEntity.ok("Email verified successfully. Please complete your registration.");
            } else {
                return ResponseEntity.status(400).body("Invalid or expired token");
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }

    @GetMapping("/checkEmailVerify/{email}")
    public ResponseEntity<String> checkEmailVerification(@PathVariable String email) {
        try {
            boolean isVerified = emailVerificationService.isEmailVerified(email);
            if (isVerified) {
                // 이메일 인증 확인 후 토큰 삭제
                emailVerificationService.deleteVerificationTokenByEmail(email);
                return ResponseEntity.ok("Email is verified and token deleted");
            } else {
                return ResponseEntity.status(400).body("Email is not verified");
            }
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }
}
