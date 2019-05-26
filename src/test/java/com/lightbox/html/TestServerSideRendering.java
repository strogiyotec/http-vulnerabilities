package com.lightbox.html;

import com.lightbox.html.vertx.ServerSideRenderingVertx;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public final class TestServerSideRendering {

    /**
     * Vertx instance
     */
    private Vertx vertx;

    /**
     * Init vertx
     */
    @Before
    public void init(TestContext context) {
        this.vertx = Vertx.vertx();

        this.vertx.deployVerticle(
                new ServerSideRenderingVertx(),
                context.asyncAssertSuccess()
        );
    }

    /**
     * Stop vertx
     */
    @After
    public void stop(TestContext context) {
        this.vertx.close(context.asyncAssertSuccess());
    }

    /**
     * Test ServerSide html render
     *
     * @param context TestContext
     */
    @Test
    public void testSimpleHtmlRender(TestContext context) {
        final Async async = context.async();

        this.vertx.createHttpClient().getNow(8080, "localhost", "/", resp -> {
            resp.handler(body -> {
                context.assertTrue(body.toString().contains("Almas"));
                async.complete();
            });
        });
    }
}
