package ru.lanit.dibr.utils;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Created with IntelliJ IDEA.
 * User: Vladimir
 * Date: 06.02.14
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public final class CmdLineConfiguration {
    @Option(name = "-fontSize", usage = "Font size")
    public static int fontSize = 12;

    @Argument(usage = "Name of XML xonfig file. Default: settings.xml")
    public static String settingsFileName = "settings.xml";

}
