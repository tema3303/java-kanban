package Tests;

import constans.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static constans.Status.*;
import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected void initTask(){
        taskManager.addTask(new Task("Задача 1", "Описание задачи 1", Status.NEW,
                LocalDateTime.of(2021,APRIL,2,3,0), 20));//0
        taskManager.addTask(new Task("Задача 2", "Описание задачи 2", Status.DONE,
                LocalDateTime.of(2021,DECEMBER,2,4,0),
                120));//1
        taskManager.addEpic(new Epic("Epic 1", "Описание эпика 1"));//2
        taskManager.addSubTask((new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,MAY,10,2,0),
                100, 2)));//3
        taskManager.addSubTask((new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,
                LocalDateTime.of(2020,FEBRUARY,2,2,0),
                120, 2)));//4
    }

    @Test
    void addNewTaskTest() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW,
                LocalDateTime.of(2021,NOVEMBER,2,3,0), 20);
        taskManager.addTask(task);
        final int taskId = task.getId();
        final Task savedTask = taskManager.getIdTask(taskId);
        final List<Task> tasks = taskManager.getAllTask();

        assertAll(
                () -> assertNotNull(savedTask, "Задача не найдена."),
                () -> assertEquals(task, savedTask, "Задачи не совпадают."),
                () -> assertNotNull(tasks, "Задачи на возвращаются."),
                () -> assertEquals(3, tasks.size(), "Неверное количество задач."),
                () -> assertEquals(task, tasks.get(2), "Задачи не совпадают.")
        );
    }

    @Test
    void addNewSubTaskTest() {
        Epic epic = new Epic("Epic 2", "Описание эпика 2");
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("Subtask 3", "Описание подзадачи 1", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,NOVEMBER,10,2,0),
                100, epic.getId());
        taskManager.addSubTask(subTask);
        final int subTaskId = subTask.getId();
        final Task savedSub = taskManager.getIdSubTask(subTaskId);
        final List<SubTask> subTasks = taskManager.getAllSubTask();

        assertAll(
                () -> assertNotNull(savedSub, "Задача не найдена."),
                () -> assertEquals(subTask, savedSub, "Задачи не совпадают."),
                () -> assertNotNull(subTasks, "Задачи на возвращаются."),
                () -> assertEquals(3, subTasks.size(), "Неверное количество задач."),
                () -> assertEquals(subTask, subTasks.get(2), "Задачи не совпадают.")
        );
    }

    @Test
    void addNewEpicTest() {
        Epic epic = new Epic("Epic Test", "Описание эпика Test");
        taskManager.addEpic(epic);
        final int epicId = epic.getId();
        final Task savedEpic= taskManager.getIdEpic(epicId);
        final List<Epic> epics = taskManager.getAllEpic();

        assertAll(
                () -> assertNotNull(savedEpic, "Задача не найдена."),
                () -> assertEquals(epic, savedEpic, "Задачи не совпадают."),
                () -> assertNotNull(epics, "Задачи на возвращаются."),
                () -> assertEquals(2,epics.size(), "Неверное количество задач."),
                () -> assertEquals(epic, epics.get(1), "Задачи не совпадают.")
        );
    }

    @Test
    void getAllTasksTest(){
        taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description", NEW,
                LocalDateTime.of(2021,NOVEMBER,2,3,0), 20));
        List<Task> tasks = taskManager.getAllTask();
        Assertions.assertEquals(3,tasks.size());
    }

    @Test
    void getAllSubTask(){
        Epic epic = new Epic("Test epic", "Test epic description");
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("Test subtask", "Test subtask description", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,NOVEMBER,10,2,0),
                100, epic.getId());
        taskManager.addSubTask(subTask);
        List<SubTask> subTasks = taskManager.getAllSubTask();
        Assertions.assertEquals(3,subTasks.size());
    }

    @Test
    void getAllEpicTest(){
        taskManager.addEpic(new Epic("Test epic", "Test epic description"));
        List<Task> tasks = taskManager.getAllEpic();
        Assertions.assertEquals(2,tasks.size());
    }

    @Test
    void removeAllTasksTest(){
        taskManager.removeAllTask();
        Assertions.assertEquals(new ArrayList<>(),taskManager.getAllTask(),"Список содержит элементы");
    }

    @Test
    void removeAllEpicTest(){
        taskManager.removeAllEpic();

        assertAll(
                () -> assertEquals(new ArrayList<>(),taskManager.getAllEpic(),"Список эпиков содержит элементы"),
                () -> assertEquals(new ArrayList<>(),taskManager.getAllSubTask(),"Список сабтасков содержит элементы")
        );
    }

    @Test
    void removeAllSubTaskTest(){
        taskManager.removeAllSubTask();
        Assertions.assertEquals(new ArrayList<>(),taskManager.getAllSubTask(),"Список содержит элементы");
    }

    @Test
    void getIdTaskTest(){
        assertAll(
                () -> assertNotNull(taskManager.getIdTask(0),"Задача не найдена"),
                () -> assertNull(taskManager.getIdTask(10),"Список сабтасков содержит элементы")
        );
    }

    @Test
    void getIdSubTaskTest(){
        assertAll(
                () -> assertNotNull(taskManager.getIdSubTask(3),"Задача не найдена"),
                () -> assertNull(taskManager.getIdTask(10),"Список сабтасков содержит элементы")
        );
    }

    @Test
    void getIdEpicTest(){
        assertAll(
                () -> assertNotNull(taskManager.getIdEpic(2),"Задача не найдена"),
                () -> assertNull(taskManager.getIdEpic(1),"Список сабтасков содержит элементы")
        );
    }

    @Test
    void getEpicSubTaskTest(){
        assertAll(
                () -> assertEquals(taskManager.getEpicSubTask(2).size(),2,"Список сабтасков содержит элементы")
        );
    }

    @Test
    void removeByIdTaskTest(){
        taskManager.removeByIdTask(0);
        assertAll(
                () -> assertNull(taskManager.getIdTask(0),"Задача не удалена")
        );
    }

    @Test
    void removeByIdEpicTest(){
        taskManager.removeByIdEpic(2);
        assertAll(
                () -> assertNull(taskManager.getIdEpic(2),"Эпик не удален"),
                () -> assertEquals(taskManager.getEpicSubTask(2).size(),0,"Подзадачи не удалены")
        );
    }

    @Test
    void removeByIdSubTaskTest(){
        taskManager.removeByIdSubTask(3);
        assertAll(
                () -> assertNull(taskManager.getIdSubTask(3),"Задача не удалена")
        );
    }

    @Test
    void updateTaskTest(){
        Task task20 = new Task("Test addNewTask", "Test addNewTask description", NEW,
                LocalDateTime.of(2025,NOVEMBER,2,3,0), 20);
        taskManager.addTask(task20);
        taskManager.updateTask(task20,1);
        assertAll(
                () -> assertEquals(taskManager.getIdTask(1),task20,"Задача не обновлена")
        );
    }

    @Test
    void updateEpicTest(){
        Epic epic = new Epic("Epic Test", "Описание эпика Test");
        taskManager.addEpic(epic);
        taskManager.updateEpic(epic,2);
        assertAll(
                () -> assertEquals(taskManager.getIdEpic(2),epic,"Эпик не обновлен")
        );
    }

    @Test
    void updateSubTaskTest(){
        Epic epic = new Epic("Epic 2", "Описание эпика 2");
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("Subtask 3", "Описание подзадачи 1", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,NOVEMBER,10,2,0),
                100, epic.getId());
        taskManager.addSubTask(subTask);
        taskManager.updateSubTask(subTask,4);
        assertAll(
                () -> assertEquals(taskManager.getIdSubTask(4),subTask,"Задача не обновлена")
        );
    }

    @Test
    void updateEpicStatusTest(){
        Epic epic = (Epic) taskManager.getIdEpic(2);
        SubTask subTask = new SubTask("Subtask 1", "Описание подзадачи 1", DONE ,
                LocalDateTime.of(2025,MAY,10,2,0),
                100, 2);
        taskManager.addSubTask(subTask);
        taskManager.updateSubTask(subTask,3);
        Epic epic2 = new Epic("Epic 3", "Описание эпика 2");
        taskManager.addEpic(epic2);
        Epic epic3 = new Epic("Epic 4", "Описание эпика 2");
        taskManager.addEpic(epic3);
        SubTask subTask4 = new SubTask("Subtask 10", "Описание подзадачи 1", NEW ,
                LocalDateTime.of(2024,MAY,10,2,0),
                100, epic3.getId());
        taskManager.addSubTask(subTask4);
        SubTask subTask5 = new SubTask("Subtask 11", "Описание подзадачи 1", NEW ,
                LocalDateTime.of(2027,MAY,10,2,0),
                100, epic3.getId());
        taskManager.addSubTask(subTask5);
        taskManager.addEpic(epic3);
        Epic epic4 = new Epic("Epic 4", "Описание эпика 2");
        taskManager.addEpic(epic4);
        SubTask subTask6 = new SubTask("Subtask 10", "Описание подзадачи 1", NEW ,
                LocalDateTime.of(1993,MAY,10,2,0),
                100, epic4.getId());
        taskManager.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("Subtask 10", "Описание подзадачи 1", DONE ,
                LocalDateTime.of(1995,MAY,10,2,0),
                100, epic4.getId());
        taskManager.addSubTask(subTask7);
        Epic epic5 = new Epic("Epic 4", "Описание эпика 2");
        taskManager.addEpic(epic5);
        SubTask subTask8 = new SubTask("Subtask 10", "Описание подзадачи 1", IN_PROGRESS ,
                LocalDateTime.of(1998,MAY,10,2,0),
                100, epic5.getId());
        taskManager.addSubTask(subTask8);
        SubTask subTask9 = new SubTask("Subtask 10", "Описание подзадачи 1", DONE ,
                LocalDateTime.of(1999,MAY,10,2,0),
                100, epic5.getId());
        taskManager.addSubTask(subTask9);
        assertAll(
                () -> assertEquals(epic.getStatus(),DONE,"Статус не обновлен(Все сабстаки DONE)"),
                () -> assertEquals(epic2.getStatus(),NEW,"Статус не обновлен (Нет сабстаков)"),
                () -> assertEquals(epic3.getStatus(),NEW,"Статус не обновлен(Все новые)"),
                () -> assertEquals(epic4.getStatus(),IN_PROGRESS,"Статус не обновлен(NEW+DONE)"),
                () -> assertEquals(epic4.getStatus(),IN_PROGRESS,"Статус не обновлен(NEW+DONE)")
        );

    }

    @Test
    void updateEpicDurationTest(){
        Epic epic = (Epic) taskManager.getIdEpic(2);
        assertAll(
                () -> assertEquals(epic.getDuration(),220,"Время не совпадает")
        );
    }

    @Test
    void updateEpicStartTimeTest(){
        Epic epic = (Epic) taskManager.getIdEpic(2);
        assertAll(
                () -> assertEquals(epic.getStartTime(),LocalDateTime.of(2020,FEBRUARY,2,2,0),"Время не совпадает")
        );
    }

    @Test
    void updateEpicEndTimeTest(){
        Epic epic = (Epic) taskManager.getIdEpic(2);
        assertAll(
                () -> assertEquals(epic.getEndTime(),LocalDateTime.of(2021,MAY,10,3,40),"Время не совпадает")
        );
    }

    @Test
    void getPrioritizedTasksTest(){
        List<Task> expectedList = List.of(
                taskManager.getIdSubTask(4),
                taskManager.getIdTask(0),
                taskManager.getIdSubTask(3),
                taskManager.getIdTask(1));
        List<Task> priorityList = taskManager.getPrioritizedTasks();
        Assertions.assertEquals(expectedList,priorityList,"Приоритеты не совпадаеют");
    }

    @Test
    void isNoIntersectionsTest(){
         Task task4 =  new Task("Задача 1", "Описание задачи 1", Status.NEW,
                LocalDateTime.of(2021,APRIL,2,3,10), 120);
        taskManager.addTask(task4);
        SubTask subTask = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS ,
                LocalDateTime.of(2021,MAY,10,2,0),
                100, 2);
        taskManager.addSubTask(subTask);
        assertAll(
                () -> assertFalse(taskManager.getAllTask().contains(task4),"Задача не должна быть добвлена"),
                () -> assertFalse(taskManager.getAllSubTask().contains(subTask),"Задача не должна быть добвлена")
        );
    }
}