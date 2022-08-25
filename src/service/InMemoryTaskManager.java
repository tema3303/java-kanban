package service;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import constans.Status;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private int generator = 0;
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task task) {
        int taskId = generator++;
        task.setId(taskId);
        tasks.put(taskId, task);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        int subTaskId = generator++;
        subTask.setId(subTaskId);
        subTasks.put(subTaskId, subTask);
        epic.addSubTask(subTaskId);
        updateEpicStatus(epic);
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        int countNew = 0;
        int countDone = 0;
        for (Integer id : epic.getSubTaskId()) {
            if (subTasks.get(id).getStatus() == Status.NEW) {
                countNew++;
            }
            if (subTasks.get(id).getStatus() == Status.DONE) {
                countDone++;
            }
        }
        if (epic.getSubTaskId().size() == countNew || epic.getSubTaskId().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else if (epic.getSubTaskId().size() == countDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        int epicId = generator++;
        epic.setId(epicId);
        epics.put(epicId, epic);
        updateEpicStatus(epic);
    }

    @Override
    public void getAllTask() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    @Override
    public void getAllEpic() {
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    @Override
    public void getAllSubTask() {
        for (SubTask subTask : subTasks.values()) {
            System.out.println(subTask);
        }
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public void removeAllEpic() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubTask() {
        subTasks.clear();
        for (Epic e : epics.values()) {
            e.getSubTaskId().clear();
            updateEpicStatus(e);
        }
    }

    @Override
    public void getIdTask(int idTask) {
        if (tasks.containsKey(idTask)) {
            System.out.println(tasks.get(idTask));
            historyManager.add(tasks.get(idTask));
        } else {
            System.out.println("Задачи с таким id не сущетсвует");
        }
    }

    @Override
    public void getIdEpic(int idEpic) {
        if (epics.containsKey(idEpic)) {
            System.out.println(epics.get(idEpic));
            historyManager.add(epics.get(idEpic));
        } else {
            System.out.println("Эпика с таким id не сущетсвует");
        }
    }

    @Override
    public void getIdSubTask(int idSubTask) {
        if (subTasks.containsKey(idSubTask)) {
            System.out.println(subTasks.get(idSubTask));
            historyManager.add(subTasks.get(idSubTask));
        } else {
            System.out.println("Подзадачи с таким id не сущетсвует");
        }
    }

    @Override
    public void getEpicSubTask(int idEpic) {
        if (epics.containsKey(idEpic)) {
            for (SubTask subTask : subTasks.values()) {
                if (idEpic == subTask.getEpicId()) {
                    System.out.println(subTask);
                }
            }
        } else {
            System.out.println("Эпика с таким id не сущетсвует!");
        }
    }

    @Override
    public void removeByIdTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeByIdSubTask(int id) {
        int epicId = subTasks.get(id).getEpicId();
        subTasks.remove(id);
        for (int i = 0; i < epics.get(epicId).getSubTaskId().size(); i++) {
            if (epics.get(epicId).getSubTaskId().get(i) == id) {
                epics.get(epicId).getSubTaskId().remove(i);
            }
        }
        updateEpicStatus(epics.get(epicId));
    }

    @Override
    public void removeByIdEpic(int id) {
        for (SubTask s : subTasks.values()) {
            if (id == s.getEpicId()) {
                subTasks.put(s.getId(), null);
            }
        }
        while (subTasks.values().remove(null)) ;
        epics.remove(id);
    }

    @Override
    public void updateTask(Task task, Integer i) {
        if (tasks.get(i) == null) {
            return;
        }
        tasks.put(i, task);
        task.setId(i);
    }

    @Override
    public void updateEpic(Epic epic, Integer i) {
        if (epics.get(i) == null) {
            return;
        }
        epics.put(i, epic);
        epic.setId(i);
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == i) {
                epic.addSubTask(subTask.getId());
            }
        }
        updateEpicStatus(epic);
    }

    @Override
    public void updateSubTask(SubTask subTask, Integer i) {
        if (subTasks.get(i) == null) {
            return;
        }
        subTasks.put(i, subTask);
        subTask.setId(i);
        for (Epic e : epics.values()) {
            if (subTask.getEpicId() == e.getId()) {
                e.getSubTaskId().remove(i);
                e.addSubTask(i);
            } else {
                e.getSubTaskId().remove(i);
            }
        }
        updateEpicStatus(epics.get(subTask.getEpicId()));
    }

    @Override
    public void getTaskHistory() {
        System.out.println(historyManager.getHistory());
    }
}