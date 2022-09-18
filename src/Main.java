import constans.Status;
import service.*;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);//0
        Task task2 = new Task("Задача 2", "Описание задачи 2",Status.NEW);//1
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Описание эпика 1");//2
        Epic epic2 = new Epic("Epic 2", "Описание эпика 2");//3
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS,epic1.getId());//4
        SubTask subTask2 = new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,epic1.getId());//5
        SubTask subTask3 = new SubTask("Subtask 3", "Описание подзадачи 3", Status.NEW,epic1.getId());//6
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.getIdTask(1);
        System.out.println(taskManager.getTaskHistory());
        taskManager.getIdTask(0);
        taskManager.getIdSubTask(4);
        System.out.println(taskManager.getTaskHistory());
        taskManager.getIdSubTask(6);
        taskManager.getIdSubTask(5);
        taskManager.getIdEpic(2);;
        taskManager.getIdEpic(3);
        taskManager.getIdTask(0);
        taskManager.getIdEpic(2);
        taskManager.getIdSubTask(6);
        taskManager.getIdTask(1);

        System.out.println(' ');
        System.out.println(taskManager.getTaskHistory());
        System.out.println(' ');
        taskManager.removeByIdTask(0);
        System.out.println(taskManager.getTaskHistory());
        System.out.println(' ');
        taskManager.removeByIdEpic(2);
        System.out.println(taskManager.getTaskHistory());
        System.out.println(' ');
    }
}