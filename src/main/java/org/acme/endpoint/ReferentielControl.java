package org.acme.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.data.RedisNotification;
import org.acme.data.RedisOperation;

@Path("/referentiel")
public class ReferentielControl {

    @Inject
    private RedisOperation redis;

    @Inject
    private RedisNotification notif;

    @GET
    public String getData() {
        return redis.get("clef", "champs");
    }

    @Path("sub")
    @POST
    public void subscribe() {
        notif.subscribe("clef");
    }

    @Path("check")
    @POST
    public String check() {
        return redis.getIndex("c8l2", 8, 16);
    }
}
