package service;

import constans.Status;
import constans.TaskType;
import exception.ManagerSaveException;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Calendar.NOVEMBER;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public List getAllTask() {
        return super.getAllTask();
    }

    @Override
    public List getAllEpic() {
        return super.getAllEpic();
    }

    @Override
    public List getAllSubTask() {
        return super.getAllSubTask();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    @Override
    public Task getIdTask(int idTask) {
        return super.getIdTask(idTask);
    }

    @Override
    public Task getIdEpic(int idEpic) {
        return super.getIdEpic(idEpic);
    }

    @Override
    public Task getIdSubTask(int idSubTask) {
        return super.getIdSubTask(idSubTask);
    }

    @Override
    public List getEpicSubTask(int idEpic) {
        return super.getEpicSubTask(idEpic);
    }

    @Override
    public void removeByIdTask(int id) {
        super.removeByIdTask(id);
        save();
    }

    @Override
    public void removeByIdSubTask(int id) {
        super.removeByIdSubTask(id);
        save();
    }

    @Override
    public void removeByIdEpic(int id) {
        super.removeByIdEpic(id);
        save();
    }

    @Override
    public void updateTask(Task task, Integer i) {
        super.updateTask(task, i);
        save();
    }

    @Override
    public void updateEpic(Epic epic, Integer i) {
        super.updateEpic(epic, i);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask, Integer i) {
        super.updateSubTask(subTask, i);
        save();
    }

    @Override
    public List<Task> getTaskHistory() {
        List<Task> history = super.getTaskHistory();
        save();
        return history;
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,startTime,duration,endTime,epic\r\n");
            taskToString(bufferedWriter, tasks.values());
            taskToString(bufferedWriter, epics.values());
            subToString(bufferedWriter, subTasks.values());
            bufferedWriter.newLine();
            historyToString(bufferedWriter, historyManager);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в записи", e);
        }
    }

    private <T extends Task> void taskToString(BufferedWriter bufferedWriter, Collection<T> tasks) throws IOException {
        for (Task value : tasks) {
            bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + ","
                    + value.getStatus() + "," + value.getDescription() + "," + value.getStartTime() + ","
                    + value.getDuration() + "," + value.getEndTime());
            bufferedWriter.newLine();
        }
    }

    private void subToString(BufferedWriter bufferedWriter, Collection<SubTask> tasks) throws IOException {
        for (SubTask value : tasks) {
            bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + ","
                    + value.getStatus() + "," + value.getDescription() + "," + value.getStartTime() + ","
                    + value.getDuration() + "," + value.getEndTime() + "," + value.getEpicId());
            bufferedWriter.newLine();
        }
    }

    private static void historyToString(BufferedWriter bufferedWriter, HistoryManager manager) throws IOException {
        List<Task> historyId = manager.getHistory();
        for (Task task : historyId) {
            bufferedWriter.write(task.getId() + ",");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        try {
            final String csv = Files.readString(Path.of("recourses/task.csv"));
            final String[] lines = csv.split(System.lineSeparator());
            if(!lines[1].isEmpty()) {
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i];
                    if (line.isEmpty()) {
                        break;
                    }
                    taskManager.taskFromString(line);
                }
                if (lines[lines.length - 2].isBlank()) {
                    List<Integer> history = historyFromString(lines[lines.length - 1]);
                    for (Integer taskId : history) {
                        if (taskManager.getIdTask(taskId) != null) {
                            taskManager.historyManager.add(taskManager.getIdTask(taskId));
                        } else if (taskManager.getIdEpic(taskId) != null) {
                            taskManager.historyManager.add(taskManager.getIdEpic(taskId));
                        } else if (taskManager.getIdSubTask(taskId) != null) {
                            taskManager.historyManager.add(taskManager.getIdSubTask(taskId));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в чтении", e);
        }
        return taskManager;
    }

    private static List<Integer> historyFromString(String value) {
        final String[] values = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String i : values) {
            history.add(Integer.valueOf(i));
        }
        return history;
    }

    private Task taskFromString(String value) {
        final String[] values = value.split(",");
        String description = values[4];
        String title = values[2];
        Integer id = Integer.valueOf(values[0]);
        TaskType type = TaskType.valueOf(values[1]);
        Status status = Status.valueOf(values[3]);
        LocalDateTime startTime = LocalDateTime.parse(values[5]);
        Integer duration = Integer.valueOf(values[6]);
        LocalDateTime endTime = LocalDateTime.parse(values[7]);

        switch (type) {
            case TASK:
                Task task = new Task(title, description, status, startTime, duration);
                task.setId(id);
                task.getEndTime();
                addTask(task);
                return task;
            case EPIC:
                Epic epic = new Epic(title, description, status);
                epic.setId(id);
                epic.setStartTime(startTime);
                epic.setDuration(duration);
                epic.setEndTime(endTime);
                addEpic(epic);
                return epic;
            case SUB_TASK:
                Integer epicId = Integer.valueOf(values[8]);
                SubTask subtask = new SubTask(title, description, status, startTime, duration, epicId);
                subtask.setId(id);
                subtask.getEndTime();
                addSubTask(subtask);
                return subtask;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW,
                LocalDateTime.of(2021, NOVEMBER, 2, 3, 0), 20);//0
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.DONE,
                LocalDateTime.of(2021, NOVEMBER, 2, 4, 0),
                120);//1
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Описание эпика 1");//2
        Epic epic2 = new Epic("Epic 2", "Описание эпика 2");//3
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("Subtask 1", "Описание подзадачи 1", Status.IN_PROGRESS,
                LocalDateTime.of(2021, NOVEMBER, 10, 2, 0),
                100, epic1.getId());//4
        SubTask subTask2 = new SubTask("Subtask 2", "Описание подзадачи 2", Status.DONE,
                LocalDateTime.of(2020, NOVEMBER, 2, 2, 0),
                120, epic1.getId());//5
        SubTask subTask3 = new SubTask("Subtask 3", "Описание подзадачи 3", Status.NEW,
                LocalDateTime.of(2020, NOVEMBER, 22, 2, 0),
                100, epic2.getId());//6
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        System.out.println(taskManager.getIdTask(0));
        System.out.println(taskManager.getIdTask(1));
        System.out.println(taskManager.getIdEpic(2));
        System.out.println(taskManager.getIdEpic(3));
        System.out.println(taskManager.getIdSubTask(5));
        System.out.println(taskManager.getIdSubTask(4));
        System.out.println(taskManager.getIdSubTask(6));
        System.out.println(taskManager.getIdTask(0));

        System.out.println(" ");
        System.out.println(taskManager.getTaskHistory());
        System.out.println(" ");
        System.out.println(taskManager.getPrioritizedTasks());
        System.out.println(" ");
        System.out.println(taskManager.getAllTask());
        System.out.println(" ");

        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(new File("recourses/task.csv"));

        System.out.println(manager2.getIdSubTask(5));
        System.out.println(manager2.getIdSubTask(6));
        System.out.println(manager2.getIdTask(0));
        System.out.println(manager2.getTaskHistory());

        System.out.println(manager2.getPrioritizedTasks());
    }
}