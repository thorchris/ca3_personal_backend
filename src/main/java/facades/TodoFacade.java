package facades;

import DTO.TodoDTO;
import entities.Todo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Thor Christensen
 */
public class TodoFacade {
    private static TodoFacade instance;
    private static EntityManagerFactory emf;

    public TodoFacade() { }
    
    public static TodoFacade getTodoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TodoFacade();
        }
        return instance;
    }
    
//    public List<TodoDTO> getTodos(){
//         EntityManager em = emf.createEntityManager(); 
//        try {
//            Query query = em.createQuery("SELECT t FROM TODO t");                 
//            List<Todo> TodoList = query.getResultList();
//            List<TodoDTO> todoListDTO = new ArrayList(); 
//            for (Todo todo : TodoList) {
//                todoListDTO.add(new TodoDTO(todo)); 
//            }
//            return todoListDTO; 
//        } finally {
//            em.close();
//        }  
//    }
    
        public List<TodoDTO> getTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query2 = em.createQuery("Select t from Todo t");
            List<Todo> movieList = query2.getResultList();
            List<TodoDTO> movieDTOList = new ArrayList();
            for (Todo movie : movieList) {
                TodoDTO movDTO = new TodoDTO(movie);
                movieDTOList.add(movDTO);    
            }
            return movieDTOList;
            
        } finally {
            em.close();
        }
    }
    
    public TodoDTO addTodo(String todoText) {
        EntityManager em = emf.createEntityManager();

        Todo todos = new Todo(todoText);

        try {
            em.getTransaction().begin();

            em.persist(todos);

            em.getTransaction().commit();
            return new TodoDTO(todos);
        } finally {
            em.close();
        }

    }
    
    
    public void populateDB(){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new Todo("Fix bilen"));
            em.persist(new Todo("Husk at købe ind"));
            em.persist(new Todo("Do whatever you want"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
}
