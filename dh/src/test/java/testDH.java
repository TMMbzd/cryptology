import com.tmm.cryptology.dh.DH;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Arrays;

public class testDH {

    @Test
    public void testDH() {

        //A的密钥
        byte[] secretKeyA = new byte[64];
        //B的密钥
        byte[] secretKeyB = new byte[64];
        new SecureRandom().nextBytes(secretKeyA);
        new SecureRandom().nextBytes(secretKeyB);

        //生成A和B的公开密钥
        byte[] publicKeyA = DH.generatePublicKey(secretKeyA);
        byte[] publicKeyB = DH.generatePublicKey(secretKeyB);

        //生成A和B的共享密钥
        byte[] sharedKeyA = DH.generateSharedKey(publicKeyB, secretKeyA);
        byte[] sharedKeyB = DH.generateSharedKey(publicKeyA, secretKeyB);

        //判断sharedKeyA和sharedB是否相等
        if(Arrays.equals(sharedKeyA, sharedKeyB)) {
            System.out.println("密钥是相等的");
        }else {
            System.out.println("密钥不相等");
        }
    }
}
