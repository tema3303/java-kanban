import constans.Status;
import service.*;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Task 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Task 2", "Описание задачи 2",Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Описание эпика 1");
        Epic epic2 = new Epic("Epic 2", "Описание эпика 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS,epic1.getId());
        SubTask subTask2 = new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,epic1.getId());
        SubTask subTask3 = new SubTask("Subtask 3", "Описание подзадачи 3", Status.NEW,epic2.getId());
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.getIdTask(1);
        System.out.println("История просмотров: " + historyManager.getHistory());
        taskManager.getIdTask(0);
        taskManager.getIdSubTask(4);
        taskManager.getIdEpic(2);
        System.out.println("История просмотров: " + historyManager.getHistory());
        taskManager.getIdTask(1);
        taskManager.getIdEpic(3);
        taskManager.getIdSubTask(5);
        taskManager.getIdSubTask(6);
        taskManager.getIdTask(1);
        taskManager.getIdEpic(2);
        taskManager.getIdEpic(2);

        System.out.println(' ');
        System.out.println("История просмотров: " + historyManager.getHistory());
    }
}