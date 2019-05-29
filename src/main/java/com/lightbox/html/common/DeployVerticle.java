package com.lightbox.html.common;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Deploy verticle with routes on given port.
 */
public final class DeployVerticle {

    /**
     * Ctor.
     */
    private DeployVerticle() {
    }

    /**
     * Deploy given vertx.
     *
     * @param vertx  Vert.x instance
     * @param router Endpoints router
     * @param port   Port to use for deployment
     */
    public static void deploy(final Vertx vertx, final Router router, final int port) {
        vertx.createHttpServer().requestHandler(router::accept).listen(port, event -> {
            if (event.failed()) {
                throw new RuntimeException(event.cause());
            } else {
                System.out.printf("Server is started at port %d\n", port);
            }
        });
    }
}
