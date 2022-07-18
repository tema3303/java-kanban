import java.util.HashMap;
import java.util.Map;

public class Manager {

    private Map<Integer,Task> tasks = new HashMap<>();
    private Map<Integer,SubTask> subTasks = new HashMap<>();
    private Map<Integer,Epic> epics = new HashMap<>();
    private int generator = 0;

    public void addTask(Task task){
        int taskId = generator++;
        task.setId(taskId);
        tasks.put(taskId,task);
    }

    public void addSubTask(SubTask subTask){
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        if(epic == null){
            return;
        }
        int subTaskId = generator++;
        subTask.setId(subTaskId);
        subTasks.put(subTaskId,subTask);
        epic.addSubTask(subTaskId);
        updateEpicStatus(epic);
    }

    public void updateEpicStatus(Epic epic){
        int countNew = 0;
        int countDone = 0;
        for(Integer id: epic.getSubTaskId()){
            if(subTasks.get(id).getStatus() == Status.NEW){
                countNew++;
            }
            if(subTasks.get(id).getStatus() == Status.DONE){
                countDone++;
            }
        }
        if(epic.getSubTaskId().size() == countNew || epic.getSubTaskId().isEmpty()){
            epic.setStatus(Status.NEW);
        } else if (epic.getSubTaskId().size() == countDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void addEpic(Epic epic){
        int epicId = generator++;
        epic.setId(epicId);
        epics.put(epicId,epic);
        updateEpicStatus(epic);
    }

    public void getAllTask(){
        for(Task task: tasks.values()){
            System.out.println(task);
        }
    }

    public void getAllEpic(){
        for(Epic epic: epics.values()){
            System.out.println(epic);
        }
    }

    public void getAllSubTask(){
        for(SubTask subTask: subTasks.values()){
            System.out.println(subTask);
        }
    }

    public void removeAllTask(){
        tasks.clear();
    }
    public void removeAllEpic(){
        subTasks.clear();
        epics.clear();
    }
    public void removeAllSubTask(){
        subTasks.clear();
        for(Epic e: epics.values()){
            e.getSubTaskId().clear();
            updateEpicStatus(e);
        }
    }

    public void getIdTask(int idTask){
        if(tasks.containsKey(idTask)){
            System.out.println(tasks.get(idTask));
        }else {
            System.out.println("Задачи с таким id не сущетсвует");
        }
    }

    public void getIdEpic(int idEpic){
        if(epics.containsKey(idEpic)){
            System.out.println(epics.get(idEpic));
        }else {
            System.out.println("Эпика с таким id не сущетсвует");
        }
    }
    public void getIdSubTask(int idSubTask){
        if(subTasks.containsKey(idSubTask)){
            System.out.println(subTasks.get(idSubTask));
        }else {
            System.out.println("Подзадачи с таким id не сущетсвует");
        }
    }

    public void getEpicSubTask(int idEpic){
        if(epics.containsKey(idEpic)){
            for(SubTask subTask: subTasks.values()){
                if(idEpic == subTask.getEpicId()){
                    System.out.println(subTask);
                }
            }
        }else{
            System.out.println("Эпика с таким id не сущетсвует!");
        }
    }

    public void removeByIdTask(int id){
        tasks.remove(id);
    }

    public void removeByIdSubTask(int id){
        int epicId = subTasks.get(id).getEpicId();
        subTasks.remove(id);
        for (int i = 0; i<epics.get(epicId).getSubTaskId().size(); i++) {
            if (epics.get(epicId).getSubTaskId().get(i) == id) {
                epics.get(epicId).getSubTaskId().remove(i);
            }
        }
        updateEpicStatus(epics.get(epicId));
    }

    public void removeByIdEpic(int id){
        epics.remove(id);
    }

    public void updateTask(Task task, Integer i){
        tasks.put(i, task);
        task.id = i;
    }

    public void updateEpic(Epic epic, Integer i){
        epics.put(i, epic);
        epic.id = i;
        for(SubTask subTask: subTasks.values()){
            if(subTask.getEpicId() == i){
                epic.addSubTask(subTask.id);
            }
        }
        updateEpicStatus(epic);
    }

    public void updateSubTask(SubTask subTask, Integer i){
        subTasks.put(i, subTask);
        subTask.id = i;
        updateEpicStatus(epics.get(subTask.getEpicId()));
    }
}