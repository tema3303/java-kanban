package Tests;

import constans.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.history.InMemoryHistoryManager;
import service.manager.InMemoryTaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.MAY;

class InMemoryHistoryManagerTest {
    private Task taskTest;
    private Epic epicTest;
    private SubTask subTaskTest;

    InMemoryHistoryManager historyManagerTest;

    @BeforeEach
    void initTaskHistory(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        historyManagerTest = new InMemoryHistoryManager();
        taskTest = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                LocalDateTime.of(2021,APRIL,2,3,0), 20);
        taskManager.addTask(taskTest);
        epicTest = new Epic("Epic 1", "Описание эпика 1");
        taskManager.addEpic(epicTest);
        subTaskTest = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,MAY,10,2,0),
                100, 1);
        taskManager.addSubTask(subTaskTest);
    }

    @AfterEach
    void deleteHistory(){
        List<Task> task = historyManagerTest.getHistory();
        for (Task tasks : task){
            historyManagerTest.remove(tasks.getId());
        }
    }

    @Test
    void addTest() {
        historyManagerTest.add(taskTest);
        historyManagerTest.add(subTaskTest);
        final List<Task> history = historyManagerTest.getHistory();
        Assertions.assertEquals(2, history.size(), "История не заполнилась.");
    }

    @Test
    void removeTest() {
        historyManagerTest.add(taskTest);
        Assertions.assertEquals(1, historyManagerTest.getHistory().size(),"Добавить не получилось");
        historyManagerTest.remove(taskTest.getId());
        Assertions.assertEquals(0, historyManagerTest.getHistory().size(),"Удалить не получилось");
        historyManagerTest.add(taskTest);
        historyManagerTest.add(subTaskTest);
        historyManagerTest.add(epicTest);
        historyManagerTest.remove(subTaskTest.getId());
        Assertions.assertFalse(historyManagerTest.getHistory().contains(subTaskTest),"Удалить cабтаск не получилось");
    }

    @Test
    void getHistoryTest() {
        Assertions.assertEquals(0,historyManagerTest.getHistory().size(),"Размер истории содержит элементы");
        historyManagerTest.add(taskTest);
        historyManagerTest.add(epicTest);
        historyManagerTest.add(subTaskTest);
        List<Task> history = historyManagerTest.getHistory();
        Assertions.assertEquals(3,history.size(),"Размер истории cобрался некоректно");
        historyManagerTest.add(taskTest);
        history = historyManagerTest.getHistory();
        Assertions.assertEquals(3,history.size(),"Дублирование в истории");
    }
}