package com.tmm.cryptology.rsa.impl;

import com.tmm.cryptology.rsa.RSA;
import com.tmm.cryptology.rsa.key.PrivateKey;
import com.tmm.cryptology.rsa.key.PublicKey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class RSAImpl implements RSA {

    //私钥
    private PrivateKey privateKey = new PrivateKey();
    //公钥
    private PublicKey publicKey = new PublicKey();

    /**
     * 构造函数
     * 创建RSA对象时会随机生成私钥和公钥
     */
    public RSAImpl() {
        BigInteger p = BigInteger.probablePrime(537, new SecureRandom());
        BigInteger q = BigInteger.probablePrime(487, new SecureRandom());
        BigInteger n = p.multiply(q);
        BigInteger fn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger prime;
        //得到大于2的16bit的与fn互质的素数
        do{
            prime = BigInteger.probablePrime(16, new SecureRandom());
        } while (prime.gcd(fn).intValue() != 1 && prime.compareTo(new BigInteger("3")) == -1);
        //设置公钥和私钥指数，以及他们的模数
        publicKey.setPublicExponent(prime);
        privateKey.setPrivateExponent(prime.modPow(new BigInteger("-1"), fn));
        privateKey.setModulus(n);
        publicKey.setModulus(n);
    }

    public RSAImpl(PrivateKey privateKey) {
        this(null, privateKey);
    }

    public RSAImpl(PublicKey publicKey) {
        this(publicKey, null);
    }

    /**
     * 构造函数，用公钥和密钥构造
     * @param publicKey
     * @param privateKey
     */
    public RSAImpl(PublicKey publicKey, PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * 加密
     * @param data 要加密的原文
     * @param keyType 密钥类型：public, private
     * @return
     */
    @Override
    public byte[] encrypt(byte[] data, String keyType) {
        return common(data,keyType);
    }

    /**
     * 解密
     * @param data 待解密的密文
     * @param keyType 密钥类型：pubic，private
     * @return 加密后的字节数组
     */
    @Override
    public byte[] decrypt(byte[] data, String keyType) {
        return common(data, keyType);
    }

    /**
     * 加密解密函数的公用部分
     * 因为加解密的算法类似，所以可以将其提取出来
     * 注意BigInteger的取模方法，当被除数是负数时，其 a mod b = -((-a) mod b) + b
     * 为了使加密和解密能得到正确的结果，明文和密文在进行模指运算前要统一取正数，即signum要设置为1
     * 在将正数转换成字节数组，即使用BigInteger.toByteArray时，如果第一个字节是负数，
     * 该函数会在第一个字节前再加上一个全0字节作为符号位，所以当byte[0]=0时，我们应该将其截取
     * @param data
     * @param keyType
     * @return 解密后的字节数组
     */
    private byte[] common(byte[] data, String keyType) {
        if (keyType == "public") {
            BigInteger publicExponent = publicKey.getPublicExponent();
            byte[] bytes = new BigInteger(1, data).modPow(publicExponent, publicKey.getModulus()).toByteArray();
            //截取符号位
            if (bytes[0] == 0) {
                return Arrays.copyOfRange(bytes, 1, bytes.length);
            }
            return bytes;
//            return Arrays.copyOfRange(bytes, 1, bytes.length);
        }
        if (keyType == "private") {
            BigInteger privateExponent = privateKey.getPrivateExponent();
            byte[] bytes = new BigInteger(1, data).modPow(privateExponent, privateKey.getModulus()).toByteArray();
            //截取符号位
            if (bytes[0] == 0) {
                return Arrays.copyOfRange(bytes, 1, bytes.length);
            }
            return bytes;
        }
        return new byte[0];
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
