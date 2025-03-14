package info.jab.ms.controller;

import info.jab.ms.dto.ActorDTO;
import info.jab.ms.service.ActorService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/v1")
public class ActorController {

    private final ActorService actorService;

    @Inject
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GET
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ActorDTO> getFirstTenActors() {
        return actorService.getFirstTenActors();
    }
} 