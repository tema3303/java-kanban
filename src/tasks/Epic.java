package tasks;

import constans.Status;
import constans.TaskType;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, Status status) {
        super(name, description);
        this.status = status;
    }

    public void addSubTask(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public List<Integer> getSubTaskId() {
        return subTaskIds;
    }
}

