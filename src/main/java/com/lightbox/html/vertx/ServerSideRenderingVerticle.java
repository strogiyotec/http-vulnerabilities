package com.lightbox.html.vertx;

import com.lightbox.html.common.DeployVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.impl.CookieImpl;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.UUID;

/**
 * Render html in server side
 * using {@link io.vertx.ext.web.templ.ThymeleafTemplateEngine}.
 */
public final class ServerSideRenderingVerticle extends AbstractVerticle {

    /**
     * Server port.
     */
    private static final int PORT = 8080;

    /**
     * Engine.
     */
    @SuppressWarnings("checkstyle:LineLength")
    private final ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();

    /**
     * Ctor.
     */
    public ServerSideRenderingVerticle() {
        this.initEngine();
    }

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
        router.route().handler(CookieHandler.create());
        router.route(HttpMethod.GET, "/")
                .handler(event -> {
                    event.put("name", "Almas");
                    this.engine.render(event, "test", res -> {
                        if (res.succeeded()) {
                            event.response().end(res.result());
                        } else {
                            event.fail(res.cause());
                        }
                    });
                });
        router.route(HttpMethod.GET, "/nocors")
                .handler(
                        event ->
                                this.engine.render(
                                        event,
                                        "no-cors",
                                        res -> event.response().end(res.result())
                                )
                );
        router.route(HttpMethod.GET, "/cors")
                .handler(
                        event ->
                                this.engine.render(
                                        event,
                                        "with-cors",
                                        res -> event.response().end(res.result())
                                )
                );
        router.route(HttpMethod.GET, "/nocookie")
                .handler(
                        event ->
                        {
                            event.addCookie(new CookieImpl("JSESSIONID", UUID.randomUUID().toString()).setMaxAge(3600));
                            this.engine.render(
                                    event,
                                    "nocookie",
                                    res -> event.response().end(res.result())
                            );
                        }
                );
        router.route(HttpMethod.GET, "/withCookie")
                .handler(
                        event ->
                        {
                            event.addCookie(new CookieImpl("JSESSIONID", UUID.randomUUID().toString()).setMaxAge(3600));
                            this.engine.render(
                                    event,
                                    "withcookie",
                                    res -> event.response().end(res.result())
                            );
                        }
                );
        DeployVerticle.deploy(this.vertx, router, PORT);
    }

    /**
     * Configure template engine.
     */
    private void initEngine() {
        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("static/");
        classLoaderTemplateResolver.setSuffix(".html");
        this.engine.getThymeleafTemplateEngine().setTemplateResolver(classLoaderTemplateResolver);
    }
}
