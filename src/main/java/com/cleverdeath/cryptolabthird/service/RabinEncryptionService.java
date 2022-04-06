package com.cleverdeath.cryptolabthird.service;

import java.util.List;

public interface RabinEncryptionService {
    String encrypt(String message, Integer publicKey);

    String decrypt(String message, Integer privateKeyP, Integer privateKeyQ);

}
