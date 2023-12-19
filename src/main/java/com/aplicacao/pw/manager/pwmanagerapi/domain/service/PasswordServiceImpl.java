package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public Long calculatePasswordScore(String password) {
        long score = 0;

        if (password.length() >= 8) {
            score += 5;
        }else{
            score += 2;
        }

        return score;
    }
}
