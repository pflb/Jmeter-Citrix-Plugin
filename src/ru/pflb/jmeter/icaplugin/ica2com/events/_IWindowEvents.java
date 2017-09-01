package ru.pflb.jmeter.icaplugin.ica2com.events;

import com4j.DISPID;
import com4j.IID;

/**
 * _IWindowEvents Interface
 */
@IID("{49813E6D-17FF-41A1-9A7B-95C3D5B44185}")
public abstract class _IWindowEvents {

    /**
     * method OnMove
     */
    @DISPID(1)
    public void onMove(
            int xPos,
            int yPos) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnSize
     */
    @DISPID(2)
    public void onSize(
            int width,
            int height) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnActivate
     */
    @DISPID(3)
    public void onActivate() {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnDeactivate
     */
    @DISPID(4)
    public void onDeactivate() {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnMinimize
     */
    @DISPID(5)
    public void onMinimize() {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnCaptionChange
     */
    @DISPID(6)
    public void onCaptionChange(
            java.lang.String caption) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnStyleChange
     */
    @DISPID(7)
    public void onStyleChange(
            int style,
            int extendedStyle) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnSmallIconChange
     */
    @DISPID(8)
    public void onSmallIconChange(
            java.lang.String smallIconHash) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnLargeIconChange
     */
    @DISPID(9)
    public void onLargeIconChange(
            java.lang.String largeIconHash) {
        throw new UnsupportedOperationException();
    }

    /**
     * method OnDestroy
     */
    @DISPID(10)
    public void onDestroy() {
        throw new UnsupportedOperationException();
    }

}
