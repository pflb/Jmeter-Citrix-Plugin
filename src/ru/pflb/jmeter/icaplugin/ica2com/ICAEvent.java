package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * List of ICA events
 */
public enum ICAEvent implements ComEnum {

    EventNone(0),
    EventConnect(1),
    EventConnectFail(2),
    EventLogin(3),
    EventLoginFail(4),
    EventDisconnect(5),
    EventRunPubishedApp(6),
    EventRunPubishedAppFail(7),
    EventIcaFilePresent(8),
    EventLoadIcaFileFailed(9),
    EventInitializing(10),
    EventConnecting(11),
    EventInitialProp(12),
    EventDisconnectFailed(13),
    EventLogoffFailed(14),
    EventChannelDataReceived(15),
    EventWindowSized(16),
    EventWindowMoved(17),
    EventWindowCreated(18),
    EventWindowDestroyed(19),
    EventWindowDocked(20),
    EventWindowUndocked(21),
    EventWindowMinimized(22),
    EventWindowMaximized(23),
    EventWindowRestored(24),
    EventWindowFullscreened(25),
    EventWindowHidden(26),
    EventWindowDisplayed(27),
    EventCGPWarn(37),
    EventCGPUnwarn(38),
    EventCGPDisconnect(39),
    EventCGPReconnect(40),;

    private final int value;

    ICAEvent(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
