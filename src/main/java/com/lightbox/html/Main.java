package com.lightbox.html;

import com.lightbox.html.vertx.ServerSideRenderingVertx;
import com.lightbox.html.vertx.UserResource;
import io.vertx.core.Vertx;

public final class Main {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new ServerSideRenderingVertx());
        Vertx.vertx().deployVerticle(new UserResource());
    }
}
