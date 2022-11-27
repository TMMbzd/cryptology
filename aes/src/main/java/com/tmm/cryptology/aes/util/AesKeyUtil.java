package com.tmm.cryptology.aes.util;

public class AesKeyUtil {
    /**
     * 生成由初始密钥扩展的11轮key
     * roundKey[0]为初始密钥
     * roundKey[1] - roundKey[10]为由初始密钥扩展的10轮密钥
     * @param initKey 初始key
     * @return roundKey[11]
     */
    public static byte[][][] generateRoundKey(byte[] initKey) {
        byte[][][] roundKey = new byte[11][4][4];
        //初始密钥转字节矩阵
        byte[][] bytes = AESUtil.toMatrix(initKey);
        roundKey[0]= bytes;
        //存储上一次生成的列
        byte[] preCol = AESUtil.getMatrixCol(roundKey[0], 3);
        for(int i = 1; i <= 10; i++) {
            //密钥矩阵转置
            byte[][] preKey = AESUtil.inverseMatrix(roundKey[i - 1]);
            for(int j = 0, wi = i *4; j < 4; j++, wi++) {
                if (wi % 4 != 0) {
                    //前4列与前一列异或
                    preCol = AESUtil.xorBytes(preKey[j], preCol);
                }else {
                    //前一列作T函数变换
                    //1.字循环
                    AESUtil.rotateL(preCol, 1);
                    //字节代换
                    for (int k = 0; k < 4; k++) {
                        preCol[k] = AESUtil.subBytes(preCol[k], AESUtil.S_BOX);
                    }
                    //轮常量异或
                    preCol = AESUtil.xorBytes(preCol, AESUtil.R_Con[i - 1]);
                    preCol = AESUtil.xorBytes(preKey[j], preCol);
                }
                //异或的结果整列赋值到当前列
                AESUtil.entireColAssign(roundKey[i], j, preCol);
            }
        }
        return roundKey;
    }

}
