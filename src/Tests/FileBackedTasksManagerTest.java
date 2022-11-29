package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import service.FileBackedTasksManager;
import service.InMemoryTaskManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File file;

    @BeforeEach
    void setUp(){
        file = new File("recourses/task.csv");
        taskManager = new FileBackedTasksManager(file);
        initTask();
    }

    @AfterEach
    void cleanUp(){
        file.delete();
    }

}