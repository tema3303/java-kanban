package service.manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);

    void addSubTask(SubTask subTask);

    void updateEpicStatus(Epic epic);

    void updateEpicDuration(Epic epic);

    void updateEpicStartTime(Epic epic);

    void updateEpicEndTime(Epic epic);

    void addEpic(Epic epic);

    List getAllTask();

    List getAllEpic();

    List getAllSubTask();

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubTask();

    Task getIdTask(int idTask);

    Task getIdEpic(int idEpic);

    Task getIdSubTask(int idSubTask);

    List getEpicSubTask(int idEpic);

    void removeByIdTask(int id);

    void removeByIdSubTask(int id);

    void removeByIdEpic(int id);

    void updateTask(Task task, Integer i);

    void updateEpic(Epic epic, Integer i);

    void updateSubTask(SubTask subTask, Integer i);

    List <Task> getPrioritizedTasks();

    boolean isNoIntersections(Task task);

    List<Task> getTaskHistory();
}