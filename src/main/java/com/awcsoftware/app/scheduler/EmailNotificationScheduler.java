package com.awcsoftware.app.scheduler;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.mail.MailConfig;
import com.awcsoftware.app.mail.MailMessageConstants;
import com.awcsoftware.app.timesheet.TimecardDao;
import com.awcsoftware.app.timesheet.TimecardInfo;
import com.awcsoftware.mybatis.DbException;

@Component
public class EmailNotificationScheduler {
	
	@Autowired
	TimecardDao dao;

	@Autowired
	MailConfig mailconfig;

	/*
	 * public TimecardInfo getTimecardStatus() { dao= new TimecardDao();
	 * List<TimecardInfo> timecard=dao.getPreviousWeekStatus();
	 * Iterator<TimecardInfo> itr = timecard.iterator(); TimecardInfo timecardInfo =
	 * null; while(itr.hasNext()) { timecardInfo =(TimecardInfo)itr.next();
	 * System.out.println("timecardInfo "+timecardInfo.getEmail());
	 * if(timecardInfo.getStatus().equalsIgnoreCase("draft")) {
	 * sendTuesdayEmailNotofication(); } } return timecardInfo;
	 * 
	 * }
	 */
	@Scheduled(cron = "0 37 23 * * WED")
	public String sendTuesdayEmailNotofication() throws AppException, DbException {

		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		emailExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println(this + " Current time is :: " + Calendar.getInstance().getTime());
					MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					List<TimecardInfo> timecard = dao.getPreviousWeekStatus();
					for (TimecardInfo sendTo : timecard) {
						System.out.println("emails " + sendTo.getEmail());
						helper.setTo(sendTo.getEmail());
						helper.setSubject(MailMessageConstants.ChangePasswordSubject.getLabel().toString());
						helper.setText("Test");

						mailconfig.javaMailSender().send(message);
						Thread.sleep(1000);

					}
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		emailExecutor.shutdown();
		/*
		 * MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		 * MimeMessageHelper helper = new MimeMessageHelper(message, true); dao = new
		 * TimecardDao(); List<TimecardInfo> timecard = dao.getPreviousWeekStatus(); for
		 * (TimecardInfo sendTo : timecard) { System.out.println("emails " +
		 * sendTo.getEmail()); helper.setTo(sendTo.getEmail());
		 * helper.setSubject(MailMessageConstants.ChangePasswordRequest.getLabel().
		 * toString()); helper.setText("Test"); if
		 * (sendTo.getStatus().equalsIgnoreCase("draft")) {
		 * mailconfig.javaMailSender().send(message); } }
		 */

		return "mail sent successfully";

	}
}
