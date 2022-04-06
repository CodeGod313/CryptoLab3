package com.cleverdeath.cryptolabthird;

import com.cleverdeath.cryptolabthird.fileprocessor.FileProcessor;
import com.cleverdeath.cryptolabthird.fileprocessor.impl.FileProcessorImpl;
import com.cleverdeath.cryptolabthird.service.RabinEncryptionService;
import com.cleverdeath.cryptolabthird.service.impl.RabinEncryptionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeneralApplicationTest {

    RabinEncryptionService rabinEncryptionService;
    FileProcessor fileProcessor;
    String inputFilePath;
    String outputFilePath;
    Integer publicKey;
    Integer privateKeyQ;
    Integer privateKeyP;

    @BeforeAll
    void setUp(){
        rabinEncryptionService = new RabinEncryptionServiceImpl();
        fileProcessor = new FileProcessorImpl();
        inputFilePath = "src/test/resources/input.txt";
        outputFilePath = "src/test/resources/output.txt";
        privateKeyQ = 19;
        privateKeyP = 11;
        publicKey = privateKeyQ * privateKeyP;
    }

    @Test
    void test(){
        Path path = Paths.get(inputFilePath);
        String message = fileProcessor.readStringFromFile(path).get();
        String encryptedMessage = rabinEncryptionService.encrypt(message, publicKey);
        fileProcessor.writeStringToFile(encryptedMessage, outputFilePath);
        Path path2 = Paths.get(outputFilePath);
        String encryptedMessageFromFile = fileProcessor.readStringFromFile(path2).get();
        String decryptedMessage = rabinEncryptionService.decrypt(encryptedMessageFromFile, privateKeyP, privateKeyQ);
        Assertions.assertEquals(message, decryptedMessage);
    }
}
