import org.mapnav.parts.Map;
import org.mapnav.Parser;
import org.mapnav.exceptions.OutOfRange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Main {
    private static String MNM2MNO = "-mnm2mno";
    private static String MNO2MNM = "-mno2mnm";
    public static void main(String[] args) {
        if(args!=null && args.length!=0) {
            if(args.length==3) {
                if(args[0].equalsIgnoreCase(MNM2MNO)) {
                    File mnmImputMap = new File(args[1]);
                    File mnoOutputMap = new File(args[2]);
                    if(!mnmImputMap.exists()) {
                        System.out.println("Input MNM file '" + mnmImputMap.getAbsolutePath() + "' not found!");
                        System.exit(0);
                    }
                    if(!mnmImputMap.canRead()){
                        System.out.println("Can not read input MNM file '" + mnmImputMap.getAbsolutePath() + "'");
                        System.exit(0);
                    }
                    if(mnoOutputMap.exists()) {
                        System.out.println("Output MNO file '" + mnoOutputMap.getAbsolutePath() + "' already exists!");
                        System.exit(0);
                    }
                    try {
                        mnoOutputMap.createNewFile();
                        if(!mnoOutputMap.canWrite()){
                            System.out.println("Can not write output MNO file '" + mnoOutputMap.getAbsolutePath() + "'!");
                        }
                        Map map = Parser.readMap(new FileInputStream(mnmImputMap), Map.INTERNAL_TYPE);
                        FileOutputStream stream = new FileOutputStream(mnoOutputMap);
                        map.writeToStream(stream);
                        stream.close();
                        System.out.println("Map conver done.");
                        return;
                    } catch (OutOfRange outOfRange) {
                        outOfRange.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(args[0].equalsIgnoreCase(MNO2MNM)) {
                    return;
                }
            }
        }
        showUsages();
    }

    private static void showUsages(){
        System.out.println("Usage:");
        System.out.println("convert MNM to MNO:");
        System.out.println("-mnm2mno <path to MNM map> <path to new MNO map>");
        System.out.println("convert MNO to MNM:");
        System.out.println("-mnm2mno <path to MNO map> <path to new MNM map>");
    }
}
