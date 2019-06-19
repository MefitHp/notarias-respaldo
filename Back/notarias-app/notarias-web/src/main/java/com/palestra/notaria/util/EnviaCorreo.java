package com.palestra.notaria.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class EnviaCorreo {
 
//    private final static String HOST = "mail.palestra.mx";
	private final static String HOST = "smtp.gmail.com";
    private final static String TLS = "true";
    private final static String AUTH = "true";
    private final static String PORT = "587";
    private final static String TRANSPORT = "smtp";
    private final static String CHARSET = "ISO-8859-1";
//    private final static String ACCOUNT = "info@notaria98y24.palestra.mx";
//    Cueta para pruebas, cambiar al tener la cuenta de palestra
    private final static String ACCOUNT = "notarias9824@gmail.com";
    private final static String PASS = "p4l3str4";
    private final static String URI_CONFIG_FILE = "/palestra/devs/.mail";
 
    public static void enviar(String destino, String mensaje, String subject, boolean isHtml) throws IOException, Exception {
        Properties propMail = new Properties();
        File file = new File(URI_CONFIG_FILE);
        String host, tls, port, usuario, clave, auth, transport, charset;
        if (file.exists()) {
            propMail.load(new FileInputStream(file));
            host = propMail.getProperty("host");
            tls = propMail.getProperty("tls");
            port = propMail.getProperty("port");
            auth = propMail.getProperty("authenticate");
            transport = propMail.getProperty("transport");
            charset = propMail.getProperty("charset");
            usuario = propMail.getProperty("mail");
            clave = propMail.getProperty("mailkey");
        } else {
            host = HOST;
            tls = TLS;
            port = PORT;
            auth = AUTH;
            transport = TRANSPORT;
            charset = CHARSET;
            usuario = ACCOUNT;
            clave = PASS;
        }
        try {
            Properties props = new Properties();
 
            props.setProperty("mail.smtp.host", host); // Nombre del host de correo
            props.setProperty("mail.smtp.starttls.enable", tls); // TLS si estรก disponible
            props.setProperty("mail.smtp.port", port); // Puerto de gmail para envio de correos
            props.setProperty("mail.smtp.user", usuario); // Nombre del usuario
            props.setProperty("mail.smtp.auth", auth); // Si requiere o no usuario y password para conectarse.
 
            System.out.print("PalestraDevs ::: Starting session");
            Session session = Session.getDefaultInstance(props);// Prepara sesion
            System.out.println("\t\t[OK]");
 
            System.out.print("PalestraDevs ::: Creating the MimeMessage");
            MimeMessage message = new MimeMessage(session); // Construye mensaje simple de texto
            System.out.println("\t[OK]");
 
            System.out.print("PalestraDevs ::: Setting the message");
            System.out.print("\nmessage.setFrom(new InternetAddress(" + usuario + "))");
            message.setFrom(new InternetAddress(usuario)); // Quien envia el correo
            System.out.print("\nmessage.addRecipient(Message.RecipientType.TO, new InternetAddress(" + destino + "))");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino)); // A quien va dirigido
            System.out.print("\nmessage.setSubject(" + subject + ")");
            message.setSubject(subject); // Asunto
            if (isHtml) {
                //message.setText(mensaje, CHARSET); // Mensaje de texto html con negritas y cursiva
                message.setContent(mensaje, "text/html; charset="+charset); // Mensaje de texto html con negritas y cursiva
            } else {
                message.setText(mensaje); // Mensaje de texto plano
            }
            System.out.println("\t[OK]");
 
            System.out.print("PalestraDevs ::: Starting transport");
            Transport t = session.getTransport(transport);
            System.out.println("\t\t[Ok]");
 
            System.out.print("PalestraDevs ::: Trying to connect -" + host +"-");
            t.connect(HOST, usuario, clave); // Establece la conexion
            System.out.println("\t[Ok]");
 
            System.out.print("PalestraDevs ::: Trying to send message to " + destino);
            t.sendMessage(message, message.getAllRecipients()); // Envia el mensaje
            System.out.println("\t[Ok]");
            System.out.print("PalestraDevs ::: Ready to closing ");
            t.close(); //Cierra conexion
            System.out.println("\t\t[Ok]");
 
        } catch (AddressException ae) {
            System.out.println("PalestraDevsException (AddressExcption):" + ae);
            ae.printStackTrace(System.out);
        } catch (MessagingException me) {
            System.out.println("PalestraDevsException (MessagingException): " + me);
            me.printStackTrace(System.out);
        }
    }
}
