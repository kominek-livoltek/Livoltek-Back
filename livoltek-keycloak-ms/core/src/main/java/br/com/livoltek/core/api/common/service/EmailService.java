package br.com.livoltek.core.api.common.service;

public interface EmailService {

    void sendEmail(String destinatario, String comCopia, String assunto, String conteudoHtml);
}
