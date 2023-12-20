package ru.pflb.jmeter.icaplugin;

import com4j.ComException;
import com4j.ExecutionException;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import ru.pflb.jmeter.icaplugin.ica.ICASession;
import ru.pflb.jmeter.icaplugin.ica.IcaConnector;
import ru.pflb.jmeter.icaplugin.ica.utils.CharReplacer;
import ru.pflb.jmeter.icaplugin.ica.utils.ICAListener;
import ru.pflb.jmeter.icaplugin.ica.utils.Interaction.Command;
import ru.pflb.jmeter.icaplugin.ica2com.IKeyboard;
import ru.pflb.jmeter.icaplugin.ica2com.IMouse;
import ru.pflb.jmeter.icaplugin.ica2com.IScreenShot;
import ru.pflb.jmeter.ocr.OCRConfig;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Описание
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public class CitrixIcaSampler extends AbstractSampler implements ICAListener, Interruptible {
    private static final Logger L = LoggerFactory.getLogger(CitrixIcaSampler.class);

    private static final String PROPERTY_HANDLE = "handle";
    private static final String PROPERTY_CONTENT = "content";
    private static final String CONTENT_ENCODING = "UTF-8";

    private CitrixIcaConfig mConfig;
    private ICASession mIca;

    private StringBuilder resultData;
    private IKeyboard mKeyboard;
    private IMouse mMouse;

    public CitrixIcaSampler() {
        super();
    }

    public CitrixIcaSampler(String name, String handle, String steps) {
        super();
        setName(name);
        setHandle(handle);
        setRawContent(steps);
    }

    private SampleResult setError(SampleResult result, String msg) {
        resultData.append(msg).append("\n");
        result.setSuccessful(false);
        result.setResponseData(resultData.toString(), CONTENT_ENCODING);
        if (result.getStartTime() == 0)
            result.sampleStart();
        result.sampleEnd();
        mIca.setParent(null);
        return result;
    }

    public SampleResult onInterrupted(SampleResult result) {
        setError(result, "Sampler was interrupted");
        return result;
    }

    public SampleResult onException(SampleResult result, Exception e) {
        setError(result, e.getMessage());
        return result;
    }

    @Override
    public SampleResult sample(Entry entry) {
        mKeyboard = null;
        mMouse = null;

        String request = getPropertyAsString(PROPERTY_CONTENT);
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.setSuccessful(true);
        result.setSamplerData(request);

        resultData = new StringBuilder();

        try {
            if (!logon()) {
                result.sampleStart();
                mIca.setParent(null);
                return setError(result, "Connection error");
            }
            mIca.setReplayMode(mConfig.getReplayMode());
            getDevices();
        } catch (InterruptedException e) {
            return onInterrupted(result);
        } catch (Exception e) {
            return onException(result, e);
        }

        result.sampleStart();

        try {
            readCommands(request, result);
        } catch (InterruptedException e) {
            return onInterrupted(result);
        } catch (Exception e) {
            setError(result, resultData.toString() + "\n" + e.getMessage());
            L.error("Exception: ", e);
            return result;
        }

        mIca.setParent(null);

        result.sampleEnd();
        result.setResponseData(resultData.toString(), CONTENT_ENCODING);
        return result;
    }

    private void readCommands(String request, SampleResult result) throws InterruptedException {
        String[] commands = request.split("\n");
        for (String s : commands) {
            s.trim();
        }

        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].isEmpty() || commands[i].startsWith("#"))
                continue;
            Command command;
            try {
                command = Command.valueOf(commands[i].substring(0, commands[i].indexOf(":")));
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            String params = commands[i].substring(commands[i].indexOf(":") + 1);

            if (command.equals(Command.StartTag)) {
                for (; i < commands.length; ++i) {
                    Command cmd = Command.valueOf(commands[i].substring(0, commands[i].indexOf(":")));
                    if (cmd.equals(Command.EndTag)) {
                        break;
                    }
                }
            }

            try {
                if (!runCommand(command, params)) {
                    result.setSuccessful(false);
                }
            } catch (ComException e) {
                L.error("Uncaught Exception", e);
                result.setSuccessful(false);
                resultData.append(e.getMessage()).append("\n");
            }
        }
    }

    private boolean runCommand(Command command, String params) throws InterruptedException {
        resultData.append(command).append("\t").append(params).append("\t - ");

        switch (command) {
            case SleepTime:
                return sleepTime(params, false, true);
            case KeyDown:
                return keyDown(params, true);
            case KeyUp:
                return keyUp(params, true);
            case MouseDown:
                return mouseDown(params);
            case MouseUp:
                return mouseUp(params);
            case MouseDClick:
                return mouseDClick(params);
            case MouseMove:
                return mouseMove(params);
            case StartTag:
                return startTag(params);
            case Screenshot:
                return screenshot(params);
            case OCR:
                return makeOCR(params);
            default:
                resultData.append("OK, ignored").append("\n");
                return true;
        }
    }

    private boolean makeOCR(String inParams) throws InterruptedException {
        String[] params = inParams.split(",");

        int areaX = Integer.parseInt(params[0]);
        int areaY = Integer.parseInt(params[1]);
        int width = Integer.parseInt(params[2]);
        int height = Integer.parseInt(params[3]);
        String OCRHandle = params[4];
        String resultVar = params[5];
        String result;

        String tmpFileName = getThreadName() + "_" + getName().replaceAll(":", ".") + "_" + System.currentTimeMillis() + ".bmp";
        if (mConfig.getSaveScreenshots())
            tmpFileName = mConfig.getScreenshotsPath() + "\\" + tmpFileName;
        else {
            File f = new File(tmpFileName);
            tmpFileName = f.getAbsolutePath();
        }
        IScreenShot screenshot = null;

        for (int i = 0; i < 5 && screenshot == null; ++i) {
            screenshot = mIca.createScreenshot(areaX, areaY, width, height);
            if (screenshot == null)
                Thread.sleep(100);
        }

        if (screenshot == null) {
            resultData.append("Can`t get screenshot").append("\n");
            return false;
        }


        screenshot.filename(tmpFileName);
        screenshot.save();

        try {
            result = OCRConfig.makeOCR(OCRHandle, tmpFileName);
            if (!resultVar.isEmpty()) {
                JMeterContextService.getContext().getVariables().put(resultVar, result);
            }
            resultData.append("OK, text = ").append(result).append("\n");
            if (!mConfig.getSaveScreenshots()) {
                (new File(tmpFileName)).delete();
            }
            return true;
        } catch (IOException e) {
            resultData.append("IOException - can`t recognize image").append("\n");
            return false;
        }
    }

    private boolean screenshot(String inParams) throws InterruptedException {
        String[] params = inParams.split(",");

        int offset = 0;
        ArrayList<String> recordedHashes = new ArrayList<>();

        for (int i = 0; i < params.length - 11; ++i) {
            recordedHashes.add(params[offset]);
            offset++;
        }

        int areaX = Integer.parseInt(params[offset]);
        int areaY = Integer.parseInt(params[offset + 1]);
        int areaWidth = Integer.parseInt(params[offset + 2]);
        int areaHeight = Integer.parseInt(params[offset + 3]);
        int deltaX = Integer.parseInt(params[offset + 4]);
        int deltaY = Integer.parseInt(params[offset + 5]);
        boolean waitSync = Boolean.parseBoolean(params[offset + 6]);
        long maxWaitTime = Long.parseLong(params[offset + 7]);
        long repeatTime = Long.parseLong(params[offset + 8]);
        boolean errorOnDistinctHash = Boolean.parseBoolean(params[offset + 9]);
        String outVariable = params[offset + 10];

        if (mConfig.getSleepTime() && !mConfig.getSleepMultiple().isEmpty()) {
            maxWaitTime *= Double.parseDouble(mConfig.getSleepMultiple());
        }

        boolean saveScreenshots = mConfig.getSaveScreenshots();
        String savePath = mConfig.getScreenshotsPath();

        boolean found = false;
        long startTime = System.currentTimeMillis();
        IScreenShot screenshot;
        IScreenShot centralScreenshot;

        int curDeltaX;
        int curDeltaY;

        int errorsCount = 0;

        do {
            screenshot = mIca.createScreenshot(areaX, areaY, areaWidth, areaHeight);
            centralScreenshot = screenshot;
            if (screenshot == null) {
                errorsCount++;
                if (errorsCount > 10) {
                    resultData.append("Can`t take screenshot").append("\n");
                    break;
                } else {
                    errorsCount = 0;
                }
                continue;
            }

            try {
                if (recordedHashes.contains(screenshot.bitmapHash())) {
                    found = true;
                } else {
                    if (deltaX > 0 || deltaY > 0) {
                        Thread.sleep(repeatTime);
                        for (curDeltaY = -deltaY; !found && curDeltaY <= deltaY && (System.currentTimeMillis() - startTime) < maxWaitTime; curDeltaY++) {
                            for (curDeltaX = -deltaX; !found && curDeltaX <= deltaX && (System.currentTimeMillis() - startTime) < maxWaitTime; curDeltaX++) {
                                screenshot = mIca.createScreenshot(areaX + curDeltaX, areaY + curDeltaY, areaWidth, areaHeight);

                                if (screenshot == null) {
                                    errorsCount++;
                                    if (errorsCount > 10) {
                                        resultData.append("Can`t take screenshot").append("\n");
                                        break;
                                    } else {
                                        errorsCount = 0;
                                    }
                                    curDeltaX--;
                                    continue;
                                }

                                if (recordedHashes.contains(screenshot.bitmapHash())) {
                                    found = true;
                                } else if (curDeltaX != deltaX && curDeltaY != deltaY)
                                    Thread.sleep(repeatTime);
                            }
                        }
                    }
                }
            } catch (ComException | com4j.ExecutionException e) {
                errorsCount++;
                if (errorsCount > 10) {
                    resultData.append("Can`t take screenshot").append("\n");
                    break;
                } else {
                    errorsCount = 0;
                }
                continue;
            }
            if (!found)
                Thread.sleep(repeatTime);
        } while (waitSync && !found && (System.currentTimeMillis() - startTime) < maxWaitTime);

        if (found && saveScreenshots) {
            screenshot.filename(savePath + "\\" + getName().replaceAll(":", "_") + "_" + startTime + ".bmp");
            try {
                screenshot.save();
            } catch (ComException e) {

            }
        } else if (saveScreenshots && centralScreenshot != null) {
            centralScreenshot.filename(savePath + "\\" + getThreadName() + "_" + getName().replaceAll(":", ".") + "_" + startTime + ".bmp");
            try {
                screenshot.save();
            } catch (ComException e) {

            }
        }

        if (!outVariable.isEmpty()) {
            JMeterContextService.getContext().getVariables().put(outVariable, String.valueOf(found));
        }

        if (found)
            resultData.append("OK, time = " + (System.currentTimeMillis() - startTime));
        else
            try {
                resultData.append("FAIL - distinct hashes, hash=" + centralScreenshot.bitmapHash());
            } catch (ComException | ExecutionException | NullPointerException e) {
                resultData.append("FAIL - distinct hashes, hash unavailable");
            }
        resultData.append("\n");

        if (!errorOnDistinctHash)
            return true;

        return found;
    }

    private boolean startTag(String inParam) throws InterruptedException {
        long timeFactor = Long.parseLong(inParam.substring(0, inParam.indexOf(",")));
        String text = inParam.substring(inParam.indexOf(",") + 1);
        for (int i = 0; i < text.length(); ++i) {
            char c = CharReplacer.replaceChar(text.charAt(i));

            keyDown(KeyEvent.VK_ALT + ",", false);
            Thread.sleep(5);
            keyDown(KeyEvent.VK_NUMPAD0 + ",", false);
            Thread.sleep(5);
            keyUp(KeyEvent.VK_NUMPAD0 + ",", false);
            Thread.sleep(5);

            String altCode = Integer.toString(c);
            int keyCode;

            for (int j = 0; j < altCode.length(); j++) {
                keyCode = (char) (altCode.charAt(j) + '0');
                Thread.sleep(2);
                keyDown(keyCode + ",", false);
                Thread.sleep(2);
                keyUp(keyCode + ",", false);
            }
            keyUp(KeyEvent.VK_ALT + ",", false);
            Thread.sleep(timeFactor);
        }

        resultData.append("OK").append("\n");

        return true;
    }

    private boolean mouseMove(String inParam) {
        String[] params = inParam.split(",");

        mMouse.sendMouseMove(Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                Integer.parseInt(params[2]), Integer.parseInt(params[3]));

        resultData.append("OK").append("\n");

        return true;
    }

    private boolean mouseDClick(String params) {
        //TODO this not need - this really not need
        /*mouseDown(params);
        mouseUp(params);
        mouseDown(params);
        mouseUp(params);*/

        resultData.append("OK").append("\n");

        return true;
    }

    private boolean mouseUp(String inParam) {
        String[] params = inParam.split(",");

        mMouse.sendMouseUp(Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                Integer.parseInt(params[2]), Integer.parseInt(params[3]));

        resultData.append("OK").append("\n");

        return true;
    }

    private boolean mouseDown(String inParam) {
        String[] params = inParam.split(",");

        mMouse.sendMouseDown(Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                Integer.parseInt(params[2]), Integer.parseInt(params[3]));

        resultData.append("OK").append("\n");

        return true;
    }

    private boolean keyUp(String inParam, boolean log) {
        String[] params = inParam.split(",");

        mKeyboard.sendKeyUp(Integer.parseInt(params[0]));

        if (log)
            resultData.append("OK").append("\n");

        return true;
    }

    private boolean keyDown(String inParam, boolean log) {
        String[] params = inParam.split(",");

        mKeyboard.sendKeyDown(Integer.parseInt(params[0]));

        if (log)
            resultData.append("OK").append("\n");

        return true;
    }

    private boolean sleepTime(String inParam, boolean ignoreMultiple, boolean log) throws InterruptedException {
        boolean result = true;
        if (!mConfig.getSleepTime()) {
            resultData.append("ignored").append("\n");
            return result;
        }
        long time;
        try {
            time = Long.parseLong(inParam);
        } catch (NumberFormatException e) {
            resultData.append("FAIL - ").append("TimeOut can not be parsed");
            return false;
        }
        if (!ignoreMultiple) {
            String multiple = mConfig.getSleepMultiple();

            if (!multiple.isEmpty()) {
                try {
                    time *= Double.parseDouble(multiple);
                } catch (NumberFormatException e) {
                    result = false;
                }
            }
        }
        Thread.sleep(time);

        if (log) {
            if (result)
                resultData.append("OK").append("\n");
            else {
                resultData.append("WARN - multiple can not be parsed").append("\n");
            }
        }
        return result;
    }

    private boolean logon() throws InterruptedException {
        getIca();
        return mIca.waitConnect();
    }

    private void getDevices() {
        mKeyboard = mIca.getIca().session().keyboard();
        mMouse = mIca.getIca().session().mouse();
    }

    private void getIca() throws InterruptedException {
        int iteration = 0;
        mConfig = CitrixIcaConfig.getConfig(getHandle());
        if (mConfig.getNewSession()) {
            iteration = JMeterContextService.getContext().getVariables().getIteration();
        }
        mIca = IcaConnector.getReplaySession(mConfig, JMeterContextService.getContext().getThread().getThreadName() + "_" + iteration, this);
    }

    public String getHandle() {
        return getPropertyAsString(PROPERTY_HANDLE);
    }

    public void setHandle(String handle) {
        setProperty(PROPERTY_HANDLE, handle);
    }

    public String getRawContent() {
        return getPropertyAsString(PROPERTY_CONTENT);
    }

    public void setRawContent(String content) {
        setProperty(PROPERTY_CONTENT, content);
    }

    @Override
    public void onConnecting() {
    }

    @Override
    public void onLogon() {
    }

    @Override
    public void onDisconnect() {
        resultData.append("Disconnected");
    }

    @Override
    public void onError(String errorMessage) {
        resultData.append("Error: ").append(errorMessage).append("\n");
    }

    @Override
    public boolean interrupt() {
        return true;
    }
}
