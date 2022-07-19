import constans.Status;
import service.Manager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Task 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Task 2", "Описание задачи 2",Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Описание эпика 1");
        Epic epic2 = new Epic("Epic 2", "Описание эпика 2");
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS,epic1.getId());
        SubTask subTask2 = new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,epic1.getId());
        SubTask subTask3 = new SubTask("Subtask 3", "Описание подзадачи 3", Status.NEW,epic2.getId());

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        manager.getAllTask();
        manager.getAllEpic();
        manager.getAllSubTask();
        System.out.println(' ');

        manager.updateTask(new Task("Task 1", "Описание задачи 1",Status.DONE),0);
        manager.updateTask(new Task("Task 2", "Описание задачи 2",Status.IN_PROGRESS),1);
        manager.updateSubTask(new SubTask("Subtask 1", "Описание подзадачи 1", Status.DONE,epic1.getId()),4);
        manager.updateSubTask(new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,epic1.getId()),5);
        manager.updateSubTask(new SubTask("Subtask 3", "Описание подзадачи 3", Status.IN_PROGRESS,epic2.getId()),6);

        manager.getAllTask();
        manager.getAllEpic();
        manager.getAllSubTask();
        System.out.println(' ');

        manager.removeByIdTask(0);
        manager.removeByIdEpic(2);

        manager.getAllTask();
        manager.getAllEpic();
        manager.getAllSubTask();
    }
}