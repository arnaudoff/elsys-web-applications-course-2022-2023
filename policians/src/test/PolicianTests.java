import com.vis.policians.LeftPolician;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicianTests {

    @Test
    public void testLeftPolicianShouldSayTheyIncreasePensions() {
        // AAA
        // Arrange Act Assert
        LeftPolician leftPolician = new LeftPolician();

        String res = leftPolician.talk();

        assertEquals("will increase pensions", res);
    }
}
