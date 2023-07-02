package ru.pflb.jmeter.icaplugin;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.testelement.TestIterationListener;
import org.apache.jmeter.testelement.TestStateListener;

import ru.pflb.jmeter.icaplugin.ica.IcaConnector;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class CitrixIcaConfig extends ConfigTestElement implements TestIterationListener, TestStateListener, NoThreadClone {
    private static final String PROPERTY_HANDLE = "handler";
    private static final String PROPERTY_ICA_FILE = "icafile";
    private static final String PROPERTY_HOST = "host";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String PROPERTY_DOMAIN = "domain";
    private static final String PROPERTY_APPLICATION = "application";
    private static final String PROPERTY_SAVE_SCREENSHOT = "savescreenshot";
    private static final String PROPERTY_SCREENSHOT_PATH = "screenshotpath";
    private static final String PROPERTY_RESOLUTION = "resolution";
    private static final String PROPERTY_COLOR = "color";
    private static final String PROPERTY_OUTPUT_MODE = "outputmode";
    private static final String PROPERTY_SLEEP_TIME = "sleeptimes";
    private static final String PROPERTY_SLEEP_MULTIPLE = "sleepmultiple";
    private static final String PROPERTY_NEW_SESSION = "newsession";
    private static final String PROPERTY_REPLAY_MODE = "replaymode";


    private static final Logger L = LoggerFactory.getLogger(CitrixIcaConfig.class);
    private static HashMap<String, CitrixIcaConfig> mConfigs = new HashMap<>();

    public CitrixIcaConfig() {
        super();
    }

    public static CitrixIcaConfig getConfig(String icaConfig) {
        return mConfigs.get(icaConfig);
    }

    public String getHandle() {
        return getPropertyAsString(PROPERTY_HANDLE);
    }

    public void setHandle(String handle) {
        setProperty(PROPERTY_HANDLE, handle);
    }

    public String getPort() {
        return getPropertyAsString(PROPERTY_PORT);
    }

    public void setPort(String port) {
        setProperty(PROPERTY_PORT, port);
    }

    public String getUser() {
        return getPropertyAsString(PROPERTY_USER);
    }

    public void setUser(String user) {
        setProperty(PROPERTY_USER, user);
    }

    public String getICAFile() {
        return getPropertyAsString(PROPERTY_ICA_FILE);
    }

    public void setICAFile(String ICAFile) {
        setProperty(PROPERTY_ICA_FILE, ICAFile);
    }

    public String getHost() {
        return getPropertyAsString(PROPERTY_HOST);
    }

    public void setHost(String host) {
        setProperty(PROPERTY_HOST, host);
    }

    public String getPassword() {
        return getPropertyAsString(PROPERTY_PASSWORD);
    }

    public void setPassword(String password) {
        setProperty(PROPERTY_PASSWORD, password);
    }

    public String getApplication() {
        return getPropertyAsString(PROPERTY_APPLICATION);
    }

    public void setApplication(String application) {
        setProperty(PROPERTY_APPLICATION, application);
    }

    public String getDomain() {
        return getPropertyAsString(PROPERTY_DOMAIN);
    }

    public void setDomain(String domain) {
        setProperty(PROPERTY_DOMAIN, domain);
    }

    public boolean getSaveScreenshots() {
        return getPropertyAsBoolean(PROPERTY_SAVE_SCREENSHOT);
    }

    public void setSaveScreenshot(boolean saveScreenshots) {
        setProperty(PROPERTY_SAVE_SCREENSHOT, saveScreenshots);
    }

    public void setScreenshotPath(String screenshotPath) {
        setProperty(PROPERTY_SCREENSHOT_PATH, screenshotPath);
    }

    public String getScreenshotsPath() {
        return getPropertyAsString(PROPERTY_SCREENSHOT_PATH);
    }

    public String getResolution() {
        return getPropertyAsString(PROPERTY_RESOLUTION);
    }

    public void setResolution(String resolution) {
        setProperty(PROPERTY_RESOLUTION, resolution);
    }

    public String getColor() {
        return getPropertyAsString(PROPERTY_COLOR);
    }

    public void setColor(String color) {
        setProperty(PROPERTY_COLOR, color);
    }

    public boolean getOutputMode() {
        return getPropertyAsBoolean(PROPERTY_OUTPUT_MODE);
    }

    public void setOutputMode(boolean outputMode) {
        setProperty(PROPERTY_OUTPUT_MODE, outputMode);
    }

    public boolean getSleepTime() {
        return getPropertyAsBoolean(PROPERTY_SLEEP_TIME);
    }

    public void setSleepTime(boolean sleepTime) {
        setProperty(PROPERTY_SLEEP_TIME, sleepTime);
    }

    public String getSleepMultiple() {
        return getPropertyAsString(PROPERTY_SLEEP_MULTIPLE);
    }

    public void setSleepMultiple(String sleepMMultiple) {
        setProperty(PROPERTY_SLEEP_MULTIPLE, sleepMMultiple);
    }

    @Override
    public void testStarted() {
        mConfigs.put(getHandle(), this);
    }

    @Override
    public void testStarted(String s) {
        mConfigs.put(getHandle(), this);
    }

    @Override
    public void testEnded() {
        mConfigs.clear();
        IcaConnector.onTestStop();
    }

    @Override
    public void testEnded(String s) {
        mConfigs.clear();
        IcaConnector.onTestStop();
    }

    @Override
    public void testIterationStart(LoopIterationEvent loopIterationEvent) {

    }

    public boolean getNewSession() {
        return getPropertyAsBoolean(PROPERTY_NEW_SESSION);
    }

    public void setNewSession(boolean newSession) {
        setProperty(PROPERTY_NEW_SESSION, newSession);
    }

    public boolean getReplayMode() {
        return getPropertyAsBoolean(PROPERTY_REPLAY_MODE);
    }

    public void setReplayMode(boolean replayMode) {
        setProperty(PROPERTY_REPLAY_MODE, replayMode);
    }
}
