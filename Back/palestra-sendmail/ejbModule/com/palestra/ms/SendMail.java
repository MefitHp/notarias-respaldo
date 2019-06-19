package com.palestra.ms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "palestraSendMail") }, mappedName = "palestraSendMail")

public class SendMail implements MessageListener {

	@Resource(mappedName = "java:Mail")
	Session mailSession;

	public SendMail() {
	}

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				TextMessage msg = (TextMessage) message;
				String contenido = msg.getText();
				String subject = msg.getStringProperty("subject");
				String to = msg.getStringProperty("to");
				String cc = msg.getStringProperty("cc");

				MimeMessage mailMessage = new MimeMessage(mailSession);
				mailMessage.setSubject(subject);
				String user = mailSession.getProperty("mail.smtp.user");

				String[] addressesTo = listaCorreos(to);
				for (String addresTo : addressesTo) {
					mailMessage.setRecipients(
							javax.mail.Message.RecipientType.TO,
							javax.mail.internet.InternetAddress.parse(addresTo,
									false));
				}
				if(cc!=null){
					String[] addressesCC = listaCorreos(cc);
					for (String addresTo : addressesCC) {
						mailMessage.setRecipients(
								javax.mail.Message.RecipientType.CC,
								javax.mail.internet.InternetAddress.parse(addresTo,
										false));
					}
				}
				
				mailMessage.setFrom(new InternetAddress(user));

				mailMessage.setContent(contenido, "text/html; charset=utf-8");
				mailMessage.saveChanges();

				Transport transport = mailSession.getTransport("smtp");

				transport.connect();
				transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
				transport.close();

			} catch (MessagingException e) {
				System.out.printf("=====> MDB-SendMail-Notificaciones: Excepción al definir el mensaje: %s%n", e.getMessage());
				e.printStackTrace(System.out);
			} catch (ClassCastException e) {
				System.out.printf("=====> MDB-SendMail-Notificaciones: No se localizo un TextMessage, se localizo %s%n", e.getClass().getSimpleName());
				e.printStackTrace(System.out);
			} catch (NullPointerException e) {
				System.out.printf("=====> MDB-SendMail-Notificaciones: java.lang.NullPointerException: %s%n",e.getMessage());
				e.printStackTrace(System.out);
			} catch (JMSException e) {
				System.out.printf("=====> MDB-SendMail-Notificaciones: %s%n", e.getMessage());
				e.printStackTrace(System.out);
			}
		}
	}

	private String[] listaCorreos(String correos){
		String[] addresses;
		if (correos.contains(",") || correos.contains(";")) {
			correos = correos.replace(";", ",");
			addresses = correos.split(",");
		} else {
			addresses = new String[1];
			addresses[0] = correos;
		}
		return addresses;
	}
	
}
