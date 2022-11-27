import com.tmm.cryptology.bag.Bag;
import com.tmm.cryptology.bag.BagCipher;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class testBag {

    @Test
    public void testBag() {

        Bag bag = new Bag();
        BagCipher cipher = bag.encrypt("ABCDEFGHIJKLMNQWERT".getBytes(StandardCharsets.UTF_8));
    }
}
