package main;

import main.model.ToDoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToDoList {
    private static HashMap<Integer, ToDoItem> toDoList = new HashMap<>();

    public static List<ToDoItem> getToDos(){
        List<ToDoItem> list = new ArrayList<>();
        list.addAll(toDoList.values());
        return list;
    }

    public static ToDoItem getItem(int id){
        return toDoList.get(id);
    }

    public static int addToDo(ToDoItem toDoItem){
        toDoList.put(toDoItem.getId(), toDoItem);
        return toDoItem.getId();
    }

    public static void deleteItem(int id){
        toDoList.remove(id);
    }
}
