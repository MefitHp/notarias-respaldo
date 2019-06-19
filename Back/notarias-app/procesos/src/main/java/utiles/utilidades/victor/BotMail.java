package utiles.utilidades.victor;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class BotMail {
	
    // Sender's email ID needs to be mentioned
//    public static String from = "secretaria98y24@gmail.com";//change accordingly
//    private  static final String username = "secretaria98y24";//change accordingly
//    private  static final String password = "notarias98y24";//change accordingly
//    private static String host = "smtp.gmail.com";
    public static String from = "gonotarias@notarias98y24.com.mx";//change accordingly
    private  static final String username = "gonotarias";//change accordingly
    private  static final String password = "notari9824";//change accordingly
    private static String host = "mail.notarias98y24.com.mx"; 
    private static Properties props = new Properties();
    
    
    public static void enviaMailHTML(String asunto, String mensajeHtml, String mailreceptor){
    	  props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", "587");

          // Get the Session object.
          Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
             }
          });

          try {
             // Create a default MimeMessage object.
             Message message = new MimeMessage(session);

             // Set From: header field of the header.
             message.setFrom(new InternetAddress(from));

             // Set To: header field of the header.
             message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailreceptor));

             // Set Subject: header field
             message.setSubject(asunto);
             
             message.setContent(mensajeHtml, "text/html; charset=utf-8");
             Transport.send(message);

             System.out.println("Sent message successfully....");
          }catch (MessagingException e) {
              throw new RuntimeException(e);
          }
    }
    /**
     * 
     * @param subject
     * @param mensaje
     * @param receptor  Recipient's email ID needs to be mentioned.
     */
    public static void  enviaMail(String asunto,String mensaje,String mailreceptor){
//    	props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
           }
        });

        try {
           // Create a default MimeMessage object.
           Message message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailreceptor));

           // Set Subject: header field
           message.setSubject(asunto);

           // Now set the actual message
           message.setText(mensaje);

           // Send message
           Transport.send(message);

           System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
    }
	
    public static void  enviaMail(String asunto,String mensaje,String mailreceptor, String archivo){
//    	props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
           }
        });

        try {
           // Create a default MimeMessage object.
           Message message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailreceptor));

           // Set Subject: header field
           message.setSubject(asunto);

           // Now set the actual message
           message.setText(mensaje);
           
           MimeBodyPart mbp1 = new MimeBodyPart();
           mbp1.setText(mensaje);
           
           MimeBodyPart mbp2 = new MimeBodyPart();
           
           // attach the file to the message
	        FileDataSource fds = new FileDataSource(archivo);
	        mbp2.setDataHandler(new DataHandler(fds));
	        mbp2.setFileName(fds.getName());
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbp1);
	        mp.addBodyPart(mbp2);
	        message.setContent(mp);
           // Send message
           Transport.send(message);

           System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
    }
   public static void main(String[] args) {
	   BotMail.enviaMail("sistema notarial","El impuesto de la escritura 1 se venció", "omar_tablas@hotmail.com");     
   }
}