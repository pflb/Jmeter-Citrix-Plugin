package ru.pflb.jmeter.icaplugin;

import com4j.ComException;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.exceptions.IllegalUserActionException;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.testelement.TestElement;
import ru.pflb.jmeter.icaplugin.gui.CitrixIcaRecorderGui;
import ru.pflb.jmeter.icaplugin.gui.CitrixSamplerGui;
import ru.pflb.jmeter.icaplugin.ica.ICASession;
import ru.pflb.jmeter.icaplugin.ica.IcaConnector;
import ru.pflb.jmeter.icaplugin.ica.utils.ICAListener;
import ru.pflb.jmeter.icaplugin.ica.utils.Interaction;
import ru.pflb.jmeter.icaplugin.ica2com.IScreenShot;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CitrixIcaRecorder extends GenericController implements ICAListener {
    private static final String PROPERTY_ICA_FILE = "icafile";
    private static final String PROPERTY_HOST = "host";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String PROPERTY_DOMAIN = "domain";
    private static final String PROPERTY_APPLICATION = "application";
    private static final String PROPERTY_SAVE_SCREENSHOT = "savescreenshot";
    private static final String PROPERTY_SCREENSHOT_PATH = "screenshotpath";
    private static final String PROPERTY_CONTROLLER = "controller";
    private static final String PROPERTY_RESOLUTION = "resolution";
    private static final String PROPERTY_COLOR = "color";
    private static final String PROPERTY_RECORD = "record";
    private static final String PROPERTY_ICA_CONFIG = "icaconfig";
    private static final Logger L = Logger.getLogger(CitrixIcaRecorder.class.getCanonicalName());
    private CitrixIcaRecorderGui mGUI;

    private ICASession mSession;
    private int mConnectionStatus;
    private boolean mRecording;
    private ArrayList<String> mCurrentStep;
    private TestElement mControllerElement;
    private int samplerNum;
    private boolean mStartTag;

    public CitrixIcaRecorder() {
        super();
        mGUI = null;
        mSession = null;
        mConnectionStatus = 0;
        mCurrentStep = null;
        samplerNum = 1;
        mStartTag = false;
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

    public String getScreenshotPath() {
        return getPropertyAsString(PROPERTY_SCREENSHOT_PATH);
    }

    public void setScreenshotPath(String screenshotPath) {
        setProperty(PROPERTY_SCREENSHOT_PATH, screenshotPath);
    }

    public boolean getSaveScreenshot() {
        return getPropertyAsBoolean(PROPERTY_SAVE_SCREENSHOT);
    }

    public void setSaveScreenshot(boolean saveScreenshot) {
        setProperty(PROPERTY_SAVE_SCREENSHOT, saveScreenshot);
    }

    public String getController() {
        return getPropertyAsString(PROPERTY_CONTROLLER);
    }

    public void setController(String controller) {
        setProperty(PROPERTY_CONTROLLER, controller);
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

    public int getRecord() {
        return getPropertyAsInt(PROPERTY_RECORD);
    }

    public void setRecord(int record) {
        setProperty(PROPERTY_RECORD, record);
        if (mSession != null)
            mSession.setRecordParams(record);
    }

    public String getICAConfig() {
        return getPropertyAsString(PROPERTY_ICA_CONFIG);
    }

    public void setICAConfig(String icaConfig) {
        setProperty(PROPERTY_ICA_CONFIG, icaConfig);
    }

    public boolean checkConfig() {
        String ICAFile = getICAFile();
        if (ICAFile.isEmpty()) {
            String host, port, username, userpassword, application;
            host = getHost();
            username = getUser();
            userpassword = getPassword();
            application = getApplication();
            if (host.isEmpty() || username.isEmpty() || userpassword.isEmpty() || application.isEmpty()) {
                mGUI.invokeError("Please, fill all mandatory fields");
                return false;
            }
            port = getPort();
            try {
                if (!port.isEmpty() && Integer.parseInt(port) < 0) {
                    mGUI.invokeError("Port number must be greater then 0");
                    return false;
                }
            } catch (NumberFormatException e) {
                mGUI.invokeError("Port number format error");
                return false;
            }
        } else {
            File f = new File(ICAFile);
            if (!(f.exists() && f.canRead())) {
                mGUI.invokeError("File " + ICAFile + " not found");
                return false;
            }
        }
        if (getSaveScreenshot() && getScreenshotPath().isEmpty()) {
            mGUI.invokeError("Screenshot path does not specified");
            return false;
        } else if (getSaveScreenshot()) {
            File f = new File(getScreenshotPath());
            if (!f.exists()) {
                if (!f.mkdirs() || !f.mkdir()) {
                    mGUI.invokeError("Cannot access screenshot folder");
                    return false;
                }
            }
            if (!f.isDirectory() || !f.canWrite()) {
                mGUI.invokeError("Cannot access screenshot folder");
                return false;
            }
        }
        String resolution = getResolution().toLowerCase();
        String[] res = resolution.split("x");
        if (res.length != 2) {
            mGUI.invokeError("Resolution cannot be parsed. Use that format: WIDTHxHEIGHT");
            return false;
        }
        try {
            if (Integer.parseInt(res[0]) <= 0 || Integer.parseInt(res[1]) <= 0) {
                mGUI.invokeError("Width or height must be greater then 0");
                return false;
            }
        } catch (NumberFormatException e) {
            mGUI.invokeError("Resolution cannot be parsed. Use that format: WIDTHxHEIGHT");
            return false;
        }
        if (getRecord() == 0) {
            mGUI.invokeError("Nothing to record. Please, specify one or more record listeners");
            return false;
        }
        String controller = getController();
        if (controller.isEmpty()) {
            mGUI.invokeError("Please, specify controller where new samplers will be add");
            return false;
        }
        if (!mGUI.checkControllerPath(controller)) {
            mGUI.invokeError("Controller " + controller + " not found");
            return false;
        }

        return true;
    }

    public void setGui(CitrixIcaRecorderGui citrixIcaRecorderGui) {
        mGUI = citrixIcaRecorderGui;
    }

    public void connect() {
        try {
            mSession = IcaConnector.newRecordingSession(this);
            mSession.start();
            mConnectionStatus = 1;
            mGUI.configure(this);
        } catch (ComException e) {
            String error = "Can`t get COM object. Did you run 32-bit java?";
            L.log(Level.SEVERE, error, e);
            mGUI.onStop();
            mGUI.invokeError(error);
        } catch (InterruptedException e) {
            return;
        }
    }

    @Override
    public void onConnecting() {
        mConnectionStatus = 2;
        mGUI.configure(this);
    }

    @Override
    public void onLogon() {
        mConnectionStatus = 3;
        mGUI.configure(this);
        if (mRecording) {
            mCurrentStep = new ArrayList<>();
            mSession.setStep(mCurrentStep);
        }
        samplerNum = 1;
    }

    @Override
    public void onDisconnect() {
        mSession = null;
        mConnectionStatus = 0;
        mRecording = false;
        if (mCurrentStep != null && !mCurrentStep.isEmpty()) {
            makeStep();
        }
        mGUI.configure(this);
    }

    @Override
    public void onError(String errorMessage) {
        stop();
        mGUI.invokeError(errorMessage);
    }

    public void stop() {
        mSession.stop();
        mSession = null;
        mConnectionStatus = 0;
        mRecording = false;
        if (mCurrentStep != null) {
            makeStep();
        }
        mGUI.configure(this);
    }

    private void makeStep() {
        if (mCurrentStep == null) {
            return;
        }
        if (mStartTag) {
            stopTag();
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = mCurrentStep.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("\n");
        }
        CitrixIcaSampler sampler = new CitrixIcaSampler("CIS: " + samplerNum++, getICAConfig(), sb.toString());
        sampler.setProperty(TestElement.GUI_CLASS, CitrixSamplerGui.class.getName());
        mCurrentStep = null;
        try {
            JMeterTreeModel model = GuiPackage.getInstance().getTreeModel();
            model.addComponent(sampler, model.getNodeOf(mControllerElement));
        } catch (IllegalUserActionException e) {
            e.printStackTrace();
        }
    }

    public int isConnected() {
        return mConnectionStatus;
    }

    public void record() {
        if (mConnectionStatus < 3) {
            mRecording = true;
            connect();
        } else {
            mRecording = !mRecording;
            if (mCurrentStep == null) {
                mCurrentStep = new ArrayList<>();
                mSession.setStep(mCurrentStep);
            }
            mGUI.configure(this);
        }
        mSession.setRecording(mRecording);
    }

    public boolean isRecording() {
        return mRecording;
    }

    public void setControllerElement(TestElement treeNode) {
        mControllerElement = treeNode;
    }

    public void newStep() {
        makeStep();
        mCurrentStep = new ArrayList<>();
        mSession.setStep(mCurrentStep);
    }

    public void restartStep() {
        mCurrentStep = new ArrayList<>();
        mSession.setStep(mCurrentStep);
    }

    public void startTag(String timeFactor, String tagName) {
        mStartTag = true;
        mCurrentStep.add(Interaction.Command.StartTag.name() + ":" + timeFactor + ",${" + tagName + "}");
    }

    public void stopTag() {
        mCurrentStep.add(Interaction.Command.EndTag + ":null");
        mStartTag = false;
    }

    public void makeFullScreenshot(String filename) {
        IScreenShot screenShot = mSession.createFullScreenshot();
        screenShot.filename(filename);
        screenShot.save();
    }

    public String getStepName() {
        return "CIR_" + samplerNum;
    }

    public String makeScreenshot(int x, int y, int width, int height) {
        IScreenShot screenshot = mSession.createScreenshot(x, y, width, height);

        if (getSaveScreenshot()) {
            screenshot.filename(getScreenshotPath() + "\\" + getStepName() + "_" + System.currentTimeMillis() + ".bmp");
        }

        return screenshot.bitmapHash();
    }

    public void addAction(String action) {
        mCurrentStep.add(action);
        mSession.updateLastTime();
    }
}
