package com.lightbox.html;

import com.lightbox.html.vertx.ServerSideRenderingVertx;
import io.vertx.core.Vertx;

public final class Main {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new ServerSideRenderingVertx());
    }
}
