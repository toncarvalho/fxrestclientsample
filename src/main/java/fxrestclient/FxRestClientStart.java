package fxrestclient;

import java.util.Map;
import java.util.TreeMap;
import javax.ws.rs.ProcessingException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import fxrestclient.gui.components.ServiceSessionManager;
import fxrestclient.gui.components.ServicesConnection;
import fxrestclient.gui.components.Session;

public class FxRestClientStart extends Application {

    private Session session;

    @Override
    public void start(final Stage stage) throws Exception {

        // TODO: Configuração dos parâmetros de conexão
        String url = this.getParameters().getUnnamed().get(0);

        System.out.println(" url: " + url);

        try {
            ServicesConnection conn = new ServicesConnection(url);
            ServiceSessionManager.setSession(conn.connect());
            //ServiceSessionManager.getSession().get(String.class, "/status");

            session = ServiceSessionManager.getSession();
            session.get(String.class, "/status");
        } catch (ProcessingException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CONECTAR COM O SERVIDOR");
            return;
        }

        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setMaximized(true);
        stage.setTitle("Rest  Sample");

        VBox box = new VBox();
        box.getChildren().add(new Text("Exemplo de conexao com servidor REST"));

        Button btnGetSample = new Button("Exemplo com GET");

        box.getChildren().add(btnGetSample);

        stage.setScene(new Scene(box));
        stage.show();

        btnGetSample.setOnAction(click -> {
            //http://localhost:8080/restserver/rest/pessoa/1

            Map<String, String> parameters = new TreeMap<>();
            parameters.put("id", "1");

            //String.format("/produto/%d", forecast.getProdutoId()));

            String retorno = session.get(String.class, String.format("/pessoa/%d",1));

            box.getChildren().add(new Text(retorno));
        });
    }
}
