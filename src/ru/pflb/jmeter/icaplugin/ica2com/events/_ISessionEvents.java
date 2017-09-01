package ru.pflb.jmeter.icaplugin.ica2com.events;

import com4j.DISPID;
import com4j.IID;
import ru.pflb.jmeter.icaplugin.ica2com.IWindow;

/**
 * _ISessionEvents Interface
 */
@IID("{24FD31DB-3560-4C78-8950-30F03352D830}")
public abstract class _ISessionEvents {

    /**
     * method OnWindowCreate
     */
    @DISPID(1)
    public void onWindowCreate(
            IWindow window) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnWindowDestroy
     */
    @DISPID(2)
    public void onWindowDestroy(
            IWindow window) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnPingAck
     */
    @DISPID(4)
    public void onPingAck(
            java.lang.String pingInfo,
            int roundTripTime) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnWindowForeground
     */
    @DISPID(5)
    public void onWindowForeground(
            int windowID) {
        throw new UnsupportedOperationException();
    }

}
