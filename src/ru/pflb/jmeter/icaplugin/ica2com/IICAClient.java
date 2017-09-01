package ru.pflb.jmeter.icaplugin.ica2com;

import com4j.Com4jObject;
import com4j.IID;
import com4j.VTID;

/**
 * IICAClient Interface
 */
@IID("{238F6F81-B8B4-11CF-8771-00A024541EE3}")
public interface IICAClient extends Com4jObject {

    @VTID(7)
    void tabStop(
            boolean pbool);

    @VTID(8)
    boolean tabStop();

    /**
     * method Show AboutBox
     */
    @VTID(9)
    void aboutBox();

    /**
     * method Clear all properties
     */
    @VTID(10)
    void clearProps();

    /**
     * method Get property count
     */
    @VTID(11)
    int getPropCount();

    /**
     * method Delete propoperty
     */
    @VTID(12)
    void deleteProp(
            java.lang.String name);

    /**
     * method Delete propoperty by index
     */
    @VTID(13)
    void deletePropByIndex(
            int index);

    /**
     * method Get propoperty name by index
     */
    @VTID(14)
    java.lang.String getPropNameByIndex(
            int index);

    /**
     * method Reset all propoperties
     */
    @VTID(15)
    void resetProps();

    /**
     * method Set propoperty
     */
    @VTID(16)
    void setProp(
            java.lang.String name,
            java.lang.String value);

    /**
     * method Get propoperty value
     */
    @VTID(17)
    java.lang.String getPropValue(
            java.lang.String name);

    /**
     * method Get propoperty value by index
     */
    @VTID(18)
    java.lang.String getPropValueByIndex(
            int index);

    /**
     * method Connect to server
     */
    @VTID(19)
    void connect();

    /**
     * method Disconnect from server
     */
    @VTID(20)
    void disconnect();

    /**
     * method Logoff from server
     */
    @VTID(21)
    void logoff();

    /**
     * method Load ICA file
     */
    @VTID(22)
    void loadIcaFile(
            java.lang.String file);

    /**
     * method Run published application
     */
    @VTID(23)
    void runPublishedApplication(
            java.lang.String appName,
            java.lang.String arguments);

    /**
     * method Set session end action
     */
    @VTID(24)
    void setSessionEndAction(
            ICASessionEndAction action);

    /**
     * method True if currently connected to server
     */
    @VTID(25)
    boolean isConnected();

    /**
     * method Get ICA Client interface version
     */
    @VTID(26)
    java.lang.String getInterfaceVersion();

    /**
     * method Get ICA Client identification
     */
    @VTID(27)
    java.lang.String getClientIdentification();

    /**
     * method Get session string
     */
    @VTID(28)
    java.lang.String getSessionString(
            ICASessionString index);

    /**
     * method Get session counter
     */
    @VTID(29)
    int getSessionCounter(
            ICASessionCounter index);

    /**
     * method Get last notification reason
     */
    @VTID(30)
    ICAEvent getNotificationReason();

    /**
     * method Startup
     */
    @VTID(31)
    void startup();

    /**
     * method Get Last Error
     */
    @VTID(32)
    int getLastError();

    /**
     * method Get Last Client Error
     */
    @VTID(33)
    int getLastClientError();

    /**
     * method Enable scaling
     */
    @VTID(34)
    int scaleEnable();

    /**
     * method Disable scaling
     */
    @VTID(35)
    int scaleDisable();

    /**
     * method Scale up to a larger size
     */
    @VTID(36)
    int scaleUp();

    /**
     * method Scale down to a smaller size
     */
    @VTID(37)
    int scaleDown();

    /**
     * method Scale to a size
     */
    @VTID(38)
    int scaleSize(
            int width,
            int height);

    /**
     * method Scale to a percent
     */
    @VTID(39)
    int scalePercent(
            int percent);

    /**
     * method Scale to fit size of ICA Client Object window
     */
    @VTID(40)
    int scaleToFit();

    /**
     * method Popup the scaling dialog box
     */
    @VTID(41)
    int scaleDialog();

    /**
     * method CreateChannels
     */
    @VTID(42)
    int createChannels(
            java.lang.String channelNames);

    /**
     * method SendChannelData
     */
    @VTID(43)
    int sendChannelData(
            java.lang.String channelName,
            java.lang.String data,
            int dataSize,
            ICAVCDataType dataType);

    /**
     * method GetChannelCount
     */
    @VTID(44)
    int getChannelCount();

    /**
     * method GetChannelName
     */
    @VTID(45)
    java.lang.String getChannelName(
            int channelIndex);

    /**
     * method GetChannelNumber
     */
    @VTID(46)
    int getChannelNumber(
            java.lang.String channelName);

    /**
     * method GetGlobalChannelCount
     */
    @VTID(47)
    int getGlobalChannelCount();

    /**
     * method GetGlobalChannelName
     */
    @VTID(48)
    java.lang.String getGlobalChannelName(
            int channelIndex);

    /**
     * method GetGlobalChannelNumber
     */
    @VTID(49)
    int getGlobalChannelNumber(
            java.lang.String channelName);

    /**
     * method GetMaxChannelCount
     */
    @VTID(50)
    int getMaxChannelCount();

    /**
     * method GetMaxChannelWrite
     */
    @VTID(51)
    int getMaxChannelWrite();

    /**
     * method GetMaxChannelRead
     */
    @VTID(52)
    int getMaxChannelRead();

    /**
     * method SetChannelFlags
     */
    @VTID(53)
    int setChannelFlags(
            java.lang.String channelName,
            int flags);

    /**
     * method GetChannelFlags
     */
    @VTID(54)
    int getChannelFlags(
            java.lang.String channelName);

    /**
     * method GetChannelDataSize
     */
    @VTID(55)
    int getChannelDataSize(
            java.lang.String channelName);

    /**
     * method GetChannelDataType
     */
    @VTID(56)
    ICAVCDataType getChannelDataType(
            java.lang.String channelName);

    /**
     * method GetChannelData
     */
    @VTID(57)
    java.lang.String getChannelData(
            java.lang.String channelName,
            ICAVCDataType dataType);

    /**
     * method EnumerateServers
     */
    @VTID(58)
    int enumerateServers();

    /**
     * method EnumerateApplications
     */
    @VTID(59)
    int enumerateApplications();

    /**
     * method EnumerateFarms
     */
    @VTID(60)
    int enumerateFarms();

    /**
     * method GetEnumNameCount
     */
    @VTID(61)
    int getEnumNameCount(
            int hndEnum);

    /**
     * method GetEnumNameByIndex
     */
    @VTID(62)
    java.lang.String getEnumNameByIndex(
            int hndEnum,
            int hndIndex);

    /**
     * method CloseEnumHandle
     */
    @VTID(63)
    int closeEnumHandle(
            int hndEnum);

    /**
     * method GetWindowWidth
     */
    @VTID(64)
    int getWindowWidth(
            ICAWindowType wndType,
            int wndFlags);

    /**
     * method GetWindowHeight
     */
    @VTID(65)
    int getWindowHeight(
            ICAWindowType wndType,
            int wndFlags);

    /**
     * method SetWindowSize
     */
    @VTID(66)
    int setWindowSize(
            ICAWindowType wndType,
            int width,
            int height,
            int wndFlags);

    /**
     * method GetWindowXPosition
     */
    @VTID(67)
    int getWindowXPosition(
            ICAWindowType wndType,
            int wndFlags);

    /**
     * method GetWindowYPosition
     */
    @VTID(68)
    int getWindowYPosition(
            ICAWindowType wndType,
            int wndFlags);

    /**
     * method SetWindowPosition
     */
    @VTID(69)
    int setWindowPosition(
            ICAWindowType wndType,
            int xPos,
            int yPos,
            int wndFlags);

    /**
     * method DisplayWindow
     */
    @VTID(70)
    int displayWindow(
            ICAWindowType wndType);

    /**
     * method HideWindow
     */
    @VTID(71)
    int hideWindow(
            ICAWindowType wndType);

    /**
     * method UndockWindow
     */
    @VTID(72)
    int undockWindow();

    /**
     * method DockWindow
     */
    @VTID(73)
    int dockWindow();

    /**
     * method PlaceWindowOnTop
     */
    @VTID(74)
    int placeWindowOnTop();

    /**
     * method PlaceWindowOnBottom
     */
    @VTID(75)
    int placeWindowOnBottom();

    /**
     * method MinimizeWindow
     */
    @VTID(76)
    int minimizeWindow();

    /**
     * method MaximizeWindow
     */
    @VTID(77)
    int maximizeWindow();

    /**
     * method RestoreWindow
     */
    @VTID(78)
    int restoreWindow();

    /**
     * method ShowTitleBar
     */
    @VTID(79)
    int showTitleBar();

    /**
     * method HideTitleBar
     */
    @VTID(80)
    int hideTitleBar();

    /**
     * method EnableSizingBorder
     */
    @VTID(81)
    int enableSizingBorder();

    /**
     * method DisableSizingBorder
     */
    @VTID(82)
    int disableSizingBorder();

    /**
     * method FullScreenWindow
     */
    @VTID(83)
    int fullScreenWindow();

    /**
     * method FocusWindow
     */
    @VTID(84)
    int focusWindow();

    /**
     * method IsWindowDocked
     */
    @VTID(85)
    boolean isWindowDocked();

    /**
     * method GetSessionWidth
     */
    @VTID(86)
    int getSessionWidth();

    /**
     * method GetSessionHeight
     */
    @VTID(87)
    int getSessionHeight();

    /**
     * method GetSessionColorDepth
     */
    @VTID(88)
    int getSessionColorDepth();

    /**
     * method GetScreenWidth
     */
    @VTID(89)
    int getScreenWidth();

    /**
     * method GetScreenHeight
     */
    @VTID(90)
    int getScreenHeight();

    /**
     * method GetScreenColorDepth
     */
    @VTID(91)
    int getScreenColorDepth();

    /**
     * method NewWindow
     */
    @VTID(92)
    int newWindow(
            int xPos,
            int yPos,
            int width,
            int height,
            int flags);

    /**
     * method DeleteWindow
     */
    @VTID(93)
    int deleteWindow();

    /**
     * method GetErrorMessage
     */
    @VTID(94)
    java.lang.String getErrorMessage(
            int errCode);

    /**
     * method GetClientErrorMessage
     */
    @VTID(95)
    java.lang.String getClientErrorMessage(
            int errCode);

    /**
     * method EnableKeyboardInput
     */
    @VTID(96)
    int enableKeyboardInput();

    /**
     * method DisableKeyboardInput
     */
    @VTID(97)
    int disableKeyboardInput();

    /**
     * method IsKeyboardInputEnabled
     */
    @VTID(98)
    boolean isKeyboardInputEnabled();

    /**
     * method EnableMouseInput
     */
    @VTID(99)
    int enableMouseInput();

    /**
     * method DisableMouseInput
     */
    @VTID(100)
    int disableMouseInput();

    /**
     * method IsMouseInputEnabled
     */
    @VTID(101)
    boolean isMouseInputEnabled();

    /**
     * method GetClientNetworkName
     */
    @VTID(102)
    java.lang.String getClientNetworkName();

    /**
     * method GetClientAddressCount
     */
    @VTID(103)
    int getClientAddressCount();

    /**
     * method GetClientAddress
     */
    @VTID(104)
    java.lang.String getClientAddress(
            int index);

    /**
     * method AttachSession
     */
    @VTID(105)
    int attachSession(
            java.lang.String pSessionId);

    /**
     * method DetachSession
     */
    @VTID(106)
    int detachSession(
            java.lang.String pSessionId);

    /**
     * method GetCachedSessionCount
     */
    @VTID(107)
    int getCachedSessionCount();

    /**
     * method IsSessionAttached
     */
    @VTID(108)
    boolean isSessionAttached(
            java.lang.String pSessionId);

    /**
     * method IsSessionDetached
     */
    @VTID(109)
    boolean isSessionDetached(
            java.lang.String pSessionId);

    /**
     * method IsSessionRunning
     */
    @VTID(110)
    boolean isSessionRunning(
            java.lang.String pSessionId);

    /**
     * method SetSessionId
     */
    @VTID(111)
    int setSessionId(
            java.lang.String pSessionId);

    @VTID(112)
    int readyState();

    @VTID(113)
    void readyState(
            int state);

    /**
     * property Address
     */
    @VTID(114)
    java.lang.String address();

    /**
     * property Address
     */
    @VTID(115)
    void address(
            java.lang.String pVal);

    /**
     * property Application
     */
    @VTID(116)
    java.lang.String application();

    /**
     * property Application
     */
    @VTID(117)
    void application(
            java.lang.String pVal);

    /**
     * property AudioBandwidthLimit
     */
    @VTID(118)
    ICASoundQuality audioBandwidthLimit();

    /**
     * property AudioBandwidthLimit
     */
    @VTID(119)
    void audioBandwidthLimit(
            ICASoundQuality pVal);

    /**
     * property Border
     */
    @VTID(120)
    int border();

    /**
     * property Border
     */
    @VTID(121)
    void border(
            int pVal);

    /**
     * property CDMAllowed
     */
    @VTID(122)
    boolean cdmAllowed();

    /**
     * property CDMAllowed
     */
    @VTID(123)
    void cdmAllowed(
            boolean pVal);

    /**
     * property ClientAudio
     */
    @VTID(124)
    boolean clientAudio();

    /**
     * property ClientAudio
     */
    @VTID(125)
    void clientAudio(
            boolean pVal);

    /**
     * property ClientName
     */
    @VTID(126)
    java.lang.String clientName();

    /**
     * property ClientName
     */
    @VTID(127)
    void clientName(
            java.lang.String pVal);

    /**
     * property COMAllowed
     */
    @VTID(128)
    boolean comAllowed();

    /**
     * property COMAllowed
     */
    @VTID(129)
    void comAllowed(
            boolean pVal);

    /**
     * property Compress
     */
    @VTID(130)
    boolean compress();

    /**
     * property Compress
     */
    @VTID(131)
    void compress(
            boolean pVal);

    /**
     * property Connected
     */
    @VTID(132)
    boolean connected();

    /**
     * property ConnectionEntry
     */
    @VTID(133)
    java.lang.String connectionEntry();

    /**
     * property ConnectionEntry
     */
    @VTID(134)
    void connectionEntry(
            java.lang.String pVal);

    /**
     * property CPMAllowed
     */
    @VTID(135)
    boolean cpmAllowed();

    /**
     * property CPMAllowed
     */
    @VTID(136)
    void cpmAllowed(
            boolean pVal);

    /**
     * property CustomMessage
     */
    @VTID(137)
    java.lang.String customMessage();

    /**
     * property CustomMessage
     */
    @VTID(138)
    void customMessage(
            java.lang.String pVal);

    /**
     * property Description
     */
    @VTID(139)
    java.lang.String description();

    /**
     * property Description
     */
    @VTID(140)
    void description(
            java.lang.String pVal);

    /**
     * property DesiredColor
     */
    @VTID(141)
    ICAColorDepth desiredColor();

    /**
     * property DesiredColor
     */
    @VTID(142)
    void desiredColor(
            ICAColorDepth pVal);

    /**
     * property DesiredHRes
     */
    @VTID(143)
    int desiredHRes();

    /**
     * property DesiredHRes
     */
    @VTID(144)
    void desiredHRes(
            int pVal);

    /**
     * property DesiredVRes
     */
    @VTID(145)
    int desiredVRes();

    /**
     * property DesiredVRes
     */
    @VTID(146)
    void desiredVRes(
            int pVal);

    /**
     * property Domain
     */
    @VTID(147)
    java.lang.String domain();

    /**
     * property Domain
     */
    @VTID(148)
    void domain(
            java.lang.String pVal);

    /**
     * property Encrypt
     */
    @VTID(149)
    boolean encrypt();

    /**
     * property Encrypt
     */
    @VTID(150)
    void encrypt(
            boolean pVal);

    /**
     * property Height
     */
    @VTID(151)
    int height();

    /**
     * property ICAFile
     */
    @VTID(152)
    java.lang.String icaFile();

    /**
     * property ICAFile
     */
    @VTID(153)
    void icaFile(
            java.lang.String pVal);

    /**
     * property IconIndex
     */
    @VTID(154)
    int iconIndex();

    /**
     * property IconIndex
     */
    @VTID(155)
    void iconIndex(
            int pVal);

    /**
     * property IconPath
     */
    @VTID(156)
    java.lang.String iconPath();

    /**
     * property IconPath
     */
    @VTID(157)
    void iconPath(
            java.lang.String pVal);

    /**
     * property InitialProgram
     */
    @VTID(158)
    java.lang.String initialProgram();

    /**
     * property InitialProgram
     */
    @VTID(159)
    void initialProgram(
            java.lang.String pVal);

    /**
     * property IPXBrowserAddress
     */
    @VTID(160)
    java.lang.String ipxBrowserAddress();

    /**
     * property IPXBrowserAddress
     */
    @VTID(161)
    void ipxBrowserAddress(
            java.lang.String pVal);

    /**
     * property NetbiosBrowserAddress
     */
    @VTID(162)
    java.lang.String netbiosBrowserAddress();

    /**
     * property NetbiosBrowserAddress
     */
    @VTID(163)
    void netbiosBrowserAddress(
            java.lang.String pVal);

    /**
     * property NotificationReason
     */
    @VTID(164)
    ICAEvent notificationReason();

    /**
     * property PersistentCacheEnabled
     */
    @VTID(165)
    boolean persistentCacheEnabled();

    /**
     * property PersistentCacheEnabled
     */
    @VTID(166)
    void persistentCacheEnabled(
            boolean pVal);

    /**
     * property ProtocolSupport
     */
    @VTID(167)
    java.lang.String protocolSupport();

    /**
     * property ProtocolSupport
     */
    @VTID(168)
    void protocolSupport(
            java.lang.String pVal);

    /**
     * property Reliable
     */
    @VTID(169)
    boolean reliable();

    /**
     * property Reliable
     */
    @VTID(170)
    void reliable(
            boolean pVal);

    /**
     * property SessionEndAction
     */
    @VTID(171)
    ICASessionEndAction sessionEndAction();

    /**
     * property SessionEndAction
     */
    @VTID(172)
    void sessionEndAction(
            ICASessionEndAction pVal);

    /**
     * property Start
     */
    @VTID(173)
    boolean start();

    /**
     * property Start
     */
    @VTID(174)
    void start(
            boolean pVal);

    /**
     * property TCPBrowserAddress
     */
    @VTID(175)
    java.lang.String tcpBrowserAddress();

    /**
     * property TCPBrowserAddress
     */
    @VTID(176)
    void tcpBrowserAddress(
            java.lang.String pVal);

    /**
     * property TransportDriver
     */
    @VTID(177)
    java.lang.String transportDriver();

    /**
     * property TransportDriver
     */
    @VTID(178)
    void transportDriver(
            java.lang.String pVal);

    /**
     * property UIActive
     */
    @VTID(179)
    boolean uiActive();

    /**
     * property UIActive
     */
    @VTID(180)
    void uiActive(
            boolean pVal);

    /**
     * property UpdatesAllowed
     */
    @VTID(181)
    boolean updatesAllowed();

    /**
     * property UpdatesAllowed
     */
    @VTID(182)
    void updatesAllowed(
            boolean pVal);

    /**
     * property Username
     */
    @VTID(183)
    java.lang.String username();

    /**
     * property Username
     */
    @VTID(184)
    void username(
            java.lang.String pVal);

    /**
     * property Version
     */
    @VTID(185)
    java.lang.String version();

    /**
     * property VSLAllowed
     */
    @VTID(186)
    boolean vslAllowed();

    /**
     * property VSLAllowed
     */
    @VTID(187)
    void vslAllowed(
            boolean pVal);

    /**
     * property Width
     */
    @VTID(188)
    int width();

    /**
     * property WinstationDriver
     */
    @VTID(189)
    java.lang.String winstationDriver();

    /**
     * property WinstationDriver
     */
    @VTID(190)
    void winstationDriver(
            java.lang.String pVal);

    /**
     * property WorkDirectory
     */
    @VTID(191)
    java.lang.String workDirectory();

    /**
     * property WorkDirectory
     */
    @VTID(192)
    void workDirectory(
            java.lang.String pVal);

    /**
     * property AppsrvIni
     */
    @VTID(193)
    java.lang.String appsrvIni();

    /**
     * property AppsrvIni
     */
    @VTID(194)
    void appsrvIni(
            java.lang.String pVal);

    /**
     * property ModuleIni
     */
    @VTID(195)
    java.lang.String moduleIni();

    /**
     * property ModuleIni
     */
    @VTID(196)
    void moduleIni(
            java.lang.String pVal);

    /**
     * property WfclientIni
     */
    @VTID(197)
    java.lang.String wfclientIni();

    /**
     * property WfclientIni
     */
    @VTID(198)
    void wfclientIni(
            java.lang.String pVal);

    /**
     * property ClientPath
     */
    @VTID(199)
    java.lang.String clientPath();

    /**
     * property ClientVersion
     */
    @VTID(200)
    java.lang.String clientVersion();

    /**
     * property LogAppend
     */
    @VTID(201)
    boolean logAppend();

    /**
     * property LogAppend
     */
    @VTID(202)
    void logAppend(
            boolean pVal);

    /**
     * property LogConnect
     */
    @VTID(203)
    boolean logConnect();

    /**
     * property LogConnect
     */
    @VTID(204)
    void logConnect(
            boolean pVal);

    /**
     * property LogErrors
     */
    @VTID(205)
    boolean logErrors();

    /**
     * property LogErrors
     */
    @VTID(206)
    void logErrors(
            boolean pVal);

    /**
     * property LogFile
     */
    @VTID(207)
    java.lang.String logFile();

    /**
     * property LogFile
     */
    @VTID(208)
    void logFile(
            java.lang.String pVal);

    /**
     * property LogFlush
     */
    @VTID(209)
    boolean logFlush();

    /**
     * property LogFlush
     */
    @VTID(210)
    void logFlush(
            boolean pVal);

    /**
     * property LogKeyboard
     */
    @VTID(211)
    boolean logKeyboard();

    /**
     * property LogKeyboard
     */
    @VTID(212)
    void logKeyboard(
            boolean pVal);

    /**
     * property LogReceive
     */
    @VTID(213)
    boolean logReceive();

    /**
     * property LogReceive
     */
    @VTID(214)
    void logReceive(
            boolean pVal);

    /**
     * property LogTransmit
     */
    @VTID(215)
    boolean logTransmit();

    /**
     * property LogTransmit
     */
    @VTID(216)
    void logTransmit(
            boolean pVal);

    /**
     * property Title
     */
    @VTID(217)
    java.lang.String title();

    /**
     * property Title
     */
    @VTID(218)
    void title(
            java.lang.String pVal);

    /**
     * property Launch
     */
    @VTID(219)
    boolean launch();

    /**
     * property Launch
     */
    @VTID(220)
    void launch(
            boolean pVal);

    /**
     * property BackgroundColor
     */
    @VTID(221)
    int backgroundColor();

    /**
     * property BackgroundColor
     */
    @VTID(222)
    void backgroundColor(
            int pVal);

    /**
     * property BorderColor
     */
    @VTID(223)
    int borderColor();

    /**
     * property BorderColor
     */
    @VTID(224)
    void borderColor(
            int pVal);

    /**
     * property TextColor
     */
    @VTID(225)
    int textColor();

    /**
     * property TextColor
     */
    @VTID(226)
    void textColor(
            int pVal);

    /**
     * property EncryptionLevelSession
     */
    @VTID(227)
    java.lang.String encryptionLevelSession();

    /**
     * property EncryptionLevelSession
     */
    @VTID(228)
    void encryptionLevelSession(
            java.lang.String pVal);

    /**
     * property HttpBrowserAddress
     */
    @VTID(229)
    java.lang.String httpBrowserAddress();

    /**
     * property HttpBrowserAddress
     */
    @VTID(230)
    void httpBrowserAddress(
            java.lang.String pVal);

    /**
     * property BrowserProtocol
     */
    @VTID(231)
    java.lang.String browserProtocol();

    /**
     * property BrowserProtocol
     */
    @VTID(232)
    void browserProtocol(
            java.lang.String pVal);

    /**
     * property LocHTTPBrowserAddress
     */
    @VTID(233)
    java.lang.String locHTTPBrowserAddress();

    /**
     * property LocHTTPBrowserAddress
     */
    @VTID(234)
    void locHTTPBrowserAddress(
            java.lang.String pVal);

    /**
     * property LocIPXBrowserAddress
     */
    @VTID(235)
    java.lang.String locIPXBrowserAddress();

    /**
     * property LocIPXBrowserAddress
     */
    @VTID(236)
    void locIPXBrowserAddress(
            java.lang.String pVal);

    /**
     * property LocNETBIOSBrowserAddress
     */
    @VTID(237)
    java.lang.String locNETBIOSBrowserAddress();

    /**
     * property LocNETBIOSBrowserAddress
     */
    @VTID(238)
    void locNETBIOSBrowserAddress(
            java.lang.String pVal);

    /**
     * property LocTCPBrowserAddress
     */
    @VTID(239)
    java.lang.String locTCPBrowserAddress();

    /**
     * property LocTCPBrowserAddress
     */
    @VTID(240)
    void locTCPBrowserAddress(
            java.lang.String pVal);

    /**
     * property DoNotUseDefaultCSL
     */
    @VTID(241)
    boolean doNotUseDefaultCSL();

    /**
     * property DoNotUseDefaultCSL
     */
    @VTID(242)
    void doNotUseDefaultCSL(
            boolean pVal);

    /**
     * property ICAPortNumber
     */
    @VTID(243)
    int icaPortNumber();

    /**
     * property ICAPortNumber
     */
    @VTID(244)
    void icaPortNumber(
            int pVal);

    /**
     * property KeyboardTimer
     */
    @VTID(245)
    int keyboardTimer();

    /**
     * property KeyboardTimer
     */
    @VTID(246)
    void keyboardTimer(
            int pVal);

    /**
     * property MouseTimer
     */
    @VTID(247)
    int mouseTimer();

    /**
     * property MouseTimer
     */
    @VTID(248)
    void mouseTimer(
            int pVal);

    /**
     * property Scrollbars
     */
    @VTID(249)
    boolean scrollbars();

    /**
     * property Scrollbars
     */
    @VTID(250)
    void scrollbars(
            boolean pVal);

    /**
     * property ScalingHeight
     */
    @VTID(251)
    int scalingHeight();

    /**
     * property ScalingHeight
     */
    @VTID(252)
    void scalingHeight(
            int pVal);

    /**
     * property ScalingMode
     */
    @VTID(253)
    ICAScalingMode scalingMode();

    /**
     * property ScalingMode
     */
    @VTID(254)
    void scalingMode(
            ICAScalingMode pVal);

    /**
     * property ScalingPercent
     */
    @VTID(255)
    int scalingPercent();

    /**
     * property ScalingPercent
     */
    @VTID(256)
    void scalingPercent(
            int pVal);

    /**
     * property ScalingWidth
     */
    @VTID(257)
    int scalingWidth();

    /**
     * property ScalingWidth
     */
    @VTID(258)
    void scalingWidth(
            int pVal);

    /**
     * property VirtualChannels
     */
    @VTID(259)
    java.lang.String virtualChannels();

    /**
     * property VirtualChannels
     */
    @VTID(260)
    void virtualChannels(
            java.lang.String pVal);

    /**
     * property UseAlternateAddress
     */
    @VTID(261)
    int useAlternateAddress();

    /**
     * property UseAlternateAddress
     */
    @VTID(262)
    void useAlternateAddress(
            int pVal);

    /**
     * property BrowserRetry
     */
    @VTID(263)
    int browserRetry();

    /**
     * property BrowserRetry
     */
    @VTID(264)
    void browserRetry(
            int pVal);

    /**
     * property BrowserTimeout
     */
    @VTID(265)
    int browserTimeout();

    /**
     * property BrowserTimeout
     */
    @VTID(266)
    void browserTimeout(
            int pVal);

    /**
     * property LanaNumber
     */
    @VTID(267)
    int lanaNumber();

    /**
     * property LanaNumber
     */
    @VTID(268)
    void lanaNumber(
            int pVal);

    /**
     * property ICASOCKSProtocolVersion
     */
    @VTID(269)
    int icasocksProtocolVersion();

    /**
     * property ICASOCKSProtocolVersion
     */
    @VTID(270)
    void icasocksProtocolVersion(
            int pVal);

    /**
     * property ICASOCKSProxyHost
     */
    @VTID(271)
    java.lang.String icasocksProxyHost();

    /**
     * property ICASOCKSProxyHost
     */
    @VTID(272)
    void icasocksProxyHost(
            java.lang.String pVal);

    /**
     * property ICASOCKSProxyPortNumber
     */
    @VTID(273)
    int icasocksProxyPortNumber();

    /**
     * property ICASOCKSProxyPortNumber
     */
    @VTID(274)
    void icasocksProxyPortNumber(
            int pVal);

    /**
     * property ICASOCKSRFC1929Username
     */
    @VTID(275)
    java.lang.String icasocksrfC1929Username();

    /**
     * property ICASOCKSRFC1929Username
     */
    @VTID(276)
    void icasocksrfC1929Username(
            java.lang.String pVal);

    /**
     * property ICASOCKSTimeout
     */
    @VTID(277)
    int icasocksTimeout();

    /**
     * property ICASOCKSTimeout
     */
    @VTID(278)
    void icasocksTimeout(
            int pVal);

    /**
     * property SSLEnable
     */
    @VTID(279)
    boolean sslEnable();

    /**
     * property SSLEnable
     */
    @VTID(280)
    void sslEnable(
            boolean pVal);

    /**
     * property SSLProxyHost
     */
    @VTID(281)
    java.lang.String sslProxyHost();

    /**
     * property SSLProxyHost
     */
    @VTID(282)
    void sslProxyHost(
            java.lang.String pVal);

    /**
     * property SSLCiphers
     */
    @VTID(283)
    java.lang.String sslCiphers();

    /**
     * property SSLCiphers
     */
    @VTID(284)
    void sslCiphers(
            java.lang.String pVal);

    /**
     * property SSLNoCACerts
     */
    @VTID(285)
    int sslNoCACerts();

    /**
     * property SSLNoCACerts
     */
    @VTID(286)
    void sslNoCACerts(
            int pVal);

    /**
     * property SSLCommonName
     */
    @VTID(287)
    java.lang.String sslCommonName();

    /**
     * property SSLCommonName
     */
    @VTID(288)
    void sslCommonName(
            java.lang.String pVal);

    /**
     * property AUTHUsername
     */
    @VTID(289)
    java.lang.String authUsername();

    /**
     * property AUTHUsername
     */
    @VTID(290)
    void authUsername(
            java.lang.String pVal);

    /**
     * property XmlAddressResolutionType
     */
    @VTID(291)
    java.lang.String xmlAddressResolutionType();

    /**
     * property XmlAddressResolutionType
     */
    @VTID(292)
    void xmlAddressResolutionType(
            java.lang.String pVal);

    /**
     * property AutoScale
     */
    @VTID(293)
    boolean autoScale();

    /**
     * property AutoScale
     */
    @VTID(294)
    void autoScale(
            boolean pVal);

    /**
     * property AutoAppResize
     */
    @VTID(295)
    boolean autoAppResize();

    /**
     * property AutoAppResize
     */
    @VTID(296)
    void autoAppResize(
            boolean pVal);

    /**
     * property Hotkey1Char
     */
    @VTID(297)
    java.lang.String hotkey1Char();

    /**
     * property Hotkey1Char
     */
    @VTID(298)
    void hotkey1Char(
            java.lang.String pVal);

    /**
     * property Hotkey1Shift
     */
    @VTID(299)
    java.lang.String hotkey1Shift();

    /**
     * property Hotkey1Shift
     */
    @VTID(300)
    void hotkey1Shift(
            java.lang.String pVal);

    /**
     * property Hotkey2Char
     */
    @VTID(301)
    java.lang.String hotkey2Char();

    /**
     * property Hotkey2Char
     */
    @VTID(302)
    void hotkey2Char(
            java.lang.String pVal);

    /**
     * property Hotkey2Shift
     */
    @VTID(303)
    java.lang.String hotkey2Shift();

    /**
     * property Hotkey2Shift
     */
    @VTID(304)
    void hotkey2Shift(
            java.lang.String pVal);

    /**
     * property Hotkey3Char
     */
    @VTID(305)
    java.lang.String hotkey3Char();

    /**
     * property Hotkey3Char
     */
    @VTID(306)
    void hotkey3Char(
            java.lang.String pVal);

    /**
     * property Hotkey3Shift
     */
    @VTID(307)
    java.lang.String hotkey3Shift();

    /**
     * property Hotkey3Shift
     */
    @VTID(308)
    void hotkey3Shift(
            java.lang.String pVal);

    /**
     * property Hotkey4Char
     */
    @VTID(309)
    java.lang.String hotkey4Char();

    /**
     * property Hotkey4Char
     */
    @VTID(310)
    void hotkey4Char(
            java.lang.String pVal);

    /**
     * property Hotkey4Shift
     */
    @VTID(311)
    java.lang.String hotkey4Shift();

    /**
     * property Hotkey4Shift
     */
    @VTID(312)
    void hotkey4Shift(
            java.lang.String pVal);

    /**
     * property Hotkey5Char
     */
    @VTID(313)
    java.lang.String hotkey5Char();

    /**
     * property Hotkey5Char
     */
    @VTID(314)
    void hotkey5Char(
            java.lang.String pVal);

    /**
     * property Hotkey5Shift
     */
    @VTID(315)
    java.lang.String hotkey5Shift();

    /**
     * property Hotkey5Shift
     */
    @VTID(316)
    void hotkey5Shift(
            java.lang.String pVal);

    /**
     * property Hotkey6Char
     */
    @VTID(317)
    java.lang.String hotkey6Char();

    /**
     * property Hotkey6Char
     */
    @VTID(318)
    void hotkey6Char(
            java.lang.String pVal);

    /**
     * property Hotkey6Shift
     */
    @VTID(319)
    java.lang.String hotkey6Shift();

    /**
     * property Hotkey6Shift
     */
    @VTID(320)
    void hotkey6Shift(
            java.lang.String pVal);

    /**
     * property Hotkey7Char
     */
    @VTID(321)
    java.lang.String hotkey7Char();

    /**
     * property Hotkey7Char
     */
    @VTID(322)
    void hotkey7Char(
            java.lang.String pVal);

    /**
     * property Hotkey7Shift
     */
    @VTID(323)
    java.lang.String hotkey7Shift();

    /**
     * property Hotkey7Shift
     */
    @VTID(324)
    void hotkey7Shift(
            java.lang.String pVal);

    /**
     * property Hotkey8Char
     */
    @VTID(325)
    java.lang.String hotkey8Char();

    /**
     * property Hotkey8Char
     */
    @VTID(326)
    void hotkey8Char(
            java.lang.String pVal);

    /**
     * property Hotkey8Shift
     */
    @VTID(327)
    java.lang.String hotkey8Shift();

    /**
     * property Hotkey8Shift
     */
    @VTID(328)
    void hotkey8Shift(
            java.lang.String pVal);

    /**
     * property Hotkey9Char
     */
    @VTID(329)
    java.lang.String hotkey9Char();

    /**
     * property Hotkey9Char
     */
    @VTID(330)
    void hotkey9Char(
            java.lang.String pVal);

    /**
     * property Hotkey9Shift
     */
    @VTID(331)
    java.lang.String hotkey9Shift();

    /**
     * property Hotkey9Shift
     */
    @VTID(332)
    void hotkey9Shift(
            java.lang.String pVal);

    /**
     * property Hotkey10Char
     */
    @VTID(333)
    java.lang.String hotkey10Char();

    /**
     * property Hotkey10Char
     */
    @VTID(334)
    void hotkey10Char(
            java.lang.String pVal);

    /**
     * property Hotkey10Shift
     */
    @VTID(335)
    java.lang.String hotkey10Shift();

    /**
     * property Hotkey10Shift
     */
    @VTID(336)
    void hotkey10Shift(
            java.lang.String pVal);

    /**
     * property ControlWindowText
     */
    @VTID(337)
    java.lang.String controlWindowText();

    /**
     * property ControlWindowText
     */
    @VTID(338)
    void controlWindowText(
            java.lang.String pVal);

    /**
     * property CacheICAFile
     */
    @VTID(339)
    boolean cacheICAFile();

    /**
     * property CacheICAFile
     */
    @VTID(340)
    void cacheICAFile(
            boolean pVal);

    /**
     * property ScreenPercent
     */
    @VTID(341)
    int screenPercent();

    /**
     * property ScreenPercent
     */
    @VTID(342)
    void screenPercent(
            int pVal);

    /**
     * property TWIMode
     */
    @VTID(343)
    boolean twiMode();

    /**
     * property TWIMode
     */
    @VTID(344)
    void twiMode(
            boolean pVal);

    /**
     * property TransportReconnectEnabled
     */
    @VTID(345)
    boolean transportReconnectEnabled();

    /**
     * property TransportReconnectEnabled
     */
    @VTID(346)
    void transportReconnectEnabled(
            boolean pVal);

    /**
     * property TransportReconnectDelay
     */
    @VTID(347)
    int transportReconnectDelay();

    /**
     * property TransportReconnectDelay
     */
    @VTID(348)
    void transportReconnectDelay(
            int pVal);

    /**
     * property TransportReconnectRetries
     */
    @VTID(349)
    int transportReconnectRetries();

    /**
     * property TransportReconnectRetries
     */
    @VTID(350)
    void transportReconnectRetries(
            int pVal);

    /**
     * property AutoLogonAllowed
     */
    @VTID(351)
    boolean autoLogonAllowed();

    /**
     * property AutoLogonAllowed
     */
    @VTID(352)
    void autoLogonAllowed(
            boolean pVal);

    /**
     * property EnableSessionSharingClient
     */
    @VTID(353)
    boolean enableSessionSharingClient();

    /**
     * property EnableSessionSharingClient
     */
    @VTID(354)
    void enableSessionSharingClient(
            boolean pVal);

    /**
     * property SessionSharingName
     */
    @VTID(355)
    java.lang.String sessionSharingName();

    /**
     * property SessionSharingName
     */
    @VTID(356)
    void sessionSharingName(
            java.lang.String pVal);

    /**
     * property SessionSharingLaunchOnly
     */
    @VTID(357)
    boolean sessionSharingLaunchOnly();

    /**
     * property SessionSharingLaunchOnly
     */
    @VTID(358)
    void sessionSharingLaunchOnly(
            boolean pVal);

    /**
     * property DisableCtrlAltDel
     */
    @VTID(359)
    boolean disableCtrlAltDel();

    /**
     * property DisableCtrlAltDel
     */
    @VTID(360)
    void disableCtrlAltDel(
            boolean pVal);

    /**
     * property SessionCacheEnable
     */
    @VTID(361)
    boolean sessionCacheEnable();

    /**
     * property SessionCacheEnable
     */
    @VTID(362)
    void sessionCacheEnable(
            boolean pVal);

    /**
     * property SessionCacheTimeout
     */
    @VTID(363)
    int sessionCacheTimeout();

    /**
     * property SessionCacheTimeout
     */
    @VTID(364)
    void sessionCacheTimeout(
            int pVal);

    /**
     * property session
     */
    @VTID(365)
    ISession session();

    /**
     * property OutputMode
     */
    @VTID(366)
    OutputMode outputMode();

    /**
     * property OutputMode
     */
    @VTID(367)
    void outputMode(
            OutputMode pVal);

    /**
     * property SessionExitTimeout
     */
    @VTID(368)
    int sessionExitTimeout();

    /**
     * property SessionExitTimeout
     */
    @VTID(369)
    void sessionExitTimeout(
            int pVal);

    /**
     * property EnableSessionSharingHost
     */
    @VTID(370)
    boolean enableSessionSharingHost();

    /**
     * property EnableSessionSharingHost
     */
    @VTID(371)
    void enableSessionSharingHost(
            boolean pVal);

    /**
     * property LongCommandLine
     */
    @VTID(372)
    java.lang.String longCommandLine();

    /**
     * property LongCommandLine
     */
    @VTID(373)
    void longCommandLine(
            java.lang.String pVal);

    /**
     * property TWIDisableSessionSharing
     */
    @VTID(374)
    boolean twiDisableSessionSharing();

    /**
     * property TWIDisableSessionSharing
     */
    @VTID(375)
    void twiDisableSessionSharing(
            boolean pVal);

    /**
     * property SessionSharingKey
     */
    @VTID(376)
    java.lang.String sessionSharingKey();

    /**
     * property SessionSharingKey
     */
    @VTID(377)
    void sessionSharingKey(
            java.lang.String pVal);

    /**
     * method DisconnectSessions
     */
    @VTID(378)
    int disconnectSessions(
            java.lang.String pGroupId);

    /**
     * method LogoffSessions
     */
    @VTID(379)
    int logoffSessions(
            java.lang.String pGroupId);

    /**
     * method SetSessionGroupId
     */
    @VTID(380)
    int setSessionGroupId(
            java.lang.String pGroupId);

    /**
     * method GetSessionHandle
     */
    @VTID(381)
    int getSessionHandle();

    /**
     * method SwitchSession
     */
    @VTID(382)
    int switchSession(
            int hSession);

    /**
     * method GetSessionCount
     */
    @VTID(383)
    int getSessionCount();

    /**
     * method GetSessionHandleByIndex
     */
    @VTID(384)
    int getSessionHandleByIndex(
            int index);

    /**
     * method GetSessionGroupCount
     */
    @VTID(385)
    int getSessionGroupCount(
            java.lang.String pGroupId);

    /**
     * property IPCLaunch
     */
    @VTID(386)
    boolean ipcLaunch();

    /**
     * property IPCLaunch
     */
    @VTID(387)
    void ipcLaunch(
            boolean pVal);

    /**
     * property AudioDuringDetach
     */
    @VTID(388)
    boolean audioDuringDetach();

    /**
     * property AudioDuringDetach
     */
    @VTID(389)
    void audioDuringDetach(
            boolean pVal);

    /**
     * property Hotkey11Char
     */
    @VTID(390)
    java.lang.String hotkey11Char();

    /**
     * property Hotkey11Char
     */
    @VTID(391)
    void hotkey11Char(
            java.lang.String pVal);

    /**
     * property Hotkey11Shift
     */
    @VTID(392)
    java.lang.String hotkey11Shift();

    /**
     * property Hotkey11Shift
     */
    @VTID(393)
    void hotkey11Shift(
            java.lang.String pVal);

    /**
     * method IsPassThrough
     */
    @VTID(394)
    boolean isPassThrough();

    /**
     * property VirtualCOMPortEmulation
     */
    @VTID(395)
    boolean virtualCOMPortEmulation();

    /**
     * property VirtualCOMPortEmulation
     */
    @VTID(396)
    void virtualCOMPortEmulation(
            boolean pVal);

    /**
     * method SetSessionSize
     */
    @VTID(397)
    int setSessionSize(
            int depth,
            int hDesiredHres,
            int hDesiredVres,
            int bSingleMonitor);

    /**
     * method GetEngineWndHandle
     */
    @VTID(398)
    long getEngineWndHandle();

    /**
     * method CreateChannelComms
     */
    @VTID(399)
    boolean createChannelComms(
            java.lang.String channelName,
            java.lang.String pipeName);

    /**
     * method EnumerateCCMSessions
     */
    @VTID(400)
    int enumerateCCMSessions();

    /**
     * method StartMonitoringCCMSession
     */
    @VTID(401)
    void startMonitoringCCMSession(
            java.lang.String ccmSessionID,
            boolean bReserved);

    /**
     * method StopMonitoringCCMSession
     */
    @VTID(402)
    void stopMonitoringCCMSession(
            java.lang.String ccmSessionID);

    /**
     * method GetCDMSecuritySettings
     */
    @VTID(403)
    int getCDMSecuritySettings();

    /**
     * method SetCDMSecuritySettings
     */
    @VTID(404)
    void setCDMSecuritySettings(
            int secSetting);

    /**
     * method GetAudioInSecuritySettings
     */
    @VTID(405)
    int getAudioInSecuritySettings();

    /**
     * method SetAudioInSecuritySettings
     */
    @VTID(406)
    void setAudioInSecuritySettings(
            int secSetting);

    /**
     * method GetFlashSecuritySettings
     */
    @VTID(407)
    int getFlashSecuritySettings();

    /**
     * method SetFlashSecuritySettings
     */
    @VTID(408)
    void setFlashSecuritySettings(
            int secSetting);

}
