package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

public interface PasswordService {
    Long calculatePasswordScore(String password);

    String test();
}
