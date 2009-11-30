package tests;

import junit.framework.TestCase;
import org.gmapper.types.IntPoint;

/**
 * User: Vova
 * Time: 1:43:28
 *
 * @author ognivo777@mail.ru
 */
public class MathTest extends TestCase {

    public void testObhod() {
        for(int i=1; i<4; i++) {
            IntPoint p0 = new IntPoint(-i,-i);
            IntPoint d = new IntPoint(0, 1);
            for(IntPoint np = p0.add(d); !np.equals(p0);np = np.add(d)) {
                System.out.println("np = " + np);
                if(np.equals(new IntPoint(-i,i))) {
                    d = new IntPoint(1,0);
                } else if (np.equals(new IntPoint(i,i))) {
                    d = new IntPoint(0, -1);
                } else if (np.equals(new IntPoint(i,-i))) {
                    d = new IntPoint(-1, 0);
                }
            }
        }
    }
}
