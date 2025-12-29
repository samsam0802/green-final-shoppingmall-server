package kr.kro.moonlightmoist.shopapi.notification.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.notification.domain.EmailVerification;
import kr.kro.moonlightmoist.shopapi.notification.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    @Value("${sendgrid.from-name}")
    private String fromName;

    private final EmailVerificationRepository verificationRepository;

    @Override
    public void sendVerificationEmail(String toEmail) {
        // 6자리 랜덤 인증 코드 생성
        String verificationCode = generateVerificationCode();

        // DB에 저장
        EmailVerification verification = EmailVerification.builder()
                .email(toEmail)
                .code(verificationCode)
                .build();

        verificationRepository.save(verification);
        log.info("인증 코드 생성 및 저장 완료 - 이메일: {}", toEmail);

        // SendGrid로 이메일 발송
        sendEmail(toEmail, verificationCode);
    }

    @Override
    @Transactional
    public boolean verifyCode(String email, String code) {
        // DB에서 가장 최근의 미인증 코드 조회
        Optional<EmailVerification> verificationOpt =
                verificationRepository.findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(email);
        log.info("여기는 verifyCode DB에 저장되어있는 인증코드 불러오기 : {}", verificationOpt);

        if (verificationOpt.isEmpty()) {
            log.warn("인증 코드 없음 - 이메일: {}", email);
            return false;
        }

        EmailVerification verification = verificationOpt.get();

        // 만료 확인
        if (verification.isExpired()) {
            log.warn("인증 코드 만료 - 이메일: {}", email);
            return false;
        }

        // 코드 일치 확인
        if (!verification.matchesCode(code)) {
            log.warn("인증 코드 불일치 - 이메일: {}", email);
            return false;
        }



        // 인증 성공 처리
        verification.verify();
        verificationRepository.save(verification);
        log.info("여기는 이메일 인증 코드 성공 확인 : {}", code);
        log.info("여기는 이메일 인증 객체 성공 확인 : {}", verification);
        log.info("이메일 인증 성공 - 이메일: {}", email);

        return true;
    }

    @Override
    public void sendEmail(String toEmail, String verificationCode) {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(toEmail);
        String subject = "[MoonlightMoist Shop] 이메일 인증 코드";
        Content content = new Content("text/html", buildEmailContent(verificationCode));

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                log.error("이메일 발송 실패 - 상태코드: {}, 응답: {}",
                        response.getStatusCode(), response.getBody());
                throw new RuntimeException("이메일 발송 실패");
            }

            log.info("이메일 발송 성공 - 수신: {}", toEmail);

        } catch (IOException ex) {
            log.error("이메일 발송 중 오류 발생", ex);
            throw new RuntimeException("이메일 발송 중 오류 발생", ex);
        }
    }

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    @Override
    public String buildEmailContent(String code) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: 'Malgun Gothic', Arial, sans-serif; 
                        line-height: 1.6;
                        color: #333;
                    }
                    .container { 
                        max-width: 600px; 
                        margin: 0 auto; 
                        padding: 40px 20px; 
                    }
                    .header {
                        text-align: center;
                        margin-bottom: 30px;
                    }
                    .header h1 {
                        color: #4A90E2;
                        margin: 0;
                    }
                    .code-box { 
                        background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                        color: white;
                        padding: 30px; 
                        text-align: center; 
                        font-size: 36px; 
                        font-weight: bold; 
                        letter-spacing: 8px;
                        margin: 30px 0;
                        border-radius: 10px;
                        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                    }
                    .info {
                        background: #f8f9fa;
                        padding: 20px;
                        border-radius: 5px;
                        margin: 20px 0;
                    }
                    .footer { 
                        color: #999; 
                        font-size: 13px; 
                        margin-top: 40px;
                        text-align: center;
                        border-top: 1px solid #eee;
                        padding-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🌙 달빛나라 촉촉마을 입니다</h1>
                        <p>이메일 인증</p>
                    </div>
                    
                    <p>안녕하세요.</p>
                    <p>회원가입을 완료하시려면 아래 인증 코드를 입력해주세요.</p>
                    
                    <div class="code-box">%s</div>
                    
                    <div class="info">
                        <p><strong>⏰ 유효시간:</strong> 5분</p>
                        <p><strong>📧 본인이 요청하지 않으셨다면</strong> 이 이메일을 무시하세요.</p>
                    </div>
                    
                    <div class="footer">
                        <p>© 2026 MoonlightMoist Shop. All rights reserved.</p>
                        <p>이 이메일은 발신 전용입니다.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(code);
    }

    @Override
    @Transactional
    public void cleanupExpiredCodes() {
        verificationRepository.deleteExpired(LocalDateTime.now());
        log.info("만료된 인증 코드 정리 완료");
    }
}
