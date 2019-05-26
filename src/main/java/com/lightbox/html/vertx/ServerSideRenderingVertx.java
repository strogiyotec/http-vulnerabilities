package com.lightbox.html.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Render html in server side
 * using {@link io.vertx.ext.web.templ.ThymeleafTemplateEngine}.
 */
public final class ServerSideRenderingVertx extends AbstractVerticle {

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
    public ServerSideRenderingVertx() {
        this.initEngine();
    }

    @Override
    public void start() {
        final Router router = Router.router(this.vertx);
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
        this.vertx.createHttpServer().requestHandler(router::accept).listen(PORT, event -> {
            if (event.failed()) {
                throw new RuntimeException(event.cause());
            }
        });
        System.out.println("Server is started at port 8080");
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
