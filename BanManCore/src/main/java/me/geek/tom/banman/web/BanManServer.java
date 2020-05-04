package me.geek.tom.banman.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class BanManServer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("BanManWeb");

    private final Server server;

    public BanManServer(int port) {
        this.server = new Server(port);
        SessionHandler sessions = new SessionHandler(new HashSessionManager());
        sessions.setHandler(new ApiHandler());
        this.server.setSessionIdManager(new HashSessionIdManager());
        this.server.setHandler(sessions);
        this.server.setStopAtShutdown(true);
    }

    @Override
    public void run() {
        LOGGER.info("Starting API server...");
        Runtime.getRuntime().addShutdownHook(new Thread(this::end, "BanManServer shutdown handler"));
        new Thread(() -> {
            try {
                this.server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BanManServer listener thread.").start();
        LOGGER.info("API Server started!");
    }

    public void end() {
        LOGGER.info("API server stopping...");
        try {
            this.server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("API server has stopped!");
    }

    // Allow cross origin by setting the header.
    public static HttpServletResponse cors(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        return resp;
    }

}
