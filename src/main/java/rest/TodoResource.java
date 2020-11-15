package rest;

import DTO.TodoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Todo;
import facades.TodoFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author Thor Christensen
 */
  
    @Path("todos")
    public class TodoResource {
        private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        private static final TodoFacade tf = TodoFacade.getTodoFacade(EMF);
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        
        
        
        @GET
        @Path("all")
        @Produces({MediaType.APPLICATION_JSON})
        public String getAllTodos() {
            EntityManager em = EMF.createEntityManager();
            try {
                TodoFacade tf = TodoFacade.getTodoFacade(EMF);
                List<TodoDTO> todoList = tf.getTodos();
                return GSON.toJson(todoList);
            } finally {
                em.close();
            }
        }
        
        @POST
        @Produces({MediaType.APPLICATION_JSON})
        @Consumes({MediaType.APPLICATION_JSON})
        @Path("add")
        public String addTodos(String todo) {
            Todo t = GSON.fromJson(todo, Todo.class);
            TodoDTO todoDTO = new TodoDTO(t);
            return GSON.toJson(tf.addTodo(todoDTO.getTodoText()));

        }
        
        @GET
        @Path("/populate")
        @Produces({MediaType.APPLICATION_JSON})
        public String populate() {
            tf.populateDB();
            return "{\"msg\":\"3 rows added\"}";
    }

    }
