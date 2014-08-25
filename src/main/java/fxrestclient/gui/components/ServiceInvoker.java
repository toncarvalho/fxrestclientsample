package fxrestclient.gui.components;

import javax.ws.rs.client.WebTarget;
import javafx.concurrent.Service;
import com.google.gson.Gson;

/**
 * Created by ton on 8/25/14.
 */
public abstract class ServiceInvoker extends Service<String> {

    protected WebTarget target;
    protected Gson gson;
    protected String path;


    protected ServiceInvoker(final WebTarget target, final Gson gson) {
        this.target = target;
        this.gson = gson;
    }
}
