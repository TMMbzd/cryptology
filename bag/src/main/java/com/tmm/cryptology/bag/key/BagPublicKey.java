package com.tmm.cryptology.bag.key;

import java.math.BigInteger;

public class BagPublicKey {

    private BigInteger[] sequence;
    private int size;
   /* private BigInteger modulus;
    private BigInteger multiplier;*/

    public BagPublicKey(BagPrivateKey privateKey) {
        this.size = privateKey.getSize();
        BigInteger modulus = privateKey.getModulus();
        BigInteger multiplier = privateKey.getModulus();
        BigInteger[] privateKeySequence = privateKey.getSequence();
        sequence = new BigInteger[privateKeySequence.length];
        for(int i = 0; i < privateKeySequence.length; i++) {
            sequence[i] = privateKeySequence[i].multiply(multiplier).mod(modulus);
        }
    }

    public BigInteger[] getSequence() {
        return sequence;
    }

    public void setSequence(BigInteger[] sequence) {
        this.sequence = sequence;
    }

    public int getSize() {
        return size;
    }
}
