package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.Com4jObject;
import com4j.IID;
import com4j.VTID;

/**
 * IScreenShot Interface
 */
@IID("{8F2D9E63-D224-47E4-8111-32DBB016A4C6}")
public interface IScreenShot extends Com4jObject {

    /**
     * property PositionX
     */
    @VTID(7)
    int positionX();

    /**
     * property PositionX
     */
    @VTID(8)
    void positionX(
            int pVal);

    /**
     * property PositionY
     */
    @VTID(9)
    int positionY();

    /**
     * property PositionY
     */
    @VTID(10)
    void positionY(
            int pVal);

    /**
     * property Width
     */
    @VTID(11)
    int width();

    /**
     * property Width
     */
    @VTID(12)
    void width(
            int pVal);

    /**
     * property Height
     */
    @VTID(13)
    int height();

    /**
     * property Height
     */
    @VTID(14)
    void height(
            int pVal);

    /**
     * property Filename
     */
    @VTID(15)
    java.lang.String filename();

    /**
     * property Filename
     */
    @VTID(16)
    void filename(
            java.lang.String pVal);

    /**
     * property BitmapHash
     */
    @VTID(17)
    java.lang.String bitmapHash();

    /**
     * method Save
     */
    @VTID(18)
    void save();

}
