import com.tmm.cryptology.rsa.RSA;
import com.tmm.cryptology.rsa.impl.RSAImpl;
import com.tmm.cryptology.rsa.key.PrivateKey;
import com.tmm.cryptology.rsa.key.PublicKey;
import com.tmm.cryptology.signature.Signer;
import com.tmm.cryptology.signature.impl.SignerImpl;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class testSigner {

    @Test
    public void testSigner() {
        Signer signer = new SignerImpl();

        byte[] data = "我爱你是真的".getBytes(StandardCharsets.UTF_8);
        //生成rsa实例，随机生成公钥和私钥
        RSA rsa1 = new RSAImpl();
        //用户1的私钥
        PrivateKey privateKey1 = rsa1.getPrivateKey();
        //用户1的公钥
        PublicKey publicKey1 = rsa1.getPublicKey();
        //签名
        byte[] sign1 = signer.sign(data, privateKey1);
        //验签
        boolean verify = signer.verify(data, sign1, publicKey1);
        System.out.println(verify ? "验证通过" : "验证不通过");

        //第二个用户试图伪造信息和签名
        RSAImpl rsa2 = new RSAImpl();
        //用户2的私钥
        PrivateKey privateKey2 = rsa2.getPrivateKey();
        byte[] data2 = "我们分手吧".getBytes(StandardCharsets.UTF_8);
        byte[] sign2 = signer.sign(data2, privateKey2);

        //用用户1的公钥验证签名
        boolean verify2 = signer.verify(data, sign2, publicKey1);
        System.out.println(verify2 ? "验证通过" : "验证不通过");
    }
}
