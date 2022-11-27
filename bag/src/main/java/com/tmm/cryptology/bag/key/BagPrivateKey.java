package com.tmm.cryptology.bag.key;

import java.math.BigInteger;
import java.security.SecureRandom;

public class BagPrivateKey {

    private final int bitSize = 128;
    private int size = 128;
    private BigInteger[] sequence;
    private BigInteger modulus;
    private BigInteger multiplier;

    public BagPrivateKey() {
        keyConstructor(this.size);
    }

    public BagPrivateKey(int size) {
        keyConstructor(size);
    }

    private void keyConstructor(int size) {
        this.size = size;
        sequence = new BigInteger[size];
        BigInteger sum = BigInteger.ZERO;
        byte[] rand = new byte[bitSize/8];
        for (int i = 0; i < size; i++) {
            new SecureRandom().nextBytes(rand);
            sequence[i] = new BigInteger(1, rand).add(sum);
            sum = sum.add(sequence[i]);
        }
        new SecureRandom(rand);
        modulus = sum.add(new BigInteger(1, rand));
        BigInteger prime;
        do {
            prime = BigInteger.probablePrime(256, new SecureRandom());
        }while (!prime.gcd(modulus).equals(BigInteger.ONE));
        multiplier = prime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BigInteger[] getSequence() {
        return sequence;
    }

    public void setSequence(BigInteger[] sequence) {
        this.sequence = sequence;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public void setModulus(BigInteger modulus) {
        this.modulus = modulus;
    }

    public BigInteger getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigInteger multiplier) {
        this.multiplier = multiplier;
    }

    public int getBitSize() {
        return bitSize;
    }
}
