package tasks;

import constans.Status;
import constans.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, Status status) {
        super(name, description);
        this.status = status;
    }

    public Epic(String name, String description, Status status, LocalDateTime startTime, int duration, LocalDateTime endTime) {
        super(name, description);
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = endTime;
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
}