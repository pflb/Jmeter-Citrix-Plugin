package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.ComEnum;

/**
 * Window extended style masks
 */
public enum WindowExStyle implements ComEnum {

    WindowsExStyleDLGMODALFRAME(1),
    WindowsExStyleNOPARENTNOTIFY(4),
    WindowsExStyleTOPMOST(8),
    WindowsExStyleACCEPTFILES(16),
    WindowsExStyleTRANSPARENT(32),
    WindowsExStyleMDICHILD(64),
    WindowsExStyleTOOLWINDOW(128),
    WindowsExStyleWINDOWEDGE(256),
    WindowsExStyleCLIENTEDGE(512),
    WindowsExStyleCONTEXTHELP(1024),
    WindowsExStyleRIGHT(4096),
    WindowsExStyleLEFT(0),
    WindowsExStyleRTLREADING(8192),
    WindowsExStyleLTRREADING(0),
    WindowsExStyleLEFTSCROLLBAR(16384),
    WindowsExStyleRIGHTSCROLLBAR(0),
    WindowsExStyleCONTROLPARENT(65536),
    WindowsExStyleSTATICEDGE(131072),
    WindowsExStyleAPPWINDOW(262144),
    WindowsExStyleOVERLAPPEDWINDOW(768),
    WindowsExStylePALETTEWINDOW(392),;

    private final int value;

    WindowExStyle(int value) {
        this.value = value;
    }

    public int comEnumValue() {
        return value;
    }
}
