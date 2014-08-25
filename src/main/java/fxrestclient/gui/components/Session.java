package fxrestclient.gui.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

/**
 * Classe que mantem a conexão com o servidor
 */
public class Session {

    private final WebTarget target;
    private final Gson gson;
    private String retorno;

    /**
     * Mantém uma sessão com o servidor e permite a execução de requisições REST realizando serialização/deserialização automaticamente.
     *
     * @param target WebTarget configurado para conectar no servidor
     * @param gson   Instância configurada do Gson
     */
    public Session(WebTarget target, Gson gson) {
        this.target = target;
        this.gson = gson;
    }

    private String getRaw(String path, Map<String, String> queryParams) {

      /*  GetInvoker getInvoker = new GetInvoker(target, gson, path, queryParams);

        if (!getInvoker.getState().equals(Worker.State.READY)) {
            getInvoker.restart();
        }

        getInvoker.setOnCancelled(cancelAction -> {
            System.out.println(" servico cancelado");
        });

        getInvoker.setOnFailed(failAction -> {
            System.out.println(" Falha na invocação do serviço");
        });

        getInvoker.setOnReady(readyAction -> {
            System.out.println(" Serviço pronto");
        });

        getInvoker.setOnRunning(runningAction -> {
            System.out.println(" Serviço RODANDO");
        });

        getInvoker.setOnScheduled(schedullAction -> {
            System.out.println(" serviço agendado");
        });

        getInvoker.setOnSucceeded(action -> {
            System.out.println(action.getSource().getValue());

            retorno = action.getSource().getValue().toString();
        });

        return retorno;*/

        WebTarget webTarget = target.path(path);
        for (String k : queryParams.keySet()) {
            webTarget = webTarget.queryParam(k, queryParams.get(k));
        }
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        int status = response.getStatus();
        String entity = response.readEntity(String.class);

        System.out.println(" dados do servidor: " +entity);

        if (status == Response.Status.OK.getStatusCode()) {
            return entity;
        } else {
            Map<String, List<String>> errorData = gson.fromJson(entity, new GenericType<Map<String, List<String>>>() {
            }.getType());
            throw new ServiceException(status, errorData);
        }
    }

    public <T> T get(Class<T> c, String path) {
        return gson.fromJson(getRaw(path, new HashMap<>()), c);
    }

    public <T> T get(Class<T> c, String path, Map<String, String> queryParams) {
        return gson.fromJson(getRaw(path, queryParams), c);
    }

    public <T> T get(GenericType<T> t, String path) {
        return gson.fromJson(getRaw(path, new HashMap<>()), t.getType());
    }

    public <T> T get(GenericType<T> t, String path, Map<String, String> queryParams) {
        return gson.fromJson(getRaw(path, queryParams), t.getType());
    }

    private String postRaw(String path, String data) {
        Response response = target.path(path).request(MediaType.APPLICATION_JSON).post(Entity.entity(data, MediaType.APPLICATION_JSON));
        int status = response.getStatus();
        String entity = response.readEntity(String.class);

        if (status == Response.Status.OK.getStatusCode()) {
            return entity;
        } else {
            Map<String, List<String>> errorData = gson.fromJson(entity, new GenericType<Map<String, List<String>>>() {}.getType());
            throw new ServiceException(status, errorData);
        }
    }

    public <T> void post(Class<T> c, String path, T data) {
        postRaw(path, gson.toJson(data, c));
    }

    public <T> void post(GenericType<T> t, String path, T data) {
        postRaw(path, gson.toJson(data, t.getType()));
    }

    public <T, R> R post(Class<T> c, Class<R> r, String path, T data) {
        String result = postRaw(path, gson.toJson(data, c));
        return gson.fromJson(result, r);
    }

    public <T, R> R post(GenericType<T> t, GenericType<R> r, String path, T data) {
        String result = postRaw(path, gson.toJson(data, t.getType()));
        return gson.fromJson(result, r.getType());
    }

    private String putRaw(String path, String data) {
        Response response = target.path(path).request(MediaType.APPLICATION_JSON).put(Entity.entity(data, MediaType.APPLICATION_JSON));
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

    public <T> void put(Class<T> c, String path, T data) {
        putRaw(path, gson.toJson(data, c));
    }

    public <T> void put(GenericType<T> t, String path, T data) {
        putRaw(path, gson.toJson(data, t.getType()));
    }

    public <T, R> R put(Class<T> c, Class<R> r, String path, T data) {
        String result = putRaw(path, gson.toJson(data, c));
        return gson.fromJson(result, r);
    }

    public <T, R> R put(GenericType<T> t, GenericType<R> r, String path, T data) {
        String result = putRaw(path, gson.toJson(data, t.getType()));
        return gson.fromJson(result, r.getType());
    }

    public void delete(String path) {
        Response response = target.path(path).request(MediaType.APPLICATION_JSON).delete();
        int status = response.getStatus();
        String entity = response.readEntity(String.class);

        if (status != Response.Status.OK.getStatusCode() && status != Response.Status.NO_CONTENT.getStatusCode()) {
            Map<String, List<String>> errorData = gson.fromJson(entity, new GenericType<Map<String, List<String>>>() {
            }.getType());
            throw new ServiceException(status, errorData);
        }
    }
}
