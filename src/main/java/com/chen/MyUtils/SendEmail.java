package com.chen.MyUtils;

import com.chen.config.MyStaticProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

    @Autowired
    private MailSender mailSender;

    @Getter
    @Setter
    private Boolean already = Boolean.FALSE; //判断是否发送了


    //重载
    public void send(String setTo, String content) {
        send(setTo, MyStaticProperties.getMyEmail(), content);
    }

    public void send(String setTo, String setFrom, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("WhiteCat");
        message.setText(content);

        message.setTo(setTo);
        message.setFrom(setFrom);

        setAlready(true);

        mailSender.send(message);
    }

}
