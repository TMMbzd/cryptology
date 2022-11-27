package com.tmm.cryptology.rc4.impl;

import com.tmm.cryptology.rc4.RC4;

import java.security.SecureRandom;
import java.util.ArrayList;

public class RC4Impl implements RC4 {

    //初始密钥
    private  byte[] initKey;

    //S表
    private byte[] S = new byte[256];

    //K表
    private byte[] K = new byte[256];

    //子密钥
    private byte[] keys;

    /**
     * 无参构造器，使用随机生成的256字节的初始密钥
     */
    public RC4Impl() {
        setInitKey(null);
    }

    public RC4Impl(byte[] initKey) {
        setInitKey(initKey);
    }

    /**
     * 加密
     * @param data 原文
     * @return 加密后的字节数组
     */
    @Override
    public byte[] encrypt(byte[] data) {
        generateKeys(data);
        byte[] bytes = new byte[data.length];
        for(int i = 0; i < data.length; i++) {
            bytes[i] = (byte) (data[i] ^ keys[i]);
        }
        return bytes;
    }


    /**
     * 解密
     * @param data 密文
     * @return 解密后的字节数组
     */
    @Override
    public byte[] decrypt(byte[] data) {
        byte[] bytes = new byte[data.length];
        for(int i = 0; i < data.length; i++) {
            bytes[i] = (byte) (data[i] ^ keys[i]);
        }
        return bytes;
    }

    /**
     * 设置初始密钥，设置后会自动更新S表和K表
     * 当初始密钥为null时，使用随机生成的长度为256的字节数组作为初始密钥
     * @param initKey 初始密钥
     */
    @Override
    public void setInitKey(byte[] initKey) {
        if(initKey == null) {
            byte[] randomBytes = new byte[256];
            new SecureRandom().nextBytes(randomBytes);
            this.initKey = randomBytes;
        } else {
            this.initKey = initKey;
        }
        //初始化S表
        for(int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }
        //填充K表
        for (int i = 0; i < 256; i++) {
            K[i] = this.initKey[i % this.initKey.length];
        }
        //置换S表
        int j = 0;
        for(int i = 0; i <256; i++) {
            j = (j + (S[i] & 0xff)+ (K[i] & 0xff))% 256;
            swap(S, i, j);
        }
    }

    @Override
    public byte[] getKeys() {
        return keys;
    }

    /**
     * 产生与原文等长的子密钥
     * @param data：原文
     */
    private void generateKeys(byte[] data) {
        keys = new byte[data.length];
        int i = 0;
        int j = 0;
        for (int r = 0; r < data.length; r++) {
            i = (i + 1) % 256;
            j = (j + (S[i] & 0xff)) % 256;
            swap(S, i, j);
            int t = ((S[i] & 0xff) + (S[j] & 0xff)) % 256;
            keys[r] = S[t];
        }
    }

    /**
     * 交换数组arr中下标为i、j的两元素
     * @param arr 数组
     * @param i
     * @param j
     */
    private void swap(byte[] arr, int i, int j) {
        byte temp = 0;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
