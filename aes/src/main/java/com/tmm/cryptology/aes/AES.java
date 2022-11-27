package com.tmm.cryptology.aes;

public interface AES {

    void setKey(byte[] key);
    byte[] getKey();
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] bytes);
}
