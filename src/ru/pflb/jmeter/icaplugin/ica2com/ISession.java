package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.Com4jObject;
import com4j.IID;
import com4j.ReturnValue;
import com4j.VTID;

/**
 * ISession Interface
 */
@IID("{4A502C16-CFAE-4BB0-B1F9-93ACADDA57BB}")
public interface ISession extends Com4jObject {

    /**
     * property TopLevelWindows
     */
    @VTID(7)
    IWindows topLevelWindows();

    @VTID(7)
    @ReturnValue(defaultPropertyThrough = {IWindows.class})
    IWindow topLevelWindows(
            int n);

    /**
     * property Mouse
     */
    @VTID(8)
    IMouse mouse();

    /**
     * property Keyboard
     */
    @VTID(9)
    IKeyboard keyboard();

    /**
     * property ForegroundWindow
     */
    @VTID(10)
    IWindow foregroundWindow();

    /**
     * property ReplayMode
     */
    @VTID(11)
    boolean replayMode();

    /**
     * property ReplayMode
     */
    @VTID(12)
    void replayMode(
            boolean pVal);

    /**
     * method CreateFullScreenShot
     */
    @VTID(13)
    IScreenShot createFullScreenShot();

    /**
     * method CreateScreenShot
     */
    @VTID(14)
    IScreenShot createScreenShot(
            int x,
            int y,
            int width,
            int height);

    /**
     * method SendPingRequest
     */
    @VTID(15)
    void sendPingRequest(
            java.lang.String pingInfo);

}
