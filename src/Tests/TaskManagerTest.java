package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.TaskManager;
import tasks.Task;

import java.util.List;

import static constans.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected void initTask(){
        //taskManager.addTask(new Task(1));
        //taskManager.addTask(new Task(2));
    }


    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        taskManager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = taskManager.getIdTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getTasksTest(){
        taskManager.addTask(new Task(3));
        List<Task> tasks = taskManager.getAllTask();
        Assertions.assertEquals(1,tasks.size());

    }
}
