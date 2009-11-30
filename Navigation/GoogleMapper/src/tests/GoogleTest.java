package tests;

import junit.framework.TestCase;
import org.gmapper.google.GoogleUtils;
import org.gmapper.google.GoogleTile;
import org.gmapper.TileFactory;
import org.gmapper.BaseTile;
import org.gmapper.types.DoublePoint;
import org.gmapper.types.IntPoint;
import org.mapnav.exceptions.OutOfRange;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Vova
 * Time: 21:58:53
 *
 * @author ognivo777@mail.ru
 */
public class GoogleTest extends TestCase {

    public void testTileNames() throws OutOfRange {
        assertEquals(GoogleUtils.tileNameFromPos(1, 0, 0), "t"  );
        assertEquals(GoogleUtils.tileNameFromPos(2, 0, 0), "tq" );
        assertEquals(GoogleUtils.tileNameFromPos(2, 0, 1), "tt" );
        assertEquals(GoogleUtils.tileNameFromPos(2, 1, 0), "tr" );
        assertEquals(GoogleUtils.tileNameFromPos(2, 1, 1), "ts" );
        assertEquals(GoogleUtils.tileNameFromPos(3, 2, 1), "trt");
        assertEquals(GoogleUtils.tileNameFromPos(3, 2, 3), "tst");
        assertEquals(GoogleUtils.tileNameFromPos(3, 0, 0), "tqq");        
        assertEquals(GoogleUtils.tileNameFromPos(3, 0, 2), "ttq");
    }

    public void testLoadImage() throws OutOfRange, IOException {
        GoogleTile tile = new GoogleTile(1,0,0, GoogleTile.MAP_TYPE_MAP);
        System.out.println(tile.load());
        List<String> strings = FileUtils.readLines(new File("c:\\1.txt"));
        LineIterator lineIterator = FileUtils.lineIterator(new File("c:\\1.txt"));
        while(lineIterator.hasNext()) {
            String s = (String) lineIterator.next();
        }
        BufferedImage im = tile.getImage();
        ImageIO.write(im, "jpeg", new File("testTile.jpg"));
    }

    /*

function latLngToPixel(lat, lng, zoom) {
    var centerPoint = Math.pow(2, zoom + 7),
    totalPixels = centerPoint * 2,
    pixelsPerLngDegree = totalPixels / 360,
    pixelsPerLngRadian = totalPixels / (2 * Math.PI),
    siny = Math.min(Math.max(Math.sin(lat * (Math.PI / 180)), -0.9999), 0.9999);
    return{
    x:Math.round(centerPoint + lng * pixelsPerLngDegree)
    ,
    y:Math.round(centerPoint - 0.5 * Math.log((1 + siny) / (1 - siny)) * pixelsPerLngRadian)
    }
}
;
    */

    //    public static BaseTile getGoogleTileByLL(DoublePoint ll, int zoom) throws OutOfRange {
//        double bm0 = Math.pow(2, zoom-1)*256/2;
//        double x = ll.x;
//        double y = ll.y;
//        x = bm0 * ( 1 + x/180);
//        y = bm0 * (1 - 0.5*Math.log((1+Math.sin(y))/(1-Math.sin(y)))/ Math.PI);
//        System.out.println("getGoogleTileByLL" + new DoublePoint(x, y));
//        return TileFactory.getGoogleTile(zoom,(int) x/256, (int) y/256, GoogleTile.MAP_TYPE_MAP);
//    }

    public void testGeoPixels() {
        int level = 14;
        DoublePoint geoPos = new DoublePoint(37.658997774124146, 55.8921177933994);
        System.out.println("geoPos = " + geoPos);
        IntPoint mapPos = GoogleUtils.mapPosFromGeo(geoPos, level);
        System.out.println("mapPos = " + mapPos);
        DoublePoint newGeoPos = GoogleUtils.geoFromMapPos(mapPos, level);
        System.out.println("newGeoPos = " + newGeoPos);
    }

}
