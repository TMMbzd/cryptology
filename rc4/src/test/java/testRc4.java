import com.tmm.cryptology.rc4.RC4;
import com.tmm.cryptology.rc4.impl.RC4Impl;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class testRc4 {

    @Test
    public void testRc4() {
        RC4 rc4 = new RC4Impl();
        byte[] data = ("我爱你中国，哈哈哈哈，嘿嘿嘿嘿，嘻嘻嘻嘻，我爱你中国，哈哈哈哈，嘿嘿嘿嘿").getBytes(StandardCharsets.UTF_8);
        byte[] encrypt = rc4.encrypt(data);
        byte[] decrypt = rc4.decrypt(encrypt);

        rc4.setInitKey("ASDFGH".getBytes(StandardCharsets.UTF_8));

        byte[] encrypt1 = rc4.encrypt("一拳一个捏捏怪".getBytes(StandardCharsets.UTF_8));
        byte[] decrypt1 = rc4.decrypt(encrypt1);

        System.out.println(new String(decrypt, StandardCharsets.UTF_8));
        System.out.println(new String(decrypt1, StandardCharsets.UTF_8));
    }

    @Test
    public void test() {
        byte a = (byte)0xff;
        int i = 0b11111111;
    }
}
