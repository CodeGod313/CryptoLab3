package com.cleverdeath.cryptolabthird.service.impl;

import com.cleverdeath.cryptolabthird.service.RabinEncryptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RabinEncryptionServiceImplTest {

    RabinEncryptionService rabinEncryptionService;
    Integer publicKey;
    Integer privateKeyP;
    Integer privateKeyQ;

    @BeforeAll
    void setUp() {
        rabinEncryptionService = new RabinEncryptionServiceImpl();
        privateKeyP = 11;
        privateKeyQ = 19;
        publicKey = privateKeyP * privateKeyQ;
    }

    @Test
    void encrypt() {
        String message = "HELLO_THERE";
        String actual = rabinEncryptionService.encrypt(message, publicKey);
        String expected = "¨£\u0085\u0085´&\u009F¨£$£";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void decrypt() {
        String message = "¨£\u0085\u0085´&\u009F¨£$£";
        String actual = rabinEncryptionService.decrypt(message, privateKeyP, privateKeyQ);
        String expected = "HELLO_THERE";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void popa() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 'а'; i < 'я'; i++) {
            System.out.print((char) i);
            stringBuilder.append(i);
            stringBuilder.append(" ");
        }
        System.out.println();
        System.out.println(stringBuilder);
    }
}