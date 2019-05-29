package com.lightbox.html;

import com.lightbox.html.vertx.NoCorsRestVerticle;
import com.lightbox.html.vertx.ServerSideRenderingVertx;
import io.vertx.core.Vertx;

/**
 * Main to show endpoint without CORS headers.
 */
public final class NoCorsMain {

    /**
     * Ctor.
     */
    private NoCorsMain() {
    }

    /**
     * Main.
     *
     * @param args Params
     */
    public static void main(final String[] args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ServerSideRenderingVertx());
        vertx.deployVerticle(new NoCorsRestVerticle());
    }
}