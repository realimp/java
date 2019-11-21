package main;

import main.model.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoItemController {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @GetMapping("/to-do-items")
    public List<ToDoItem> list(){
        Iterable<ToDoItem> toDoItems = toDoItemRepository.findAll();
        List<ToDoItem> items = new ArrayList<>();
        for (ToDoItem item : toDoItems){
            items.add(item);
        }
        return items;
    }
    @GetMapping("/to-do-items/{id}")
    public ResponseEntity getItem(@PathVariable int id){
        Optional<ToDoItem> item = toDoItemRepository.findById(id);
        if (!item.isPresent()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(item.get(), HttpStatus.OK);
    }

    @PostMapping("/to-do-items")
    public int addToDoItem(@RequestBody ToDoItem toDoItem){
        ToDoItem newToDoItem = toDoItemRepository.save(toDoItem);
        return newToDoItem.getId();
    }

    @DeleteMapping("/to-do-items/{id}")
    public void deleteToDoItem(@PathVariable int id){
        toDoItemRepository.deleteById(id);
    }
}
