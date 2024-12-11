package com.kitHub.Facilities_info.service.auth;

import com.kitHub.Facilities_info.domain.token.EmailVerificationToken;
import com.kitHub.Facilities_info.repository.token.EmailVerificationTokenRepository;
import jakarta.transaction.Transactional;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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

        String token;
        // 기존 토큰이 존재하면 업데이트
        if (existingTokenOpt.isPresent()) {
            EmailVerificationToken existingToken = existingTokenOpt.get();
            token = UUID.randomUUID().toString();
            existingToken.setToken(token);
            existingToken.setExpiryDate(LocalDateTime.now().plus(Duration.ofHours(24)));
            existingToken.setVerified(false);
            tokenRepository.save(existingToken);
        } else {
            // 새로운 토큰 생성
            token = UUID.randomUUID().toString();
            EmailVerificationToken verificationToken = new EmailVerificationToken(email, token, LocalDateTime.now().plus(Duration.ofHours(24)));
            tokenRepository.save(verificationToken);
        }

        // 이메일 전송
        String verificationUrl = "http://3.34.105.70:8080/api/verifyEmail/" + token;
        String message = String.format("""
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이메일 인증</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px;">
<div style="background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
    <div style="text-align: center; padding-bottom: 20px;">
        <h1>이메일 인증</h1>
    </div>
    <div style="text-align: center;">
        <p>아래 버튼을 클릭하여 이메일 주소를 인증해 주세요</p>
        <a href="%s" style="display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #28a745; text-decoration: none; border-radius: 5px; font-weight: bold;">이메일 인증</a>
    </div>
    <div style="text-align: center; padding-top: 20px; font-size: 12px; color: #aaaaaa;">
        <p>이 이메일을 요청하지 않으셨다면, 무시해 주세요.</p>
    </div>
</div>
</body>
</html>
        """, verificationUrl);

        MimeMessage emailMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(emailMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setSubject("Email Verification");
            helper.setText(message, true);
            mailSender.send(emailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
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
