package fxrestclient.components;

public class RESTSessionManager {
    private static RESTSession session;

    public static RESTSession getSession() {
        return session;
    }

    public static void setSession(RESTSession session) {
        RESTSessionManager.session = session;
    }
}
