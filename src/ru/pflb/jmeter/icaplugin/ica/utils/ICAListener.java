package ru.pflb.jmeter.icaplugin.ica.utils;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public interface ICAListener {
    void onConnecting();

    void onLogon();

    void onDisconnect();

    void onError(String errorMessage);
}
