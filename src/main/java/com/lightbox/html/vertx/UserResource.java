package com.lightbox.html.vertx;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Rest resource for user.
 */
public final class UserResource extends AbstractVerticle {

    /**
     * Server port.
     */
    private static final int PORT = 9090;

    /**
     * List of users.
     */
    private final JsonArray users =
            new JsonArray()
                    .add(
                            new JsonObject().put("name", "Almas").put("age", 22)
                    );

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        this.users(router);
        this.corsOptions(router);
        this.saveUser(router);

        this.vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(PORT, event -> {
                    if (event.failed()) {
                        throw new RuntimeException(event.cause());
                    } else {
                        System.out.println("Rest client in port 9090 is started");
                    }
                });
    }

    /**
     * Init get users endpoint.
     *
     * @param router Vertx Router
     */
    private void users(final Router router) {
        router.route(HttpMethod.GET, "/users")
                .handler(request -> {
                    final HttpServerResponse response = request.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json")
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), request.request().getHeader("Origin"))
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(
                                    new JsonObject()
                                            .put("users", this.users)
                                            .toString()
                            );
                });
    }

    /**
     * Endpoint to save new user
     *
     * @param router Vertx router
     */
    private void saveUser(final Router router) {
        router.route(HttpMethod.POST, "/users")
                .handler(request -> {
                  //  this.users.add(request.getBodyAsJson());
                    final HttpServerResponse response = request.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json")
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(
                                    new JsonObject()
                                            .put("users", this.users)
                                            .toString()
                            );
                });
    }

    /**
     * Init options request for CORS.
     *
     * @param router Vertx Router
     */
    private void corsOptions(final Router router) {
        router.route(HttpMethod.OPTIONS, "/users")
                .handler(request -> {
                    final HttpServerResponse response = request.response();
                    response.setStatusCode(HttpResponseStatus.OK.code())
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(),request.request().getHeader("Origin"))
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS.toString(),"POST")
                            .putHeader(HttpHeaders.CACHE_CONTROL.toString(),"max-age=3600")
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS.toString(),HttpHeaders.CONTENT_TYPE.toString())
                            .end();
                });
    }
}
