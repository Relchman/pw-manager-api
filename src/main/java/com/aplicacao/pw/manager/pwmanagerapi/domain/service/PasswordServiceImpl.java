package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public Long calculatePasswordScore(String password) {
        return 1l;
    }

    @Override
    public String test() {
        return "Sucess";
    }

    public boolean checkMinimunRequirements(String password) {
        return (checkMinLength(password) && checkContent(password));
    }

    public boolean checkMinLength(String password) {
        return true;
    }

    public boolean checkContent(String password) {
        Long conditionsMetCounter = 0L;
        if (containsUppercase(password)) {
            conditionsMetCounter += 1;
        }
        if (containsLowercase(password)) {
            conditionsMetCounter += 1;
        }
        if (containsNumber(password)) {
            conditionsMetCounter += 1;
        }
        if (containsSymbol(password)) {
            conditionsMetCounter += 1;
        }

        if (conditionsMetCounter >= 3) {
            return true;
        }
        return false;
    }

    public boolean containsUppercase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean containsLowercase(String password) {
        return true;
    }

    public boolean containsNumber(String password) {
        return true;
    }

    public boolean containsSymbol(String password) {
        return true;
    }
}
