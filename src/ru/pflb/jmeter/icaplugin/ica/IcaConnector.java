package ru.pflb.jmeter.icaplugin.ica;

import ru.pflb.jmeter.icaplugin.CitrixIcaConfig;
import ru.pflb.jmeter.icaplugin.CitrixIcaRecorder;
import ru.pflb.jmeter.icaplugin.CitrixIcaSampler;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Описание<br/>
 *
 * @author Иванов Владимир.
 * @version 0.0.1
 */
public abstract class IcaConnector {
    private static final Logger L = Logger.getLogger(IcaConnector.class.getCanonicalName());

    private static HashMap<String, ICASession> mSessions = new HashMap<>();

    public static ICASession newRecordingSession(CitrixIcaRecorder recorder) {
        ICASession session = new ICASession(false);
        if (recorder.getICAFile().isEmpty()) {
            session.configureConnect(recorder.getHost(), recorder.getPort(), recorder.getUser(), recorder.getPassword(),
                    recorder.getDomain(), recorder.getApplication());
        } else {
            session.configureConnect(recorder.getICAFile());
        }

        String[] resolution = recorder.getResolution().split("x");
        session.configureScreen(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]),
                recorder.getColor(), "OutputModeNormal");

        session.setParent(recorder);
        session.setRecordParams(recorder.getRecord());

        return session;
    }

    public static ICASession getReplaySession(CitrixIcaConfig config, String threadName, CitrixIcaSampler sampler) throws InterruptedException {
        String sessionName = config.getHandle() + threadName;
        if (mSessions.containsKey(sessionName)) {
            ICASession session = mSessions.get(sessionName);
            session.setParent(sampler);
            return session;
        }

        ICASession session = new ICASession(true);
        session.setParent(sampler);
        session.setName(sessionName);
        session.configureFromConfig(config);
        mSessions.put(sessionName, session);
        return session;
    }

    public static void removeSession(String sessionName) {
        ICASession s = mSessions.get(sessionName);
        if (s != null) {
            s.setReplayMode(false);
            s.setParent(null);
        }
        mSessions.remove(sessionName);
    }

    public static void onTestStop() {
        for (ICASession s : mSessions.values()) {
            s.setReplayMode(false);
        }

        mSessions.clear();
    }

    public static void unlock() {
        for (ICASession s : mSessions.values()) {
            s.setReplayMode(false);
        }
    }

    public static void lock() {
        for (ICASession s : mSessions.values()) {
            s.setReplayMode(true);
        }
    }
}
