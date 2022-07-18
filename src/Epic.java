import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private List <Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubTask(int subTaskId){
        subTaskIds.add(subTaskId);
    }

    public List<Integer> getSubTaskId() {
        return subTaskIds;
    }
}