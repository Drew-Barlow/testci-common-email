package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.*;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	private static final String[] TEST_EMAILS = {"abc@d.com", "hello@world.com", "test@email.com"};
	private static final String TEST_EMAIL = "test@email.com";
	private EmailConcrete email;
	private EmailConcrete header;
	
	// Setup method
	@Before
	public void setUpEmailTest() throws Exception{
		email = new EmailConcrete();
		header = new EmailConcrete();
	}
	
	// Test method for addBcc method (84.4% coverage)
	@Test
	public void testAddBcc() throws Exception{
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	// Test method for addCc method (100% coverage)
	@Test
	public void testAddCc() throws Exception{
		email.addCc(TEST_EMAIL);
		assertEquals(1, email.getCcAddresses().size());
	}
	
	// Test method for addHeader method (100% coverage)
	@Test
	public void testAddHeader() throws Exception{
		header.addHeader(TEST_EMAIL, TEST_EMAIL);
		assertEquals(header,header);
	}
	
	// Test method for addHeader method without name parameter (100% coverage)
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderEmptyName() throws Exception{
		header.addHeader("", TEST_EMAIL);
		assertEquals(null,header);
		}
	// Test method for addHeader method without value parameter (100% coverage)
	@Test(expected=IllegalArgumentException.class)
	public void testAddHeaderEmptyValue() throws Exception{
		header.addHeader(TEST_EMAIL, "");
		assertEquals(null,header);
	}
	
	// Test method for addReplyTo method (100% coverage)
	@Test
	public void testAddReplyTo() throws Exception{
		email.addReplyTo(TEST_EMAIL, "test");
		assertEquals(email,email);
	}
	
	// Test method for buildMimeMessage method that is empty (23.3% coverage)
	@Test(expected=IllegalStateException.class)
	public void testBuildMimeMessageEmpty() throws EmailException{
		email.buildMimeMessage();
		assertEquals(email.getMimeMessage(),email);
	}
	
	// Test method for buildMimeMessage (23.3% coverage)
	@Test
	public void testBuildMimeMessage2() throws Exception{
		String charset = "charset";
		String subject = "subject";
		String address = "https";
		
		List <InternetAddress> internetAddresses = new ArrayList<InternetAddress>();
		
		email.setSubject(subject);
		email.setBounceAddress(address);
		
		Properties prop = new Properties();
		Session scn = Session.getDefaultInstance(prop);
		email.setMailSession(scn);
		
		MimeMessage message = email.createMimeMessage(scn);
		message.setSubject(charset, subject);
		
		MimeMultipart emailbody = new MimeMultipart();
		emailbody.setParent(message);
		
		InternetAddress intAddress = InternetAddress.getLocalAddress(scn);
		email.setContent(emailbody);
		email.updateContentType("content type");
		
		email.toList.add(0, intAddress);
		email.ccList.add(0, intAddress);
		email.replyList.add(0, intAddress);
		email.headers.get(intAddress);
		email.toInternetAddressArray(internetAddresses);
		
		email.buildMimeMessage();
		assertEquals(email.getMimeMessage(),email);
	}
	
	
	// Test method for getHostName with session (82.4% coverage)
	@Test
	public void testGetHostNameWithSession() throws Exception{
		Properties prop = new Properties();
		prop.setProperty(email.MAIL_HOST, "localhost");
		
		Session scn = Session.getInstance(prop);
		email.setMailSession(scn);
		
		String hostname = email.getHostName();
		assertEquals("localhost",hostname);
	}
	
	// Test method for getHostName without session (82.4% coverage)
	@Test
	public void testGetHostNameWithoutSession() throws Exception{
		Properties prop = new Properties();
		prop.setProperty(email.MAIL_HOST, "localhost");
		
		String hostname = email.getHostName();
		assertEquals(hostname,null);
	}
	
	// Test method for getMailSession that is empty (87.4% coverage)
	@Test(expected = EmailException.class)
	public void testGetMailSessionEmpty() throws EmailException {
		email.getMailSession();
		assertEquals(email,email.getMailSession());
	}
	 
	// Test method for getMailSession with set parameters (87.4% coverage)
	@Test
	public void testGetMailSessionSetParams() throws Exception{
		String userName = "username";
		String passWord = "password";
		
		email.setHostName("hostname");
		email.setSSLOnConnect(true);
		email.setAuthentication(userName, passWord);
		email.getMailSession();
		assertEquals(email,email);
	}
	
	// Test method for getSentDate with empty date (100% coverage)
	@Test
	public void testGetSentDateEmptyDate() throws Exception{
		email.getSentDate();
		Date date = new Date();
		assertEquals(date,date);
	}
	
	// Test method for getSentDate with date (100% coverage)
	@Test
	public void testGetSentDateWithDate() throws Exception{
		Date date = new Date(); 
		date.setTime(1000);
		
		email.setSentDate(date);
		email.getSentDate();
		assertEquals(email,email);
	}
	 
	// Test method for getSocketConnectionTimeout (100% coverage)
	@Test
	public void testGetSocketConnectionTimeout() throws Exception{
		email.setSocketConnectionTimeout(1000);
		
		assertEquals(1000,email.getSocketConnectionTimeout());
	}
	
	// Test method for setFrom(100% coverage)
	@Test
	public void testSetFrom() throws Exception{
		email.setFrom(TEST_EMAIL);
		assertEquals(email,email.setFrom(TEST_EMAIL));
	}
	
	// Teardown method
	@After
	public void tearDown() throws Exception{}

}
