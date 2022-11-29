package service;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import constans.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected int generator = 0;
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
        updateEpicDuration(epic);
        updateEpicStartTime(epic);
        updateEpicEndTime(epic);
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
    public void updateEpicDuration(Epic epic){
        int sum = 0;
        for(Integer id: epic.getSubTaskId()){
            sum += subTasks.get(id).getDuration();
        }
        epic.setDuration(sum);
    }

    public void updateEpicStartTime(Epic epic){
        LocalDateTime startTime;
        List <LocalDateTime> startTimes = new ArrayList<>();
        for(Integer id: epic.getSubTaskId()){
            startTimes.add(subTasks.get(id).getStartTime());
        }
        startTime = startTimes.get(0);
        for(int i = 0; i < startTimes.size(); i++){
            if(startTime.isAfter(startTimes.get(i))){
                startTime = startTimes.get(i);
            }
        }
        epic.setStartTime(startTime);
    }

    public void updateEpicEndTime(Epic epic){
        LocalDateTime endTime;
        List <LocalDateTime> endTimes = new ArrayList<>();
        for(Integer id: epic.getSubTaskId()){
            endTimes.add(subTasks.get(id).getStartTime());
        }
        endTime = endTimes.get(0);
        for(int i = 0; i < endTimes.size(); i++){
            if(endTime.isBefore(endTimes.get(i))){
                endTime = endTimes.get(i);
            }
        }
        epic.setEndTime(endTime);
    }

    @Override
    public void addEpic(Epic epic) {
        int epicId = generator++;
        epic.setId(epicId);
        epics.put(epicId, epic);
        updateEpicStatus(epic);
    }

    @Override
    public List getAllTask() {
        List<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public List getAllEpic() {
        List<Epic> epicList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicList.add(epic);
        }
        return epicList;
    }

    @Override
    public List getAllSubTask() {
        List<SubTask> subList = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subList.add(subTask);
        }
        return subList;
    }

    @Override
    public void removeAllTask() {
        for (Integer t : tasks.keySet()) {
            historyManager.remove(t);
        }
        tasks.clear();
    }

    @Override
    public void removeAllEpic() {
        for (Integer s : subTasks.keySet()) {
            historyManager.remove(s);
        }
        for (Integer e : epics.keySet()) {
            historyManager.remove(e);
        }
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubTask() {
        for (Integer s : subTasks.keySet()) {
            historyManager.remove(s);
        }
        subTasks.clear();
        for (Epic e : epics.values()) {
            e.getSubTaskId().clear();
            updateEpicStatus(e);
        }
    }

    @Override
    public Task getIdTask(int idTask) {
        if (tasks.containsKey(idTask)) {
            historyManager.add(tasks.get(idTask));//добавляем историю
            return tasks.get(idTask);
        } else {
            return null;
        }
    }

    @Override
    public Task getIdEpic(int idEpic) {
        if (epics.containsKey(idEpic)) {
            historyManager.add(epics.get(idEpic));//добавляем историю
            return epics.get(idEpic);
        } else {
            return null;
        }
    }

    @Override
    public Task getIdSubTask(int idSubTask) {
        if (subTasks.containsKey(idSubTask)) {
            historyManager.add(subTasks.get(idSubTask));//добавляем историю
            return subTasks.get(idSubTask);
        } else {
            return null;
        }
    }

    @Override
    public List getEpicSubTask(int idEpic) {
        List<SubTask> epicSub = new ArrayList<>();
        if (epics.containsKey(idEpic)) {
            for (SubTask subTask : subTasks.values()) {
                if (idEpic == subTask.getEpicId()) {
                    epicSub.add(subTask);
                }
            }
        }
        return epicSub;
    }

    @Override
    public void removeByIdTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);//удаляем историю
    }

    @Override
    public void removeByIdSubTask(int id) {
        int epicId = subTasks.get(id).getEpicId();
        subTasks.remove(id);
        historyManager.remove(id);//удаляем историю
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
                historyManager.remove(s.getId());//удаляем историю
            }
        }
        while (subTasks.values().remove(null)) ;
        epics.remove(id);
        historyManager.remove(id);//удаляем историю
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
    public void getPrioritizedTasks() {
    }

    @Override
    public List<Task> getTaskHistory() {
        return historyManager.getHistory();
    }
}