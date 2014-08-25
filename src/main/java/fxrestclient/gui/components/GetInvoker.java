package fxrestclient.gui.components;

import java.util.List;
import java.util.Map;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javafx.concurrent.Task;
import com.google.gson.Gson;

/**
 * Created by ton on 8/25/14.
 */
public class GetInvoker extends ServiceInvoker {

    protected Map<String, String> queryParams;

    protected GetInvoker(final WebTarget target, final Gson gson, final String path) {
        super(target, gson);
        this.path = path;
    }

    protected GetInvoker(final WebTarget target, final Gson gson, final String path, final Map<String, String> queryParams) {
        super(target, gson);
        this.path = path;
        this.queryParams = queryParams;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                WebTarget webTarget = target.path(path);
                for (String k : queryParams.keySet()) {
                    webTarget = webTarget.queryParam(k, queryParams.get(k));
                }
                Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
                int status = response.getStatus();
                String entity = response.readEntity(String.class);

                if (status == Response.Status.OK.getStatusCode()) {
                    return entity;
                } else {
                    Map<String, List<String>> errorData = gson.fromJson(entity, new GenericType<Map<String, List<String>>>() {
                    }.getType());
                    throw new ServiceException(status, errorData);
                }
            }
        };
    }
}
