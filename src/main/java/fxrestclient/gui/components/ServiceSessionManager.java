package fxrestclient.gui.components;

public class ServiceSessionManager {
    private static Session session;

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        ServiceSessionManager.session = session;
    }
}
