package ru.pflb.jmeter.icaplugin.ica;

import com4j.ComException;
import com4j.EventCookie;
import com4j.ExecutionException;
import ru.pflb.jmeter.icaplugin.CitrixIcaConfig;
import ru.pflb.jmeter.icaplugin.ica.utils.ICAListener;
import ru.pflb.jmeter.icaplugin.ica.utils.Interaction;
import ru.pflb.jmeter.icaplugin.ica2com.*;
import ru.pflb.jmeter.icaplugin.ica2com.events._IICAClientEvents;
import ru.pflb.jmeter.icaplugin.ica2com.events._IKeyboardEvents;
import ru.pflb.jmeter.icaplugin.ica2com.events._IMouseEvents;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class ICASession extends _IICAClientEvents {
    private static final Logger L = Logger.getLogger(ICASession.class.getCanonicalName());
    private IMouse mMouse;

    private boolean mRecording;
    private volatile boolean recordKeyboard;
    private volatile boolean recordMouseClicks;
    private volatile boolean recordMouseMove;
    private boolean mReplayMode;

    private IICAClient ica;
    private EventCookie mClientListener;
    private EventCookie mKeyboardListener;
    private EventCookie mMouseListener;
    private ICAListener mParent;
    private ArrayList<String> mCurrentStep;
    private String mSessionName;
    private long mLastTime;
    private boolean mConnected;
    private IKeyboard mKeyboard;
    private CitrixIcaConfig mConfig;
    private boolean mLogonError;
    private boolean mDisconnected;

    public ICASession(boolean replay) {
        ica = ClassFactory.createICAClient();
        mKeyboard = null;
        mMouse = null;
        mClientListener = null;
        mKeyboardListener = null;
        mMouseListener = null;
        mParent = null;
        recordKeyboard = recordMouseClicks = recordMouseMove = mReplayMode = false;
        mRecording = false;
        mReplayMode = replay;
        mLogonError = false;
        mConnected = false;
    }

    public void configureConnect(String ICAFile) {
        ica.icaFile(ICAFile);
    }

    public void configureConnect(String host, String port,
                                 String user, String password, String domain,
                                 String application) {
        ica.application(application.startsWith("#") ? application : "#" + application);
        ica.address(host);
        if (!port.isEmpty()) {
            ica.icaPortNumber(Integer.parseInt(port));
        }
        ica.username(user);
        ica.setProp("Password", password);
        ica.domain(domain);
    }

    public void configureScreen(int width, int height, String colorMode, String outputMode) {
        ica.desiredHRes(width);
        ica.desiredVRes(height);
        ica.desiredColor(ICAColorDepth.valueOf(colorMode));
        ica.outputMode(OutputMode.valueOf(outputMode));
    }


    public void start() throws InterruptedException {
        mConnected = false;
        mClientListener = ica.advise(_IICAClientEvents.class, new ClientListener());
        ica.ipcLaunch(true);
        ica.twiMode(false);
        ica.launch(true);
        ica.connect();
    }

    public void setRecordParams(int recordParams) {
        recordKeyboard = (recordParams & 1) == 1;
        recordMouseClicks = (recordParams & 2) >> 1 == 1;
        recordMouseMove = (recordParams & 4) >> 2 == 1;
    }

    public void setParent(ICAListener parent) {
        mParent = parent;
    }

    public void stop() {
        mRecording = false;
        mClientListener.close();
        if (mKeyboardListener != null && mMouseListener != null) {
            mKeyboardListener.close();
            mMouseListener.close();
        }
    }

    private void recordEvent(Interaction.Type type, Interaction.Command command, String params) {
        if (mRecording) {
            if (type == Interaction.Type.Keyboard && !recordKeyboard ||
                    type == Interaction.Type.MouseClick && !recordMouseClicks ||
                    type == Interaction.Type.MouseMove && !recordMouseMove)
                return;
            mCurrentStep.add(Interaction.Command.SleepTime + ":" + (System.currentTimeMillis() - mLastTime));
            mLastTime = System.currentTimeMillis();
            mCurrentStep.add(command.name() + ":" + params);
        }
    }

    public void setStep(ArrayList<String> step) {
        mCurrentStep = step;
    }

    public void setRecording(boolean recording) {
        mRecording = recording;
        mLastTime = System.currentTimeMillis();
    }

    public void setReplayMode(boolean b) {
        if (ica != null && mReplayMode) {
            ISession session = ica.session();
            if (session != null) {
                session.replayMode(b);
            }
        }
    }

    public boolean isConnected() {
        return mConnected;
    }

    public void setConnected(boolean connected) {
        mConnected = connected;
    }

    public IICAClient getIca() {
        return ica;
    }

    public IScreenShot createFullScreenshot() {
        return ica.session().createFullScreenShot();
    }

    public IScreenShot createScreenshot(int x, int y, int width, int height) {
        try {
            if (ica != null && ica.session() != null)
                return ica.session().createScreenShot(x, y, width, height);
        } catch (ExecutionException e) {
            return null;
        }
        return null;
    }

    public String getName() {
        return mSessionName;
    }

    public void setName(String sessionName) {
        mSessionName = sessionName;
    }

    public IKeyboard getKeyboard() {
        return mKeyboard;
    }

    public IMouse getMouse() {
        return mMouse;
    }

    public boolean waitConnect() throws InterruptedException {
        return waitConnect(0);
    }

    private void configureFromConfig() throws InterruptedException {
        if (mConfig.getICAFile().isEmpty()) {
            configureConnect(mConfig.getHost(), mConfig.getPort(), mConfig.getUser(), mConfig.getPassword(),
                    mConfig.getDomain(), mConfig.getApplication());
        } else {
            configureConnect(mConfig.getICAFile());
        }
        String[] resolution = mConfig.getResolution().split("x");
        configureScreen(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]),
                mConfig.getColor(), mConfig.getOutputMode() ? "OutputModeNormal" : "OutputModeWindowless");
    }

    public void configureFromConfig(CitrixIcaConfig config) throws InterruptedException {
        mConfig = config;
        configureFromConfig();
    }

    public boolean waitConnect(int count) throws InterruptedException {
        if (mConnected) {
            return true;
        } else if (count > 4) {
            L.info("Connection not possible");
            return false;
        }
        long startTime = System.currentTimeMillis();
        start();

        while (!mConnected && !mLogonError && (System.currentTimeMillis() - startTime) < 120000) {
            Thread.sleep(10);
        }

        startTime = System.currentTimeMillis();
        while (ica.session() == null && (System.currentTimeMillis() - startTime) < 60000)
            Thread.sleep(100);

        if (mLogonError || !mConnected) {
            ICAListener parent = mParent;
            mParent = null;
            mClientListener.close();
            L.info("Restarting session");
            try {
                ica.session().replayMode(false);
                ica.logoff();
            } catch (ComException e) {
            }
            mDisconnected = false;
            try {
                ica.disconnect();
                ica.disconnect();
                ica.disconnect();
            } catch (ComException e) {
            }
            Thread.sleep(60000);
            mParent = parent;
            L.info("Creating new Session");
            ica = ClassFactory.createICAClient();
            mConnected = false;
            mDisconnected = true;
            mLogonError = false;
            configureFromConfig();
            return waitConnect(count + 1);
        }

        return true;
    }

    public void updateLastTime() {
        mLastTime = System.currentTimeMillis();
    }

    private class ClientListener extends _IICAClientEvents {
        public ClientListener() {
            super();
        }

        @Override
        public void onInitializing() {
            super.onInitializing();
        }

        @Override
        public void onConnectFailed() {
            if (mParent != null)
                mParent.onError("Connect failed");
            super.onConnectFailed();
        }

        @Override
        public void onLogon() {
            if (mParent != null)
                mParent.onLogon();
            if (!mReplayMode) {
                mKeyboardListener = ica.session().keyboard().advise(_IKeyboardEvents.class, new KeyboardListener());
                mMouseListener = ica.session().mouse().advise(_IMouseEvents.class, new MouseListener());
                mLastTime = System.currentTimeMillis();
            }
            mConnected = true;
            mLogonError = false;
            mDisconnected = false;
            super.onLogon();
        }

        @Override
        public void onLogonFailed() {
            mLogonError = true;
            mConnected = false;
            if (mParent != null)
                mParent.onError("Logon failed");
            super.onLogonFailed();
        }

        @Override
        public void onDisconnect() {
            if (mReplayMode) {
                IcaConnector.removeSession(mSessionName);
            }
            if (mParent != null)
                mParent.onDisconnect();
            mConnected = false;
            mDisconnected = true;
            super.onDisconnect();
        }

        @Override
        public void onConnecting() {
            if (mParent != null)
                mParent.onConnecting();
            super.onConnecting();
        }

        @Override
        public void onLogoffFailed() {
            mLogonError = true;
            mConnected = false;
            mDisconnected = true;
            if (mParent != null)
                mParent.onDisconnect();
            super.onLogoffFailed();
        }
    }

    private class KeyboardListener extends _IKeyboardEvents {
        public KeyboardListener() {
            super();
        }

        @Override
        public void onKeyUp(int keyId, int modifierState) {
            recordEvent(Interaction.Type.Keyboard, Interaction.Command.KeyUp, keyId + "," + modifierState);
            super.onKeyUp(keyId, modifierState);
        }

        @Override
        public void onKeyDown(int keyId, int modifierState) {
            recordEvent(Interaction.Type.Keyboard, Interaction.Command.KeyDown, keyId + "," + modifierState);
            super.onKeyDown(keyId, modifierState);
        }
    }

    private class MouseListener extends _IMouseEvents {
        MouseListener() {
            super();
        }

        @Override
        public void onMove(int buttonState, int modifierState, int xPos, int yPos) {
            recordEvent(Interaction.Type.MouseMove, Interaction.Command.MouseMove, buttonState + "," + modifierState + "," + xPos + "," + yPos);
            super.onMove(buttonState, modifierState, xPos, yPos);
        }

        @Override
        public void onMouseDown(int buttonState, int modifierState, int xPos, int yPos) {
            recordEvent(Interaction.Type.MouseClick, Interaction.Command.MouseDown, buttonState + "," + modifierState + "," + xPos + "," + yPos);
            super.onMouseDown(buttonState, modifierState, xPos, yPos);
        }

        @Override
        public void onMouseUp(int buttonState, int modifierState, int xPos, int yPos) {
            recordEvent(Interaction.Type.MouseClick, Interaction.Command.MouseUp, buttonState + "," + modifierState + "," + xPos + "," + yPos);
            super.onMouseUp(buttonState, modifierState, xPos, yPos);
        }

        @Override
        public void onDoubleClick() {
            recordEvent(Interaction.Type.MouseClick, Interaction.Command.MouseDClick, null);
            super.onDoubleClick();
        }
    }
}
