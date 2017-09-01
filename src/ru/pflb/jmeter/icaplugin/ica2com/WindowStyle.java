package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * Window style masks
 */
public enum WindowStyle implements ComEnum {

    WindowStyleOVERLAPPED(0),
    WindowStylePOPUP(-2147483648),
    WindowStyleCHILD(1073741824),
    WindowStyleMINIMIZE(536870912),
    WindowStyleVISIBLE(268435456),
    WindowStyleDISABLED(134217728),
    WindowStyleCLIPSIBLINGS(67108864),
    WindowStyleCLIPCHILDREN(33554432),
    WindowStyleMAXIMIZE(16777216),
    WindowStyleCAPTION(12582912),
    WindowStyleBORDER(8388608),
    WindowStyleDLGFRAME(4194304),
    WindowStyleVSCROLL(2097152),
    WindowStyleHSCROLL(1048576),
    WindowStyleSYSMENU(524288),
    WindowStyleTHICKFRAME(262144),
    WindowStyleGROUP(131072),
    WindowStyleTABSTOP(65536),
    WindowStyleMINIMIZEBOX(131072),
    WindowStyleMAXIMIZEBOX(65536),;

    private final int value;

    WindowStyle(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
