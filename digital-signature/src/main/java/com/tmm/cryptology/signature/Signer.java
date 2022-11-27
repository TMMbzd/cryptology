package com.tmm.cryptology.signature;

import com.tmm.cryptology.rsa.key.PrivateKey;
import com.tmm.cryptology.rsa.key.PublicKey;

public interface Signer {

    byte[] sign(byte[] data, PrivateKey privateKey);
    boolean verify(byte[] data, byte[] signed, PublicKey publicKey);
}
