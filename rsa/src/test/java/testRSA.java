import cn.hutool.crypto.SecureUtil;
import com.tmm.cryptology.rsa.RSA;
import com.tmm.cryptology.rsa.impl.RSAImpl;
import com.tmm.cryptology.rsa.util.KeyType;
import org.junit.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

public class testRSA {

    @Test
    public void testRsa() {
        RSA rsa = new RSAImpl();
        byte[] data = "我爱你中国，哈哈哈哈，我爱你中国，嘻嘻嘻嘻,我爱你中国，哈哈哈哈，我爱你中国，嘻嘻嘻嘻".getBytes(StandardCharsets.UTF_8);
        byte[] encrypt = rsa.encrypt(data,  KeyType.PUBLIC_KEY);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PRIVATE_KEY);
        System.out.println(new String(decrypt, StandardCharsets.UTF_8));

    }

    @Test
    public void testMod() {

        BigInteger a = new BigInteger("11");
        BigInteger a1 = a.modPow(new BigInteger("-1"), new BigInteger("5"));
        BigInteger r = a.multiply(a1).mod(new BigInteger("10"));
    }

    @Test
    public void test() {

        byte i = (byte)0b10000000;
    }
}
