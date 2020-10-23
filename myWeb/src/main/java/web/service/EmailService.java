package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.extern.slf4j.Slf4j;

/**
 * Email Serivce
 */
@Slf4j
@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;
	
	public void weeklyMailBuild(String to, String subject) {
		Context context = new Context();
		context.setVariable("email", to);
		String contents = templateEngine.process("email/weeklyEmail", context);
		
		sendSimpleMessage(to, subject, contents);
	}

	private void sendSimpleMessage(String to, String subject, String contents) {
		MimeMessagePreparator message = miemMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(miemMessage);
			messageHelper.setFrom("hobbitbears@greenlab9.com");
			messageHelper.setTo(to);
			messageHelper.setSubject("부트 메일 테스트");
			messageHelper.setText(contents, true);
		};

		emailSender.send(message);
	}
}
