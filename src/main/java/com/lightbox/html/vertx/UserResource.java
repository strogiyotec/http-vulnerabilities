package com.lightbox.html.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Rest resource for user
 */
public final class UserResource extends AbstractVerticle {

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        router.route(HttpMethod.GET, "/users")
                .handler(request -> {
                    final HttpServerResponse response = request.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json")
                            .setStatusCode(200)
                            .end(
                                    new JsonObject().put(
                                            "users",
                                            new JsonArray().add(new JsonObject().put("name", "Almas").put("age", 22))
                                    ).toString()
                            );
                });
        this.vertx.createHttpServer().requestHandler(router::accept).listen(9090);
        System.out.println("Rest client in port 9090 is started");
    }
}
