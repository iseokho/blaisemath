/*
 * PolynomialTest.java
 * JUnit based test
 *
 * Created on June 21, 2007, 2:09 PM
 */

package scio.algebra.polynomial;

import junit.framework.*;

/**
 *
 * @author ae3263
 */
public class PolynomialTest extends TestCase {
    
    public PolynomialTest(String testName) {
        super(testName);
    }

    public static Test suite(){return new TestSuite(PolynomialTest.class);}

    Polynomial instance=new Polynomial();
    
    protected void setUp() throws Exception {
        instance.appendTerm(1,1);
        instance.appendTerm(1.5f,7);
        instance.appendTerm(6,0);
        instance.appendTerm(-8,0);
        instance.appendTerm(-48,-2);
        instance.appendTerm(3,7);
    }

    /**
     * Test of toString method, of class scio.algebra.polynomial.Polynomial.
     */
    public void testToString() {
        System.out.println("toString");
        System.out.println(instance.toString());
        assertEquals("+4.5x^7+x-2-48x^-2",instance.toString());
    }
}
