package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // ⚠️ Cambia estos datos por los tuyos
    private static final String EMAIL_REMITENTE = "emma.fe.prez2@gmail.com";
    private static final String PASSWORD_APP = "fwgo uaer qjvr vcrx"; // App Password de Gmail, NO tu contraseña normal
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    private static Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMITENTE, PASSWORD_APP);
            }
        });
    }

    private static boolean enviar(String destinatario, String asunto, String cuerpoHtml) {
        try {
            Session session = crearSesion();
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(EMAIL_REMITENTE));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setContent(cuerpoHtml, "text/html; charset=UTF-8");

            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a: " + destinatario);
            return true;
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo a " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean enviarCorreoRegistro(String destinatario, String nombreCompleto) {
        String asunto = "Registro exitoso - Pendiente de activación";
        String cuerpo = "<h2>Hola " + nombreCompleto + "</h2>"
                + "<p>Tu cuenta ha sido registrada correctamente.</p>"
                + "<p><b>Estado:</b> Pendiente de activación por un administrador.</p>"
                + "<p>Te avisaremos por correo cuando tu cuenta sea revisada.</p>";
        return enviar(destinatario, asunto, cuerpo);
    }

    public static boolean enviarCorreoActivacion(String destinatario, String nombreCompleto) {
        String asunto = "Tu cuenta ha sido activada";
        String cuerpo = "<h2>Hola " + nombreCompleto + "</h2>"
                + "<p>¡Buenas noticias! Tu cuenta ha sido <b>ACTIVADA</b>.</p>"
                + "<p>Ya puedes iniciar sesión en el sistema.</p>";
        return enviar(destinatario, asunto, cuerpo);
    }

    public static boolean enviarCorreoRechazo(String destinatario, String nombreCompleto) {
        String asunto = "Tu registro ha sido rechazado";
        String cuerpo = "<h2>Hola " + nombreCompleto + "</h2>"
                + "<p>Lamentamos informarte que tu solicitud de registro ha sido <b>RECHAZADA</b>.</p>"
                + "<p>Si crees que esto es un error, contacta al administrador.</p>";
        return enviar(destinatario, asunto, cuerpo);
    }
}