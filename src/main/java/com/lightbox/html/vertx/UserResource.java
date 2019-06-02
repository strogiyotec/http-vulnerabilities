package com.lightbox.html.vertx;

import com.lightbox.html.common.DeployVerticle;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Rest resource for user.
 */
public final class UserResource extends AbstractVerticle {

    /**
     * Server port.
     */
    private static final int PORT = 9090;

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        router.route().handler(BodyHandler.create());
        this.users(router);
        this.corsOptions(router);
        this.saveUser(router);
        this.noCredentials(router);
        this.credentials(router);

        DeployVerticle.deploy(this.vertx, router, PORT);
    }

    /**
     * Endpoint to save new user
     *
     * @param router Vertx router
     */
    private void saveUser(final Router router) {
        router.route(HttpMethod.POST, "/users")
                .handler(request -> {
                    final HttpServerResponse response = request.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json")
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), request.request().getHeader("Origin"))
                            .end(
                                    new JsonObject()
                                            .put("users", request.getBodyAsJson())
                                            .toString()
                            );
                });
    }

    /**
     * Init get users endpoint.
     *
     * @param router Vertx Router
     */
    private void users(final Router router) {
        router.route(HttpMethod.GET, "/users")
                .handler(event -> {
                    final HttpServerResponse response = event.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                            //add control origin
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), event.request().getHeader("Origin"))
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(
                                    new JsonObject()
                                            .put("users",
                                                    new JsonArray()
                                                            .add(
                                                                    new JsonObject().put("name", "Almas").put("age", 22)
                                                            ))
                                            .toString()
                            );
                });
    }

    /**
     * Credentials not allowed response.
     *
     * @param router Vertx Router
     */
    private void noCredentials(final Router router) {
        router.route(HttpMethod.GET, "/nocred")
                .handler(event -> {
                    final HttpServerResponse response = event.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                            //add control origin
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), event.request().getHeader("Origin"))
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(
                                    new JsonObject()
                                            .put("users",
                                                    new JsonArray()
                                                            .add(
                                                                    new JsonObject().put("name", "Almas").put("age", 22)
                                                            ))
                                            .toString()
                            );
                });
    }

    /**
     * Credentials allowed response.
     *
     * @param router Vertx Router
     */
    private void credentials(final Router router) {
        router.route(HttpMethod.GET, "/cred")
                .handler(event -> {
                    final HttpServerResponse response = event.response();
                    response.putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
                            //add control origin
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), event.request().getHeader("Origin"))
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString())
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(
                                    new JsonObject()
                                            .put("users",
                                                    new JsonArray()
                                                            .add(
                                                                    new JsonObject().put("name", "Almas").put("age", 22)
                                                            ))
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
                .handler(event -> {
                    final HttpServerResponse response = event.response();
                    response.setStatusCode(HttpResponseStatus.OK.code())
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), event.request().getHeader("Origin"))
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS.toString(), "POST")
                            .putHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE.toString(), "600")
                            .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS.toString(), HttpHeaders.CONTENT_TYPE.toString())
                            .end();
                });
    }
}
