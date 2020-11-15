package facades;

import entities.Role;
import entities.Todo;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    public void deleteUser(String name) {
        EntityManager em = emf.createEntityManager(); 
        User user = em.find(User.class, name);

        if (user == null) {
            System.out.println("Der er sket en fejl");
        } else {

            try {
                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();
            } finally {
                em.close();
            }     
        }
    }
    
    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager(); 
        try {
            List<User> userList = em.createQuery("SELECT u from User u").getResultList();
            return userList; 
        } finally {
            em.close();
        }
    }
    
    
    public void addUser(String userName, String password){
        EntityManager em = emf.createEntityManager(); 
        User user = new User(userName, password); 
        
        try{
            em.getTransaction().begin();
            Role userRole = new Role("user");
            user.addRole(userRole);
            em.persist(user);
            em.getTransaction().commit();
        } finally{
            em.close(); 
        } 
    }

    public void populateDB() {
        EntityManager em = emf.createEntityManager();
        try {
            User user = new User("user", "kode123");
            User admin = new User("admin", "kodeord");
            User both = new User("user_admin", "kodetest");

            if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test")) {
                throw new UnsupportedOperationException("You have not changed the passwords");
            }

            em.getTransaction().begin();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
      
    

}
