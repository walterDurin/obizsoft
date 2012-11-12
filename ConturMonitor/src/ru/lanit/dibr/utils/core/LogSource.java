package ru.lanit.dibr.utils.core;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:15
 */
public interface LogSource extends Source {
    public void startRead() throws Exception;
    public void reloadFull() throws Exception;
    public void close() throws Exception;
}
