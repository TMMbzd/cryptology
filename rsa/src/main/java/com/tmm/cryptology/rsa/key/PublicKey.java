package com.tmm.cryptology.rsa.key;

import java.math.BigInteger;

public class PublicKey {

    //指数d
    private BigInteger d;
    //模数n
    private BigInteger n;

    //设置指数
    public void setPublicExponent(BigInteger d) {
        this.d = d;
    }

    //设置模数
    public void setModulus(BigInteger n) {
        this.n = n;
    }

    //获取公钥的指数
    public BigInteger getModulus() {
        return n;
    }

    //获取公钥的模数
    public BigInteger getPublicExponent() {
        return d;
    }
}
