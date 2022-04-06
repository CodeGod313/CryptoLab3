package com.cleverdeath.cryptolabthird.service.impl;

import com.cleverdeath.cryptolabthird.service.RabinEncryptionService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RabinEncryptionServiceImpl implements RabinEncryptionService {

    public static final String REGEX_ANY_LETTER = "[A-Z_]";

    @Override
    public String encrypt(String message, Integer publicKey) {
        StringBuilder encryptedSequence = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            int square = message.charAt(i) * message.charAt(i);
            char encryptedCharacter = (char) (square % publicKey);
            encryptedSequence.append(encryptedCharacter);
        }
        return encryptedSequence.toString();
    }

    public String decrypt(String message, Integer privateKeyP, Integer privateKeyQ) {
        StringBuilder decryptedMessage = new StringBuilder();
        List<Integer> lettersPositions = new ArrayList<>();
        int publicKey = privateKeyP * privateKeyQ;
        for (int i = 0; i < message.length(); i++) {
            int currentCharNumber = message.charAt(i);
            int mP = (int) (Math.pow(currentCharNumber, (privateKeyP + 1) / 4) % privateKeyP);
            int mQ = (int) (Math.pow(currentCharNumber, (privateKeyQ + 1) / 4) % privateKeyQ);
            List<Integer> coefficients = extendedEuclideanAlgorithm(privateKeyP, privateKeyQ);
            int yP = coefficients.get(0);
            int yQ = coefficients.get(1);
            List<Integer> possibleDecryption = new ArrayList<>();
            possibleDecryption.add(Math.abs((yP * privateKeyP * mQ + yQ * privateKeyQ * mP) % publicKey));
            possibleDecryption.add(Math.abs(publicKey - possibleDecryption.get(0)));
            possibleDecryption.add(Math.abs((yP * privateKeyP * mQ - yQ * privateKeyQ * mP) % publicKey));
            possibleDecryption.add(Math.abs(publicKey - possibleDecryption.get(2)));
            List<Character> decryptedCharacters = possibleDecryption.stream()
                    .filter(x -> String.valueOf((char) x.intValue()).matches(REGEX_ANY_LETTER))
                    .map(x -> (char) x.intValue())
                    .toList();
            Set<Character> decryptedCharactersSet = new HashSet<>(decryptedCharacters);
            if (decryptedCharactersSet.size() == 1) {
                decryptedMessage.append(decryptedCharacters.get(0));
            } else {
                decryptedMessage.append("[");
                decryptedCharactersSet.forEach(x -> {
                    decryptedMessage.append(x);
                    decryptedMessage.append(",");
                });
                decryptedMessage.deleteCharAt(decryptedMessage.length() - 1);
                decryptedMessage.append("]");
            }
        }
        return decryptedMessage.toString();
    }


    private List<Integer> extendedEuclideanAlgorithm(int a, int b) {
        int d0 = a;
        int d1 = b;
        int x0 = 1;
        int x1 = 0;
        int y0 = 0;
        int y1 = 1;
        while (d1 > 1) {
            int q = d0 / d1;
            int d2 = d0 % d1;
            int x2 = x0 - q * x1;
            int y2 = y0 - q * y1;
            d0 = d1;
            d1 = d2;
            x0 = x1;
            x1 = x2;
            y0 = y1;
            y1 = y2;
        }
        return List.of(x1, y1);
    }

}
