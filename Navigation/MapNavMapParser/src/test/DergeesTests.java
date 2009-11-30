package test;

import junit.framework.TestCase;
import org.mapnav.types.Degrees;
import org.mapnav.Parser;
import org.mapnav.Utils;
import org.mapnav.parts.Map;
import org.mapnav.exceptions.OutOfRange;

import java.io.*;

/**
 * User: Vova
 * Time: 0:21:49
 *
 * @author ognivo777@mail.ru
 */
public class DergeesTests extends TestCase {

    public void test01() {
        Degrees dg = new Degrees();
        dg.setFloatDeg(18.258);
        System.out.println(dg.getDeg());
        System.out.println(dg.getMin());
        System.out.println(dg.getSec());
        System.out.println(dg.getFloatDeg());
    }

    public void testParserInternal() throws IOException, OutOfRange {
        Map map = Parser.readMap(new FileInputStream("wcdemo2.mnm"), Map.INTERNAL_TYPE);
        System.out.println(map);
    }

    public void testParserExternal() throws IOException, OutOfRange {
        Map map = Parser.readMap(new FileInputStream("wcdemo2.mno"), Map.EXTERNAL_TYPE);
        System.out.println(map);
    }

    public void testParseWriteInternal() throws OutOfRange, IOException {
        Map map = Parser.readMap(new FileInputStream("wcdemo2.mnm"), Map.INTERNAL_TYPE);
        FileOutputStream out1 = new FileOutputStream("out.mnm");
        map.writeToStream(out1);
        out1.close();
        FileOutputStream out2 = new FileOutputStream("out.mno");
        map.setType(Map.EXTERNAL_TYPE);
        map.writeToStream(out2);
        out2.close();

    }

    public void testShift() throws IOException {
        long l = 0;
        l+=((long)(0))<<24;
        System.out.println(l);
        l=6011;
        System.out.println(Long.toHexString(l));
        byte[] bytes = Utils.longToBytes(l);
        ByteArrayInputStream baInputStream = new ByteArrayInputStream(bytes);
        System.out.println(Long.toHexString(Utils.readLong(baInputStream)));
    }


    public void testRead() throws IOException {
        InputStream is = new FileInputStream("wcdemo2.mnm");
        for(int i=0; i<9; i++) {
            System.out.println("["+i+"]="+is.read());
        }
    }
}
