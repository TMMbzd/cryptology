package com.tmm.cryptology.signature.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.tmm.cryptology.rsa.RSA;
import com.tmm.cryptology.rsa.impl.RSAImpl;
import com.tmm.cryptology.rsa.key.PrivateKey;
import com.tmm.cryptology.rsa.key.PublicKey;
import com.tmm.cryptology.rsa.util.KeyType;
import com.tmm.cryptology.signature.Signer;

import java.util.Arrays;

public class SignerImpl implements Signer {

    /**
     * 签名
     * 先将原文计算成MD5摘要，再用私钥进行签名
     * @param data
     * @param privateKey
     * @return
     */
    @Override
    public byte[] sign(byte[] data, PrivateKey privateKey) {
        //用私钥构造RSA对象
        RSA rsa = new RSAImpl(privateKey);
        //计算MD5摘要
        byte[] digest = SecureUtil.md5().digest(data);
        //用私钥对摘要进行加密
        return rsa.encrypt(digest, KeyType.PRIVATE_KEY);
    }

    /**
     * 签名验证
     * 用私钥将签名解密，得到的数据与原文的MD5摘要进行比较，如果相同则通过
     * @param data
     * @param signed
     * @param publicKey
     * @return
     */
    @Override
    public boolean verify(byte[] data, byte[] signed, PublicKey publicKey) {
        //用公钥构造rsa对象
        RSA rsa = new RSAImpl(publicKey);
        //计算摘要
        byte[] digest = SecureUtil.md5().digest(data);
        //用公钥解密签名
        byte[] decrypt = rsa.decrypt(signed, KeyType.PUBLIC_KEY);
        //比较摘要和签名解密后的内容是否相同
        if (Arrays.equals(digest, decrypt)) {
            return true;
        }
        return false;
    }
}
