package main;

import main.model.ToDoItem;
import main.model.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {
    @Autowired
    ToDoItemRepository toDoItemRepository;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<ToDoItem> itemsIterable = toDoItemRepository.findAll();
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        for (ToDoItem item : itemsIterable){
            toDoItems.add(item);
        }
        model.addAttribute("items", toDoItems);

        return "index";
    }
}
