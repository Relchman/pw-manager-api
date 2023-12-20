package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @InjectMocks
    private PasswordServiceImpl passwordService;

    public static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    @DisplayName("Teste inicial de passwordService")
    @Test
    public void givenCidadeId_whenGetCidadeById_thenReturnCidadeObject() {
        String test = passwordService.test();
        assertThat(test).isNotNull();
    }

    @DisplayName("Testing containsLowercase - positive scenario")
    @Test
    public void givenPassword_whenContainsLowercase_thenReturnTrue() {
        // given -- string with lowercase
        String password = "PAS$word";

        // when
        boolean teste = passwordService.containsLowercase(password);

        // then
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing containsLowercase - negative scenario")
    @Test
    public void givenPassword_whenNotContainsLowercase_thenReturnFalse() {
        // given -- string without lowercase
        String password = "PASSW0RD";

        // when
        boolean teste = passwordService.containsLowercase(password);

        // then
        assertThat(teste).isFalse();
    }

    @DisplayName("Testing containsUppercase - positive scenario")
    @Test
    public void givenPassword_whenContainsUppercase_thenReturnTrue() {
        // given -- string with uppercase
        String password = "pasSword";

        // when
        boolean teste = passwordService.containsUppercase(password);

        // then
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing containsUppercase - negative scenario")
    @Test
    public void givenPassword_whenNotContainsUppercase_thenReturnFalse() {
        // given -- string without uppercase
        String password = "pas$word";

        // when
        boolean teste = passwordService.containsUppercase(password);

        // then
        assertThat(teste).isFalse();
    }

    @DisplayName("Testing checkMinLength - positive scenario")
    @Test
    public void givenPassword_whenCheckMinLength_thenReturnTrue() {
        // given -- string with minimum length
        String password = "pas$word";

        // when
        boolean teste = passwordService.checkMinLength(password);

        // then
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing checkMinLength - negative scenario")
    @Test
    public void givenPassword_whenNotCheckMinLength_thenReturnTrue() {
        // given -- string with minimum length
        String password = "pas$wor";

        // when
        boolean teste = passwordService.checkMinLength(password);

        // then
        assertThat(teste).isFalse();
    }

    @DisplayName("Testing containsNumber - positive scenario")
    @Test
    public void givenPassword_whenContainsNumber_thenReturnTrue() {
        // given -- string with numbers
        String password = "pas$wor1";

        // when
        boolean teste = passwordService.containsNumber(password);

        // then
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing containsNumber - negative scenario")
    @Test
    public void givenPassword_whenNotContainsNumber_thenReturnFalse() {
        // given -- string withhout numbers
        String password = "pas$wor";

        // when
        boolean teste = passwordService.containsNumber(password);

        // then
        assertThat(teste).isFalse();
    }

    @DisplayName("Testing containsSymbol - positive scenario")
    @Test
    public void givenPassword_whenContainsSymbol_thenReturnTrue() {
        // given -- string with symbols
        String password = "pas$wor!1";

        // when
        boolean teste = passwordService.containsSymbol(password);

        // then
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing containsSymbol - negative scenario")
    @Test
    public void givenPassword_whenNotContainsSymbol_thenReturnFalse() {
        // given -- string with no symbols
        String password = "password";

        // when
        boolean teste = passwordService.containsSymbol(password);

        // then
        assertThat(teste).isFalse();
    }

    @DisplayName("Testing checkMinimumRequirements - positive scenarios")
    @Test
    public void givenGoodPasswords_whenCheckMinimumRequirements_thenReturnTrue() {
        // given -- good passwords
        String passwordWithoutUppercase = "p4s$word";
        String passwordWithoutLowercase = "P4S$WORD";
        String passwordWithoutNumbers = "PaS$word";
        String passwordWithoutSymbols = "p4sSword";

        // when
        boolean passwordWithoutUppercaseCheck = passwordService.checkMinimumRequirements(passwordWithoutUppercase);
        boolean passwordWithoutLowercaseCheck = passwordService.checkMinimumRequirements(passwordWithoutLowercase);
        boolean passwordWithoutNumbersCheck = passwordService.checkMinimumRequirements(passwordWithoutNumbers);
        boolean passwordWithoutSymbolsCheck = passwordService.checkMinimumRequirements(passwordWithoutSymbols);

        // then
        assertThat(passwordWithoutUppercaseCheck).isTrue();
        assertThat(passwordWithoutLowercaseCheck).isTrue();
        assertThat(passwordWithoutNumbersCheck).isTrue();
        assertThat(passwordWithoutSymbolsCheck).isTrue();
    }

    @DisplayName("Testing checkMinimumRequirements - negative scenarios")
    @Test
    public void givenBadPasswords_whenCheckMinimumRequirements_thenReturnTrue() {
        // given -- bad passwords
        String passwordLackingMinLength = "pa$Sw0r";
        String passwordLowercaseOnly = "password";
        String passwordUppercaseOnly = "PASSWORD";
        String passwordNumbersOnly = "12345678";
        String passwordSymbolsOnly = "!!*&#@!!";

        // when
        boolean passwordLackingMinLengthCheck = passwordService.checkMinimumRequirements(passwordLackingMinLength);
        boolean passwordLowercaseOnlyCheck = passwordService.checkMinimumRequirements(passwordLowercaseOnly);
        boolean passwordUppercaseOnlyCheck = passwordService.checkMinimumRequirements(passwordUppercaseOnly);
        boolean passwordNumbersOnlyCheck = passwordService.checkMinimumRequirements(passwordNumbersOnly);
        boolean passwordSymbolsOnlyCheck = passwordService.checkMinimumRequirements(passwordSymbolsOnly);

        // then
        assertThat(passwordLackingMinLengthCheck).isFalse();
        assertThat(passwordLowercaseOnlyCheck).isFalse();
        assertThat(passwordUppercaseOnlyCheck).isFalse();
        assertThat(passwordNumbersOnlyCheck).isFalse();
        assertThat(passwordSymbolsOnlyCheck).isFalse();
    }

    @Test
    public void givenText_whenSimpleRegexMatches_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo");

        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void givenRepeatedText_whenMatchesOnceWithDotMetach_thenCorrect() {
        int matches = runTest("foo.", "foofoo");

        assertEquals(matches, 1);
    }

    @Test
    public void givenText_whenSimpleRegexMatchesTwice_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foofoo");
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(matches, 2);
    }

    @Test
    public void givenPassword_whenCalcNumberOfCharacters_thenReturnValue() {
        // given password
        String password1 = "1234";
        String password2 = "1a234";
        String password3 = "as";
        String password4 = "1 3 ddd#4";

        // when calcNumberOfCharacters
        Long score1 = passwordService.calcNumberOfCharacters(password1);
        Long score2 = passwordService.calcNumberOfCharacters(password2);
        Long score3 = passwordService.calcNumberOfCharacters(password3);
        Long score4 = passwordService.calcNumberOfCharacters(password4);

        // then;
        assertEquals(score1, 16L);
        assertEquals(score2, 20L);
        assertEquals(score3, 8L);
        assertEquals(score4, 36L);
    }

    @Test
    public void givenPassword_whenCalcUppercaseLetters_thenReturnValue() {
        // given password
        String password1 = "1234";
        String password2 = "1Aa234";
        String password3 = "aabcAABs";
        String password4 = "ABCDE5";

        // when calcUppercaseLetters
        Long score1 = passwordService.calcUppercaseLetters(password1);
        Long score2 = passwordService.calcUppercaseLetters(password2);
        Long score3 = passwordService.calcUppercaseLetters(password3);
        Long score4 = passwordService.calcUppercaseLetters(password4);

        // then return correct value
        assertEquals(score1, 0L);
        assertEquals(score2, 10L);
        assertEquals(score3, 10L);
        assertEquals(score4, 2L);

    }

    @Test
    public void givenPassword_whenCalcLowercaseLetters_thenReturnValue() {
        // given password
        String password1 = "1234";
        String password2 = "1Aa234";
        String password3 = "aabcAABs";
        String password4 = "ABCDE5";

        // when calcLowercaseLetters
        Long score1 = passwordService.calcLowercaseLetters(password1);
        Long score2 = passwordService.calcLowercaseLetters(password2);
        Long score3 = passwordService.calcLowercaseLetters(password3);
        Long score4 = passwordService.calcLowercaseLetters(password4);

        // then return correct value
        assertEquals(score1, 0L);
        assertEquals(score2, 10L);
        assertEquals(score3, 6L);
        assertEquals(score4, 0L);

    }

    @Test
    public void givenPassword_whenCalcNumbers_thenReturnValue() {
        // given password
        String password1 = "Abc$1234";
        String password2 = "1Aa234$";
        String password3 = "332abc!";
        String password4 = "__xdxdABCDE5";

        // when calcLowercaseLetters
        Long score1 = passwordService.calcNumbers(password1);
        Long score2 = passwordService.calcNumbers(password2);
        Long score3 = passwordService.calcNumbers(password3);
        Long score4 = passwordService.calcNumbers(password4);

        // then return correct value
        assertEquals(score1, 16L);
        assertEquals(score2, 16L);
        assertEquals(score3, 12L);
        assertEquals(score4, 4L);

    }

    @Test
    public void givenPassword_whenCalcSymbols_thenReturnValue() {
        // given password
        String password1 = "Abc$1234";
        String password2 = "1Aa234$";
        String password3 = "332abc!";
        String password4 = "--xd$dA!@DE5";

        // when calcLowercaseLetters
        Long score1 = passwordService.calcSymbols(password1);
        Long score2 = passwordService.calcSymbols(password2);
        Long score3 = passwordService.calcSymbols(password3);
        Long score4 = passwordService.calcSymbols(password4);

        // then return correct value
        assertEquals(score1, 6L);
        assertEquals(score2, 6L);
        assertEquals(score3, 6L);
        assertEquals(score4, 30L);
    }

    @Test
    public void givenPassword_whenRemoveFirstAndLastCharacter_thenRemoveIt() {
        // given password
        String password1 = "12345678";
        String password2 = "1234";

        // when calcLowercaseLetters
        String newString1 = passwordService.removeFirstAndLastCharacter(password1);
        String newString2 = passwordService.removeFirstAndLastCharacter(password2);

        // then return correct value
        assertEquals("234567", newString1);
        assertEquals("23", newString2);
    }

    @Test
    public void givenPassword_whenCalcMiddleNumberOrSymbol_thenReturnValue() {
        // given password
        String password1 = "Abc$1234";
        String password2 = "1Aa234$";
        String password3 = "332abc!";
        String password4 = "--xd$dA!@DE5";

        // when calcLowercaseLetters
        Long score1 = passwordService.calcMiddleNumberOrSymbol(password1);
        Long score2 = passwordService.calcMiddleNumberOrSymbol(password2);
        Long score3 = passwordService.calcMiddleNumberOrSymbol(password3);
        Long score4 = passwordService.calcMiddleNumberOrSymbol(password4);

        // then return correct value
        assertEquals(8L, score1);
        assertEquals(6L, score2);
        assertEquals(4L, score3);
        assertEquals(8L, score4);
    }


    @Test
    public void givenPassword_whenCalcRequirements_thenReturnValue() {
        // given password
        String password1 = "12345";
        String password2 = "12345678";
        String password3 = "--xd$dA!@DE";
        String password4 = "--xd$dA!@DE5";

        // when calcLowercaseLetters
        Long score1 = passwordService.calcRequirements(password1);
        Long score2 = passwordService.calcRequirements(password2);
        Long score3 = passwordService.calcRequirements(password3);
        Long score4 = passwordService.calcRequirements(password4);

        // then return correct value
        assertEquals(0L, score1);
        assertEquals(0L, score2);
        assertEquals(8L, score3);
        assertEquals(10L, score4);
    }


    @Test
    public void givenPassword_whenCalcAdditionsScore_thenReturnValue() {
        // given password
        String password1 = "2!EgAbG5#";

        // when calcLowercaseLetters
        Long score1 = passwordService.calculatePasswordScore(password1);

        // then return correct value
        assertEquals(96L, score1);
    }
}
