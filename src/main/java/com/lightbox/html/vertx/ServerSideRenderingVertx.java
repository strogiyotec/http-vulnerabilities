package com.lightbox.html.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Render html in server side using {@link io.vertx.ext.web.templ.ThymeleafTemplateEngine}
 */
public final class ServerSideRenderingVertx extends AbstractVerticle {

    /**
     * Engine
     */
    private final ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();

    public ServerSideRenderingVertx() {
        this.initEngine();
    }

    @Override
    public void start() throws Exception {
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
        this.vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        System.out.println("Server is started at port 8080");
    }

    /**
     * Configure template engine
     */
    private void initEngine() {
        final ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("static/");
        classLoaderTemplateResolver.setSuffix(".html");
        this.engine.getThymeleafTemplateEngine().setTemplateResolver(classLoaderTemplateResolver);
    }
}
