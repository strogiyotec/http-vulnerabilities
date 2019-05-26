package com.lightbox.html;

import com.lightbox.html.vertx.UserResource;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public final class TestUserResource {

    /**
     * Vertx instance.
     */
    private Vertx vertx;

    /**
     * Init vertx.
     */
    @Before
    public void init(final TestContext context) {
        this.vertx = Vertx.vertx();

        this.vertx.deployVerticle(
                new UserResource(),
                context.asyncAssertSuccess()
        );
    }

    /**
     * Stop vertx
     */
    @After
    public void stop(final TestContext context) {
        this.vertx.close(context.asyncAssertSuccess());
    }

    /**
     * Test UsersResource response
     *
     * @param context TestContext
     */
    @Test
    public void testUsersAsJson(final TestContext context) {
        final Async async = context.async();

        this.vertx.createHttpClient().getNow(9090, "localhost", "/users", resp -> resp.handler(body -> {
            final JsonArray users = new JsonObject(body.toString()).getJsonArray("users");
            context.assertTrue(users.size() == 1);
            context.assertEquals(users.getJsonObject(0), new JsonObject().put("name", "Almas").put("age", 22));

            async.complete();
        }));
    }
}
