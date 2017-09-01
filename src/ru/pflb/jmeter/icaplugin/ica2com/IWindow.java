package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.Com4jObject;
import com4j.IID;
import com4j.VTID;

/**
 * IWindow Interface
 */
@IID("{4D5D2139-29E2-4CDC-8020-429B35999BE6}")
public interface IWindow extends Com4jObject {

    /**
     * property PositionX
     */
    @VTID(7)
    int positionX();

    /**
     * property PositionY
     */
    @VTID(8)
    int positionY();

    /**
     * property Width
     */
    @VTID(9)
    int width();

    /**
     * property Height
     */
    @VTID(10)
    int height();

    /**
     * property Style
     */
    @VTID(11)
    int style();

    /**
     * property ExtendedStyle
     */
    @VTID(12)
    int extendedStyle();

    /**
     * property Caption
     */
    @VTID(13)
    java.lang.String caption();

    /**
     * property SmallIconHash
     */
    @VTID(14)
    java.lang.String smallIconHash();

    /**
     * property LargeIconHash
     */
    @VTID(15)
    java.lang.String largeIconHash();

    /**
     * property Disposed
     */
    @VTID(16)
    boolean disposed();

    /**
     * property WindowFlags
     */
    @VTID(17)
    int windowFlags();

    /**
     * property WindowID
     */
    @VTID(18)
    int windowID();

    /**
     * property ParentID
     */
    @VTID(19)
    int parentID();

    /**
     * method BringToTop
     */
    @VTID(20)
    void bringToTop();

    /**
     * method Move
     */
    @VTID(21)
    void move(
            int xPos,
            int yPos);

    /**
     * method Resize
     */
    @VTID(22)
    void resize(
            int width,
            int height);

    /**
     * method Restore
     */
    @VTID(23)
    void restore();

}
