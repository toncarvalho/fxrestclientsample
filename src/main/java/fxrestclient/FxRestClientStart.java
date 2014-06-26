package fxrestclient;

import javax.ws.rs.ProcessingException;
import javafx.application.Application;
import javafx.stage.Stage;
import fxrestclient.components.RESTConnection;
import fxrestclient.components.RESTSessionManager;

public class FxRestClientStart extends Application {
    @Override
    public void start(final Stage stage) throws Exception {

        // TODO: Configuração dos parâmetros de conexão
        String url = this.getParameters().getUnnamed().get(0);

        System.out.println(" url: " + url);

        try {
            RESTConnection conn = new RESTConnection(url);
            RESTSessionManager.setSession(conn.connect());
            RESTSessionManager.getSession().get(String.class, "/status");
        } catch (ProcessingException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CONECTAR COM O SERVIDOR");
            return;
        }

        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setMaximized(true);
        stage.setTitle("Rest  Sample");
        //stage.setScene(new MainScreen());
        stage.show();
    }
}
