package tech.diggle.apps.qikpay.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(@Autowired val emailSender: JavaMailSender) : EmailService {

    override fun sendSimpleMessage(
            to:String,  subject:String,  text:String) {
        try {
            val message = SimpleMailMessage()
            message.setTo(to)
            message.from = "qikpaynoreply@diggle.tech"
            message.subject = subject
            message.text = text
            emailSender.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}