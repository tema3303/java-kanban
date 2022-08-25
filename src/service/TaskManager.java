package service;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public interface TaskManager {
    void addTask(Task task);

    void addSubTask(SubTask subTask);

    void updateEpicStatus(Epic epic);

    void addEpic(Epic epic);

    void getAllTask();

    void getAllEpic();

    void getAllSubTask();

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubTask();

    void getIdTask(int idTask);

    void getIdEpic(int idEpic);

    void getIdSubTask(int idSubTask);

    void getEpicSubTask(int idEpic);

    void removeByIdTask(int id);

    void removeByIdSubTask(int id);

    void removeByIdEpic(int id);

    void updateTask(Task task, Integer i);

    void updateEpic(Epic epic, Integer i);

    void updateSubTask(SubTask subTask, Integer i);

    void getTaskHistory();
}