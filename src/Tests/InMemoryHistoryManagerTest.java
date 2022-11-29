package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import tasks.Task;

import java.util.List;

import static constans.Status.NEW;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest <T extends InMemoryHistoryManager> {

    protected T historyManager;

    protected void initTask(){
        historyManager.add(new Task(1));
        historyManager.add(new Task(2));
    }



    @Test
    void add() {
        historyManager.add(new Task("Test addNewTask", "Test addNewTask description", NEW));
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove() {
    }

    @Test
    void getHistory() {
    }
}