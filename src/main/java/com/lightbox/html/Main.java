package com.lightbox.html;

import com.lightbox.html.vertx.ServerSideRenderingVerticle;
import com.lightbox.html.vertx.UserResource;
import io.vertx.core.Vertx;

/**
 * Entry point.
 */
public final class Main {

    /**
     * Ctor.
     */
    private Main() {
    }

    /**
     * Main.
     *
     * @param args Params
     */
    public static void main(final String[] args) {
        Vertx.vertx().deployVerticle(new ServerSideRenderingVerticle());
        Vertx.vertx().deployVerticle(new UserResource());
    }
}
