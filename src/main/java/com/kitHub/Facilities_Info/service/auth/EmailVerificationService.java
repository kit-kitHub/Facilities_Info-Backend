package com.kitHub.Facilities_info.service.auth;

import com.kitHub.Facilities_info.domain.token.EmailVerificationToken;
import com.kitHub.Facilities_info.repository.token.EmailVerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationTokenRepository tokenRepository;

    @Transactional
    public void sendVerificationEmail(String email) {
        Optional<EmailVerificationToken> existingTokenOpt = tokenRepository.findByEmail(email);

        // 기존 토큰이 존재하면 삭제
        existingTokenOpt.ifPresent(tokenRepository::delete);

        // 새로운 토큰 생성
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(email, token, LocalDateTime.now().plus(Duration.ofHours(24)));
        tokenRepository.save(verificationToken);

        // 이메일 전송
        String verificationUrl = "http://localhost:8080/api/verifyEmail/" + token;
        String message = "Click the link to verify your email: " + verificationUrl;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("Email Verification");
        emailMessage.setText(message);
        mailSender.send(emailMessage);
    }

    public boolean verifyToken(String token) {
        Optional<EmailVerificationToken> verificationTokenOpt = tokenRepository.findByToken(token);
        if (verificationTokenOpt.isPresent() && verificationTokenOpt.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            EmailVerificationToken verificationToken = verificationTokenOpt.get().verify();
            tokenRepository.save(verificationToken);
            return true;
        }
        return false;
    }

    public boolean isEmailVerified(String email) {
        Optional<EmailVerificationToken> verificationToken = tokenRepository.findByEmail(email);
        return verificationToken.isPresent() && verificationToken.get().isVerified();
    }

    @Transactional
    public void deleteVerificationTokenByEmail(String email) {
        tokenRepository.deleteByEmail(email);
    }
}
