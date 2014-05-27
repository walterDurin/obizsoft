package ru.lanit.dibr.utils.core;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:15
 */
public interface LogSource extends Source {
    public final String SKIP_LINE = "SKIP_LINE";
    public void startRead() throws Exception;
    public void reloadFull() throws Exception;
    public void close() throws Exception;
    public String getName();
    public boolean isWriteLineNumbers();

    public void setWriteLineNumbers(boolean writeLineNumbers);

}
