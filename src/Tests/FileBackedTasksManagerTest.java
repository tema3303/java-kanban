package Tests;

import constans.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.file.FileBackedTasksManager;
import tasks.Epic;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File file;

    @BeforeEach
    void setUp() {
        file = new File("recourses/task.csv");
        taskManager = new FileBackedTasksManager(file);
        initTask();
    }

    @AfterEach
    void cleanUp() {
        file.delete();
    }

    @Override
    void addNewTaskTest() {
        super.addNewTaskTest();
    }

    @Override
    void addNewSubTaskTest() {
        super.addNewSubTaskTest();
    }

    @Override
    void addNewEpicTest() {
        super.addNewEpicTest();
    }

    @Override
    void getAllTasksTest() {
        super.getAllTasksTest();
    }

    @Override
    void getAllSubTask() {
        super.getAllSubTask();
    }

    @Override
    void getAllEpicTest() {
        super.getAllEpicTest();
    }

    @Override
    void removeAllTasksTest() {
        super.removeAllTasksTest();
    }

    @Override
    void removeAllEpicTest() {
        super.removeAllEpicTest();
    }

    @Override
    void removeAllSubTaskTest() {
        super.removeAllSubTaskTest();
    }

    @Override
    void getIdTaskTest() {
        super.getIdTaskTest();
    }

    @Override
    void getIdSubTaskTest() {
        super.getIdSubTaskTest();
    }

    @Override
    void getIdEpicTest() {
        super.getIdEpicTest();
    }

    @Override
    void getEpicSubTaskTest() {
        super.getEpicSubTaskTest();
    }

    @Override
    void removeByIdTaskTest() {
        super.removeByIdTaskTest();
    }

    @Override
    void removeByIdEpicTest() {
        super.removeByIdEpicTest();
    }

    @Override
    void removeByIdSubTaskTest() {
        super.removeByIdSubTaskTest();
    }

    @Override
    void updateTaskTest() {
        super.updateTaskTest();
    }

    @Override
    void updateEpicTest() {
        super.updateEpicTest();
    }

    @Override
    void updateSubTaskTest() {
        super.updateSubTaskTest();
    }

    @Override
    void updateEpicStatusTest() {
        super.updateEpicStatusTest();
    }

    @Override
    void updateEpicDurationTest() {
        super.updateEpicDurationTest();
    }

    @Override
    void updateEpicStartTimeTest() {
        super.updateEpicStartTimeTest();
    }

    @Override
    void updateEpicEndTimeTest() {
        super.updateEpicEndTimeTest();
    }

    @Override
    void getPrioritizedTasksTest() {
        super.getPrioritizedTasksTest();
    }

    @Override
    void isNoIntersectionsTest() {
        super.isNoIntersectionsTest();
    }

    @Test
    void loadFromFileTest() {
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File(String.valueOf(file)));

        assertAll(
                () -> assertEquals(2, fileBackedTasksManager.getAllSubTask().size(), "Список задач не соответсвует ожидаемому"),
                () -> assertEquals(2, fileBackedTasksManager.getAllTask().size(), "Список сабстаков не соответсвует ожидаемому"),
                () -> assertEquals(1, fileBackedTasksManager.getAllEpic().size(), "Список эпиков не соответсвует ожидаемому"),
                () -> assertEquals(0, fileBackedTasksManager.getTaskHistory().size(), "Список истории должен быть пустой")
        );

        taskManager.getIdTask(0);
        taskManager.getIdSubTask(3);
        taskManager.getIdEpic(2);
        taskManager.getTaskHistory();
        FileBackedTasksManager fileBackedTasksManager1 = FileBackedTasksManager.loadFromFile(new File(String.valueOf(file)));

        assertAll(
                () -> assertEquals(3, fileBackedTasksManager1.getTaskHistory().size(), "Список истории заполняется неверно")
        );
        taskManager.addEpic(new Epic("Эпик Тест", "Эпик тест", Status.NEW, LocalDateTime.MIN, 0, LocalDateTime.MIN));
        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(new File(String.valueOf(file)));
        assertAll(
                () -> assertEquals(2, fileBackedTasksManager2.getAllEpic().size(), "Пустой эпик не учитывается")
        );
    }
}