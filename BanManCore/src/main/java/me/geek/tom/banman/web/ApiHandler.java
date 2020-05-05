package me.geek.tom.banman.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.geek.tom.banman.BanEntry;
import me.geek.tom.banman.manager.BanManager;
import me.geek.tom.banman.web.json.JsonUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static me.geek.tom.banman.web.BanManServer.cors;

public class ApiHandler extends AbstractHandler {

    private static final Gson gson = new Gson();
    public static final String APPLICATION_JSON = "application/json";

    private static final List<String> allowedPaths = new ArrayList<>();
    static {
        allowedPaths.add("/api/bans/players");
        allowedPaths.add("/api/bans/ban/");
        allowedPaths.add("/api/users/");
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse res)
            throws IOException, ServletException {

        if (target.startsWith("/api/users/")) {
            String uuid = target.substring("/api/users/".length());
            UUID id;
            try {
                id = UUID.fromString(uuid);
            } catch (IllegalArgumentException e) {
                cors(res);
                res.setStatus(400);
                res.setContentType(APPLICATION_JSON);
                res.getWriter().print(JsonUtils.createJsonError(400, "Invalid UUID: " + e.toString()));
                baseRequest.setHandled(true);
                return;
            }
            String name = BanManager.getInstance().lookupUsername(id);
            if (name == null) {
                cors(res).setStatus(404);
                res.setContentType(APPLICATION_JSON);
                res.getWriter().write(JsonUtils.createJsonError(404, "No user for player with UUID: " + id.toString()).toString());
                baseRequest.setHandled(true);
                return;
            }
            JsonObject response = JsonUtils.createUsernameResponse(name, id);
            cors(res);
            res.setContentType(APPLICATION_JSON);
            res.getWriter().write(gson.toJson(response));
            baseRequest.setHandled(true);
        } else if (target.startsWith("/api/bans/players")) {
            try {
                ResultSet set = BanManager.getInstance().query("select PlayerUUID from bans;");

                HashMap<UUID, Integer> response = new HashMap<>();

                while (set.next()) {
                    UUID uuid = UUID.fromString(set.getString("PlayerUUID"));
                    int rowId = set.getRow();

                    if (response.containsKey(uuid) && response.get(uuid) < rowId)
                        response.put(uuid, rowId);
                    else if (!response.containsKey(uuid))
                        response.put(uuid, rowId);
                }
                JsonArray results = new JsonArray();
                for (Map.Entry<UUID, Integer> entry : response.entrySet()) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("uuid", entry.getKey().toString());
                    obj.addProperty("id", entry.getValue());
                    results.add(obj);
                }
                cors(res);
                res.setContentType(APPLICATION_JSON);
                res.getWriter().write(gson.toJson(results));
                baseRequest.setHandled(true);
            } catch (SQLException e) {
                cors(res);
                res.setContentType(APPLICATION_JSON);
                res.getWriter().write(JsonUtils.createJsonError(500, "Failed to fetch data from database: " + e.getLocalizedMessage()).toString());
                baseRequest.setHandled(true);
            }
        } else if (target.startsWith("/api/bans/player/")) {
            UUID uuid;

            try {
                uuid = UUID.fromString(target.substring("/api/bans/player/".length()));
            } catch (IllegalArgumentException e) {
                cors(res);
                res.setStatus(400);
                res.setContentType(APPLICATION_JSON);
                res.getWriter().print(JsonUtils.createJsonError(400, "Invalid UUID: " + e.toString()));
                baseRequest.setHandled(true);
                return;
            }

            List<BanEntry> bans = BanManager.getInstance().fetchBans(uuid);

            List<JsonObject> result = bans.stream().map(JsonUtils::serializeBanEntry).collect(Collectors.toList());
            JsonArray ret = new JsonArray();
            result.forEach(ret::add);

            cors(res);
            res.setContentType(APPLICATION_JSON);
            res.getWriter().write(gson.toJson(ret));
            baseRequest.setHandled(true);
        } else {
            cors(res);
            res.setStatus(404);
            res.setContentType(APPLICATION_JSON);
            res.getWriter().write(JsonUtils.createJsonError(404, "Invalid API endpoint.").toString());
            baseRequest.setHandled(true);
        }
    }
}
