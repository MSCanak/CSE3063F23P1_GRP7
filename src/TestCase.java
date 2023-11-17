import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestCase {
    private LoginInterface loginInterface;

    @Before
    public void setUp() {
        loginInterface = new LoginInterface();
    }

    @Test
    public void testLogin() {
        assertEquals(true, loginInterface.login());
    }
}
