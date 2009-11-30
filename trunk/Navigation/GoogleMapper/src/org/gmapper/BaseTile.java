package org.gmapper;

import org.apache.commons.logging.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * User: Vova
 * Time: 20:58:30
 *
 * @author ognivo777@mail.ru
 */
public abstract class BaseTile {
    /**спутник*/
    public static final int MAP_TYPE_SAT = 1;
    /**карта*/
    public static final int MAP_TYPE_MAP = 2;
    /**Гибрид*/
    public static final int MAP_TYPE_HYB = 3;
    /**Я.Пробки*/
    public static final int YA_MAP_TYPE_TAFFIC = 4; 

    /**Start with 1 end with 20 or 17*/
    protected int level;
    /**Start with 0*/
    protected int xNum;
    /**Start with 0*/
    protected int yNum;

    protected int type;
    protected boolean loaded;

    protected byte[] imageData;

    public int getLevel() {
        return level;
    }

    public int getXNum() {
        return xNum;
    }

    public int getYNum() {
        return yNum;
    }

    public int getType() {
        return type;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public byte[] getImageData() {
        return imageData;
    }

    protected abstract Log getLog();

    public BufferedImage getImage() {
        if(!loaded || imageData==null) {
            BufferedImage na = new BufferedImage(256, 256, 1);
            na.getGraphics().setPaintMode();
            na.getGraphics().setColor(Color.RED);
            na.getGraphics().drawLine(0,0,0,1);
            na.getGraphics().drawLine(0,1,1,1);
            na.getGraphics().drawLine(1,1,0,1);
            na.getGraphics().drawLine(0,1,0,0);

            na.getGraphics().drawLine(0,0,1,1);
            na.getGraphics().drawLine(0,1,1,0);
            getLog().info("get [X]");
            return na;
        }
        try {
            return ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            //ToDo
            return null;
        }
    }

    public abstract String getLoadUrl();

    public abstract boolean load();
}
