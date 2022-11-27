package com.tmm.cryptology.bag;

import com.tmm.cryptology.bag.key.BagPrivateKey;
import com.tmm.cryptology.bag.key.BagPublicKey;

import java.math.BigInteger;
import java.util.List;

public class Bag {

    private BagPrivateKey privateKey = new BagPrivateKey();
    private BagPublicKey publicKey = new BagPublicKey(privateKey);

    public BagCipher encrypt(byte[] data) {

        BagCipher cipher = new BagCipher();
        BigInteger[] sequence = publicKey.getSequence();
        int size = publicKey.getSize();
        int wordSize = size / 8;
        int loop1 = data.length/wordSize;
        if (data.length % wordSize != 0) {
            loop1++;
        }
        for (int i = 0; i < loop1; i++) {
            BigInteger sum = BigInteger.ZERO;
            int loop2 = i * wordSize + wordSize;
            if (i == loop1 - 1) {
                loop2 = i * wordSize + data.length % wordSize;
            }
            for (int j = i * wordSize; j < loop2; j++) {
                int offset = 0;
                for (int k = 0; k < 8; k++) {
                    offset = j - i * wordSize + k;
                    if(isOne(data[j], k)) {
                        sum = sum.add(sequence[offset]);
                    }
                }
            }
            cipher.addCipher(sum);
        }
        return cipher;
    }

    public byte[] decrypt(BagCipher cipher) {

        List<BigInteger> cipherSequence = cipher.getCipherSequence();


        for (int i = 0; i < cipherSequence.size(); i++) {

            for(int j = privateKey.getSize(); j >= 0; j--) {

            }
        }

        return null;
    }

    private boolean isOne(byte b, int offset) {
        int val = 0;
        int[] mask = new int[]{128, 64, 32, 16, 8, 4, 2, 1};
        val = (int)b & mask[offset];
        if (val == mask[offset]) {
            return true;
        }
        return false;
    }
}
