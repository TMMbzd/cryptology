package com.tmm.cryptology.rsa.key;

import java.math.BigInteger;

public class PrivateKey {

    //私钥的指数
    private BigInteger e;

    //私钥的模数
    private BigInteger n;

    //设置私钥指数
    public void setPrivateExponent(BigInteger e) {
        this.e = e;
    }

    //设置私钥模数
    public void setModulus(BigInteger n) {
        this.n = n;
    }

    //获取模数
    public BigInteger getModulus() {
        return n;
    }

    //获取指数
    public BigInteger getPrivateExponent() {
        return e;
    }
}
