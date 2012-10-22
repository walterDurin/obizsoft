package ru.lanit.dibr.utils.gui.configuration;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 23.10.12
 * Time: 1:44
 * To change this template use File | Settings | File Templates.
 */
public class LogFile {
    private String name;
    private String path;
    private String blockPattern;

    public LogFile(String name, String path, String blockPattern) {
        this.name = name;
        this.path = path;
        this.blockPattern = blockPattern;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getBlockPattern() {
        return blockPattern;
    }
}
