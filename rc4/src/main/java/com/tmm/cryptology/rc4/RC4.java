package com.tmm.cryptology.rc4;

public interface RC4 {

    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
    void setInitKey(byte[] initKey);
    byte[] getKeys();
}
