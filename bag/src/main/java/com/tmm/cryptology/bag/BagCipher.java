package com.tmm.cryptology.bag;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BagCipher {

    private List<BigInteger> cipherSequence = new ArrayList<>();

    public BagCipher() {
    }

    public void addCipher(BigInteger...cipher) {
        for (BigInteger c : cipher) {
            cipherSequence.add(c);
        }
    }

    public List<BigInteger> getCipherSequence() {
        return cipherSequence;
    }
}
