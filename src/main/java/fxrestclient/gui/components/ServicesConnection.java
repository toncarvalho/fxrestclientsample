package fxrestclient.gui.components;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

public class ServicesConnection {
    private final String url;
    private final WebTarget target;
    private final Gson gson;

    public ServicesConnection(String url) {
        this.url = url;

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new HttpUrlConnectorProvider());

        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).build();
        this.target = client.target(url);

        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

    public Session connect() {
        return new Session(target, gson);
    }
}
