package DTO;

import entities.Todo;

/**
 *
 * @author Thor Christensen
 */
public class TodoDTO {
    private String todoText;
    
    
     public TodoDTO(Todo todo){
         this.todoText = todo.getTodoText();
     }
     
     public String getTodoText(){
         return todoText;
     }
     
     public void setTodText(String todoText){
         this.todoText = todoText;
     }
}
