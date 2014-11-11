package org.ovirtChina.enginePlugin.vmBackupScheduler.restResource;

import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.ovirtChina.enginePlugin.vmBackupScheduler.common.Task;
import org.ovirtChina.enginePlugin.vmBackupScheduler.dao.DbFacade;

@Path("/tasks")
public class TasksResource {

    @POST
    public Response add(Task task) {
        return addOrUpdateTask(task);
    }

    @GET
    @Path("{id}")
    public Task getTaskByID(@PathParam("id") String id) {
        return DbFacade.getInstance().getTaskDAO().get(UUID.fromString(id));
    }

    @PUT
    public Response updateTask(Task task) {
        return addOrUpdateTask(task);
    }

    @DELETE
    @Path("{id}")
    public Response removeTask(@PathParam("id") String id) {
        DbFacade.getInstance().getTaskDAO().delete(UUID.fromString(id));
        return Response.status(Response.Status.OK).build();
    }

    private Response addOrUpdateTask(Task task) {
        Task tmpTask = DbFacade.getInstance().getTaskDAO().get(task.getVmID());
        if (tmpTask != null) {
            DbFacade.getInstance().getTaskDAO().update(task);
        } else {
            DbFacade.getInstance().getTaskDAO().save(task);
        }
        return Response.status(Response.Status.OK).build();
    }
}
