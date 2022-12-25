package service.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import service.file.FileBackedTasksManager;
import service.manager.Managers;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.lang.reflect.Type;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    Gson gson;
    KVTaskClient kvTaskClient;
    HttpTaskManager inMemoryHistoryManager = new HttpTaskManager();

    public HttpTaskManager() {
        super(null);
        gson = Managers.getGson();
        this.kvTaskClient = new KVTaskClient();
        this.loadFromServer();
    }

    public void loadFromServer() {
        Type tasksType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(kvTaskClient.load("task"), tasksType);
        if (tasks != null) {
            tasks.forEach(task -> {
                int id = task.getId();
                this.tasks.put(id, task);
                this.set.add(task);
                if (id > generator) {
                    generator = id;
                }
            });
        }

        Type subtasksType = new TypeToken<List<SubTask>>() {
        }.getType();
        List<SubTask> subtasks = gson.fromJson(kvTaskClient.load("subtask"), subtasksType);
        if (subtasks != null) {
            subtasks.forEach(subtask -> {
                int id = subtask.getId();
                this.subTasks.put(id, subtask);
                this.set.add(subtask);
                if (id > generator) {
                    generator = id;
                }
            });
        }

        Type epicsType = new TypeToken<List<Epic>>() {
        }.getType();
        List<Epic> epics = gson.fromJson(kvTaskClient.load("epic"), epicsType);
        if (epics != null) {
            epics.forEach(epic -> {
                int id = epic.getId();
                this.epics.put(id, epic);
                this.set.add(epic);
                if (id > generator) {
                    generator = id;
                }
            });
        }

        Type historyType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> history = gson.fromJson(kvTaskClient.load("history"), historyType);
        if (history != null) {
            for (Task task : history) {
                inMemoryHistoryManager.addTask(task);
            }
        }
    }

    @Override
    protected void save() {
        String tasks = gson.toJson(getAllTask());
        kvTaskClient.put("task", tasks);
        String epics = gson.toJson(getAllEpic());
        kvTaskClient.put("epic", epics);
        String sub = gson.toJson(getAllSubTask());
        kvTaskClient.put("subtask", sub);
        String history = gson.toJson((getTaskHistory()));
        kvTaskClient.put("history", history);
    }
}