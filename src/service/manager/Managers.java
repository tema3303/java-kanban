package service.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.file.FileBackedTasksManager;
import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;
import service.http.HttpTaskManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager(new File("recourses/task.csv"));
    }

    public static TaskManager getDefaultHttp(){
        return new HttpTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}