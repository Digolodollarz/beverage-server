package tech.diggle.apps.qikpay.mail

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}