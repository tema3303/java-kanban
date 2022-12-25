package Tests;

import org.junit.jupiter.api.*;
import service.manager.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setUp(){
        taskManager = new InMemoryTaskManager();
        initTask();
    }

    @AfterEach
    void deleteAllTestTask(){
        taskManager.removeAllTask();
        taskManager.removeAllSubTask();
        taskManager.removeAllEpic();
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
}