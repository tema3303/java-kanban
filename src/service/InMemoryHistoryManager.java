package service;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static List<Task> history = new ArrayList<>();
    private static final int LIST_LENGTH = 10;

    @Override
    public void add(Task task){
        if(history.size()<LIST_LENGTH){
            history.add(task);
        } else{
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory(){
        return history;
    }
}