import com.tmm.cryptology.aes.AES;
import com.tmm.cryptology.aes.impl.AESImpl;
import com.tmm.cryptology.aes.util.AESUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class testAes {

    @Test
    public void testAES() {

        //使用构造器直接赋值key
        AES aes = new AESImpl("ABCDEFGHIJKLMNOP".getBytes(StandardCharsets.UTF_8));
        //加密
        byte[] encrypt = aes.encrypt("QWERTYUIOPASDFGH".getBytes(StandardCharsets.UTF_8));
        //解密
        byte[] ming = aes.decrypt(encrypt);

        //使用setKey重新设置key
        aes.setKey("QWERTYUIOPASDFGH".getBytes(StandardCharsets.UTF_8));
        //用新密钥加密
        byte[] encrypt1 = aes.encrypt("密码学真难a".getBytes(StandardCharsets.UTF_8));
        //解密
        byte[] ming1 = aes.decrypt(encrypt1);
        //输出明文
        System.err.println(new String(ming, StandardCharsets.UTF_8));
        System.err.println(new String(ming1, StandardCharsets.UTF_8));
    }
}
