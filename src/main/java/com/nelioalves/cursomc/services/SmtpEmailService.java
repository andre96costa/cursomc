package com.nelioalves.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

public class SmtpEmailService extends AbstractEmailService{
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendMail(SimpleMailMessage msn) {
		LOG.info("Enviando de email...");
		mailSender.send(msn);
		LOG.info("Email enviado");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando de email...");
		javaMailSender.send(msg);
		LOG.info("Email enviado");
		
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		// TODO Auto-generated method stub
		
	}
	

}
