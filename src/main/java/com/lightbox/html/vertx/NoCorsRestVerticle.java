package com.lightbox.html.vertx;

import com.lightbox.html.common.DeployVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Rest verticle that doesn't add CORS headers.
 */
public final class NoCorsRestVerticle extends AbstractVerticle {

    /**
     * Server port.
     */
    private static final int PORT = 9091;

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);

        router.route(HttpMethod.GET, "/withoutcors")
                .handler(handler -> handler.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .end(new JsonObject().put("message", "No cors").toString()));

        DeployVerticle.deploy(this.vertx, router, PORT);
    }
}
