package com.payrollSystem.java;

import java.util.Properties;
import java.io.File;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSend {
	
	protected static <BodyPart> void MailSending(String toAddress) {
		final String username= "sakthiparameswaran0128@outlook.com";
		final String password= "Sakthi@2003";
		Properties properties  = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "outlook.office365.com");
		properties.put("mail.smtp.port", 587);
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			message.setSubject("Salary PaySlip");
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find attached the payroll PDF.");
            
            String filePathString = "C:\\Users\\sakthi_n\\eclipse-workspace\\PayrollSystem\\PaySlip.pdf";
            
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            DataSource source = new FileDataSource(filePathString);
            pdfAttachment.setDataHandler(new DataHandler(source));
            pdfAttachment.setFileName(new File(filePathString).getName());
            
            
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(pdfAttachment);
            message.setContent(multipart);
			
			Transport.send(message);
			System.out.println("Email sent successfully!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
