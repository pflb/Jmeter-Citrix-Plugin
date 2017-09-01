package ru.pflb.jmeter.icaplugin.ica2com.events;

import com4j.DISPID;
import com4j.IID;

/**
 * _IMouseEvents Interface
 */
@IID("{24013613-FF51-4B50-9832-37FA561594ED}")
public abstract class _IMouseEvents {

    /**
     * method OnMove
     */
    @DISPID(1)
    public void onMove(
            int buttonState,
            int modifierState,
            int xPos,
            int yPos) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnMouseDown
     */
    @DISPID(3)
    public void onMouseDown(
            int buttonState,
            int modifierState,
            int xPos,
            int yPos) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnMouseUp
     */
    @DISPID(2)
    public void onMouseUp(
            int buttonState,
            int modifierState,
            int xPos,
            int yPos) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnDoubleClick
     */
    @DISPID(4)
    public void onDoubleClick() {
        throw new UnsupportedOperationException();
    }

}
