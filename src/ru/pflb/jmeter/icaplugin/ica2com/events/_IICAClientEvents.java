package ru.pflb.jmeter.icaplugin.ica2com.events;

import com4j.DISPID;
import com4j.IID;

/**
 * _IICAClientEvents Interface
 */
@IID("{238F6F82-B8B4-11CF-8771-00A024541EE3}")
public abstract class _IICAClientEvents {

    @DISPID(-609)
    public void onReadyStateChange(
            int lReadyState) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnClick
     */
    @DISPID(2)
    public void onClick(
            int mouseButton,
            int posX,
            int posY,
            int keyMask) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnConnect
     */
    @DISPID(3)
    public void onConnect() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnConnectFailed
     */
    @DISPID(4)
    public void onConnectFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnLogon
     */
    @DISPID(5)
    public void onLogon() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnLogonFailed
     */
    @DISPID(6)
    public void onLogonFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnDisconnect
     */
    @DISPID(7)
    public void onDisconnect() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnPublishedApp
     */
    @DISPID(8)
    public void onPublishedApp() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnPublishedAppFailed
     */
    @DISPID(9)
    public void onPublishedAppFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnICAFile
     */
    @DISPID(10)
    public void onICAFile() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnICAFileFailed
     */
    @DISPID(11)
    public void onICAFileFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnInitializing
     */
    @DISPID(12)
    public void onInitializing() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnConnecting
     */
    @DISPID(13)
    public void onConnecting() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnInitialProp
     */
    @DISPID(14)
    public void onInitialProp() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnDisconnectFailed
     */
    @DISPID(15)
    public void onDisconnectFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnLogoffFailed
     */
    @DISPID(16)
    public void onLogoffFailed() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnChannelDataReceived
     */
    @DISPID(17)
    public void onChannelDataReceived(
            java.lang.String channelName) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowSized
     */
    @DISPID(18)
    public void onWindowSized(
            int wndType,
            int width,
            int height) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowMoved
     */
    @DISPID(19)
    public void onWindowMoved(
            int wndType,
            int xPos,
            int yPos) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowCreated
     */
    @DISPID(20)
    public void onWindowCreated(
            int wndType,
            int xPos,
            int yPos,
            int width,
            int height) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowDestroyed
     */
    @DISPID(21)
    public void onWindowDestroyed(
            int wndType) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowDocked
     */
    @DISPID(22)
    public void onWindowDocked() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowUndocked
     */
    @DISPID(23)
    public void onWindowUndocked() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowMinimized
     */
    @DISPID(24)
    public void onWindowMinimized() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowMaximized
     */
    @DISPID(25)
    public void onWindowMaximized() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowRestored
     */
    @DISPID(26)
    public void onWindowRestored() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowFullscreened
     */
    @DISPID(27)
    public void onWindowFullscreened() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowHidden
     */
    @DISPID(28)
    public void onWindowHidden(
            int wndType) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowDisplayed
     */
    @DISPID(29)
    public void onWindowDisplayed(
            int wndType) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnWindowCloseRequest
     */
    @DISPID(30)
    public void onWindowCloseRequest() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnDisconnectSessions
     */
    @DISPID(31)
    public void onDisconnectSessions(
            int hCommand) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnDisconnectSessionsFailed
     */
    @DISPID(32)
    public void onDisconnectSessionsFailed(
            int hCommand) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnLogoffSessions
     */
    @DISPID(33)
    public void onLogoffSessions(
            int hCommand) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnLogoffSessionsFailed
     */
    @DISPID(34)
    public void onLogoffSessionsFailed(
            int hCommand) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnSessionSwitch
     */
    @DISPID(35)
    public void onSessionSwitch(
            int hOldSession,
            int hNewSession) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnSessionEventPending
     */
    @DISPID(36)
    public void onSessionEventPending(
            int hSession,
            int eventNum) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnSessionAttach
     */
    @DISPID(37)
    public void onSessionAttach(
            int hSession) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnSessionDetach
     */
    @DISPID(38)
    public void onSessionDetach(
            int hSession) {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnCGPWarn
     */
    @DISPID(39)
    public void onCGPWarn() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnCGPUnwarn
     */
    @DISPID(40)
    public void onCGPUnwarn() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnCGPDisconnect
     */
    @DISPID(41)
    public void onCGPDisconnect() {
        throw new UnsupportedOperationException();
    }

    /**
     * event OnCGPReconnect
     */
    @DISPID(42)
    public void onCGPReconnect() {
        throw new UnsupportedOperationException();
    }

}
