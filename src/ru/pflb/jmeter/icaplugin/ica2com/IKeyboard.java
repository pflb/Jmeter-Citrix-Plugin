package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.Com4jObject;
import com4j.IID;
import com4j.VTID;

/**
 * IKeyboard Interface
 */
@IID("{17BFCA0A-C42E-4AC9-A693-29473FF9BA6C}")
public interface IKeyboard extends Com4jObject {

    /**
     * method SendKeyDown
     */
    @VTID(7)
    void sendKeyDown(
            int keyId);

    /**
     * method SendKeyUp
     */
    @VTID(8)
    void sendKeyUp(
            int keyId);

}
