package com.tmm.cryptology.rsa;

import com.tmm.cryptology.rsa.key.PrivateKey;
import com.tmm.cryptology.rsa.key.PublicKey;

public interface RSA {

    byte[] encrypt(byte[] data, String keyType);
    byte[] decrypt(byte[] data, String keyType);
    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
}
