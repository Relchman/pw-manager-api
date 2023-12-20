package com.aplicacao.pw.manager.pwmanagerapi.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordServiceTest {

    @Autowired
    private PasswordServiceImpl passwordService;

    @DisplayName("Teste inicial de passwordService")
    @Test
    public void givenCidadeId_whenGetCidadeById_thenReturnCidadeObject() {
        String test = passwordService.test();
        assertThat(test).isNotNull();
    }

    @DisplayName("Testing containsUppercase - positive scenario")
    @Test
    public void containsUppercase_positiveScenario() {
        // given -- string with uppercase
        String password = "pasSword";

        // when
        boolean teste = passwordService.containsUppercase(password);

        // then
        System.out.println("----------------------");
        System.out.println(teste);
        assertThat(teste).isTrue();
    }

    @DisplayName("Testing containsUppercase - negative scenario")
    @Test
    public void containsUppercase_negativeScenario() {
        // given -- string with uppercase
        String password = "pas$word";

        // when
        boolean teste = passwordService.containsUppercase(password);

        // then
        System.out.println("----------------------");
        System.out.println(teste);
        assertThat(teste).isFalse();
    }

    @DisplayName("Teste minRequirements")
    @Test
    public void testMinReq() {
        boolean teste = passwordService.checkMinimunRequirements("password");
        System.out.println("----------------------");
        System.out.println(teste);
        assertThat(teste).isTrue();
    }
}
