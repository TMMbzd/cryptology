package com.tmm.cryptology.aes.impl;

import com.tmm.cryptology.aes.AES;
import com.tmm.cryptology.aes.util.AesKeyUtil;
import com.tmm.cryptology.aes.util.AESUtil;

import static com.tmm.cryptology.aes.util.AESUtil.*;

public class AESImpl implements AES {

    private byte[] key;
    /**
     * 轮密钥，roundKey[0]为初始密钥
     * roundKey[1]-roundKey[10]为初始密钥扩展得到的10轮密钥
     */
    private byte[][][] roundKey;

    public AESImpl() {

    }

    public AESImpl(byte[] key) {
        this.key = key;
        this.roundKey = AesKeyUtil.generateRoundKey(key);
    }

    @Override
    public void setKey(byte[] key) {
        this.key = key;
        this.roundKey = AesKeyUtil.generateRoundKey(key);
    }

    @Override
    public byte[] getKey() {
        return key;
    }

    /**
     * 加密
     * @param data：明文
     * @return
     */
    @Override
    public byte[] encrypt(byte[] data) {

        byte[][] dataM = AESUtil.toMatrix(data);
        xorMatrix(dataM, roundKey[0]);

        //九轮迭代变换
        for(int i = 1; i <= 9; i++) {
            subBytes(dataM);
            //行移位
            shiftRaws(dataM);
            //列混合
            mixColumn(dataM);
            //轮密钥加
            xorMatrix(dataM, roundKey[i]);
        }
        //最后一轮变换
        subBytes(dataM);
        //行移位
        shiftRaws(dataM);
        //轮密钥加
        xorMatrix(dataM, roundKey[10]);
        return AESUtil.toBytes(dataM);
    }


    /**
     * 解密
     * @param data: 秘文
     */
    @Override
    public byte[] decrypt(byte[] data) {

        byte[][] dataM = toMatrix(data);
        xorMatrix(dataM, roundKey[10]);
        In_shiftRaws(dataM);
        In_subBytes(dataM);
        for (int i = 9; i >= 1; i--) {
            xorMatrix(dataM, roundKey[i]);
            In_mixColumns(dataM);
            In_shiftRaws(dataM);
            In_subBytes(dataM);
        }
        xorMatrix(dataM, roundKey[0]);
        return AESUtil.toBytes(dataM);
    }

    /**
     * 矩阵按列异或
     * 异或的结果由m1接收
     * @param m1
     * @param m2
     */
    private void xorMatrix(byte[][] m1, byte[][] m2) {
        for (int i = 0; i < 4; i++) {
            byte[] m1Col = AESUtil.getMatrixCol(m1, i);
            byte[] m2Col = AESUtil.getMatrixCol(m2, i);
            AESUtil.entireColAssign(m1, i, AESUtil.xorBytes(m1Col, m2Col));
        }
    }

    /**
     * 字节代换
     * 经S盒在源字节说做代换
     */
    private void subBytes(byte[][] data) {
        for(int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                data[j][k] = AESUtil.subBytes(data[j][k], S_BOX);
            }
        }
    }

    /**
     * 逆字节代换
     * 经逆S盒在源字节上做代换
     * @param data
     */
    private void In_subBytes(byte[][] data) {
        for(int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                data[j][k] = AESUtil.subBytes(data[j][k], In_SBox);
            }
        }
    }

    /**
     * 列混合
     * 将矩阵左乘一个矩阵，结果由原矩阵接收
     * @param data
     */
    private void mixColumn(byte[][] data) {

        byte[] tmp = new byte[4];
        for (int i = 0; i < 4; i++) {
            byte[] col = AESUtil.getMatrixCol(data, i);
            tmp[0] = (byte) (x2time(col[0]) ^ x3time(col[1]) ^ col[2] ^ col[3]);	//2 3 1 1
            tmp[1] = (byte) (col[0] ^ x2time(col[1]) ^ x3time(col[2]) ^ col[3]);	//1 2 3 1
            tmp[2] = (byte) (col[0] ^ col[1] ^ x2time(col[2]) ^ x3time(col[3]));	//1 1 2 3
            tmp[3] = (byte) (x3time(col[0]) ^ col[1] ^ col[2] ^ x2time(col[3]));	//3 1 1 2
            AESUtil.entireColAssign(data, i, tmp);
        }
    }

    /**
     * 逆列混合
     * 将矩阵左乘一个矩阵，结果由原矩阵接收
     * @param data
     */
    private void In_mixColumns(byte[][] data)
    {
        byte[] tmp = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            byte[] col = getMatrixCol(data, i);
            tmp[0] = (byte) (xEtime(col[0]) ^ xBtime(col[1]) ^ xDtime(col[2]) ^ x9time(col[3]));
            tmp[1] = (byte) (x9time(col[0]) ^ xEtime(col[1]) ^ xBtime(col[2]) ^ xDtime(col[3]));
            tmp[2] = (byte) (xDtime(col[0]) ^ x9time(col[1]) ^ xEtime(col[2]) ^ xBtime(col[3]));
            tmp[3] = (byte) (xBtime(col[0]) ^ xDtime(col[1]) ^ x9time(col[2]) ^ xEtime(col[3]));
            AESUtil.entireColAssign(data, i, tmp);
        }
    }

    /**
     * 行移位
     */
    private void shiftRaws(byte[][] data) {
        AESUtil.rotateL(data[1], 1);
        AESUtil.rotateL(data[2], 2);
        AESUtil.rotateL(data[3], 3);
    }

    /**
     * 逆行移位
     */
    private void In_shiftRaws(byte[][] data) {
        AESUtil.rotateR(data[1], 1);
        AESUtil.rotateR(data[2], 2);
        AESUtil.rotateR(data[3], 3);
    }
}
