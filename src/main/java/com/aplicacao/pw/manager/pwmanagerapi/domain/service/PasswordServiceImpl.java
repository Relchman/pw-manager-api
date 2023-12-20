package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Long calcAdditionsScore(String password) {
        Long score = calcNumberOfCharacters(password) + calcUppercaseLetters(password)
                + calcLowercaseLetters(password) + calcNumbers(password) + calcSymbols(password)
                + calcMiddleNumberOrSymbol(password) + calcRequirements(password);

        if (score>100L){return 100L;}
        return score;
    }

    public Long calcNumberOfCharacters(String password) {
        Long score = Long.valueOf(password.length() * 4);
        return score;
    }

    public Long calcUppercaseLetters(String password) {
        Long matches = regexCounter("[A-Z]", password);
        if (matches == 0L) {
            return 0L;

        }
        Long score = (Long.valueOf(password.length()) - matches) * 2;
        return score;
    }

    public Long calcLowercaseLetters(String password) {
        Long matches = regexCounter("[a-z]", password);
        if (matches == 0L) {
            return 0L;

        }
        Long score = (Long.valueOf(password.length()) - matches) * 2;
        return score;
    }

    public Long calcNumbers(String password) {
        Long matches = regexCounter("[0-9]", password);
        if (matches == 0L) {
            return 0L;
        }

        Long score = matches * 4L;
        return score;
    }

    public Long calcSymbols(String password) {
        Long matches = regexCounter("[^A-Za-z0-9]", password);
        if (matches == 0L) {
            return 0L;
        }

        Long score = matches * 6L;
        return score;
    }

    public Long calcMiddleNumberOrSymbol(String password) {
        password = removeFirstAndLastCharacter(password);
        Long matches = regexCounter("[^a-zA-z]", password);
        if (matches == 0L) {
            return 0L;
        }

        Long score = matches * 2L;
        return score;
    }

    public String removeFirstAndLastCharacter(String password) {
        password = password.substring(1, password.length() - 1);
        return password;
    }

    public Long calcRequirements(String password) {
        Long conditionsMetCounter = 0L;

        if (password.length() >= 8) {
            conditionsMetCounter += 1;
        }
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

        if (conditionsMetCounter <= 3) {
            return 0L;
        }

        return conditionsMetCounter * 2;
    }

    public boolean checkMinimumRequirements(String password) {
        return (checkMinLength(password) && checkContent(password));
    }

    public boolean checkMinLength(String password) {
        if (password.length() >= 8) {
            return true;
        }
        return false;
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
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean containsNumber(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean containsSymbol(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static Long regexCounter(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return Long.valueOf(matches);
    }
}
