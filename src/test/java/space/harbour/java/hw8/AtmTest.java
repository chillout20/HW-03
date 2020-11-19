package space.harbour.java.hw8;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtmTest extends TestCase
{

    // Initial balance of 945
    @Test
    public void testGiveMeMoney() {
        Atm.giveMeMoney(45);
        assertEquals(900, Atm.getBalance());
    }
}