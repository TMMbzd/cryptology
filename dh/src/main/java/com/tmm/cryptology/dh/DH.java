package com.tmm.cryptology.dh;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class DH {

    //大素数
    private static final BigInteger p = new BigInteger("18415730869705997939326059442571018665871283285899682971388375520183806644222118031300051930540840885509337145302271847142706470914762703183229922441853523");
    //p的最小原根
    private static final BigInteger a = BigInteger.TWO;

    /**
     * 计算公开密钥
     * @param secretKey
     * @return
     */
    public static byte[] generatePublicKey(byte[] secretKey) {
        BigInteger sk = new BigInteger(1, secretKey);
        BigInteger r = a.modPow(sk, p);
        byte[] bytes = r.toByteArray();
        return commonFunc(bytes);
    }

    /**
     * 计算共享密钥
     * @param publicKey
     * @param secretKey
     * @return
     */
    public static byte[] generateSharedKey(byte[] publicKey, byte[] secretKey) {
        BigInteger pk = new BigInteger(1, publicKey);
        BigInteger sk = new BigInteger(1, secretKey);
        BigInteger r = pk.modPow(sk, p);
        byte[] bytes = r.toByteArray();
        return commonFunc(bytes);
    }

    /**
     * 截取符号字节
     * @param bytes
     * @return
     */
    private static byte[] commonFunc(byte[] bytes) {
        if (bytes[0] == 0) {
            return Arrays.copyOfRange(bytes, 1, bytes.length);
        }
        return bytes;
    }
}
