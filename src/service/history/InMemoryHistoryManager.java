package service.history;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        removeNode(nodeMap.get(task.getId()));
        linklast(task);
        nodeMap.put(task.getId(), last);
    }

    private void linklast(Task task) {
        final Node oldLast = last;
        final Node newNode = new Node(task, last, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
    }

    private List<Task> getTask() {
        List<Task> tasks = new ArrayList<>();
        if (!nodeMap.isEmpty()) {
            Node taskNode = first;
            while (taskNode != null) {
                tasks.add(taskNode.task);
                taskNode = taskNode.next;
            }
        }
        return tasks;
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
    }

    private void removeNode(Node node) {
        if (!nodeMap.containsValue(node)) {
            return;
        }
        nodeMap.remove(node.task.getId());
        final Node nextNode = node.next;
        final Node prevNode = node.prev;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        node.task = null;
    }

    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    private class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "task=" + task +
                    ", prev=" + (prev != null ? prev.task : null) +
                    ", next=" + (next != null ? next.task : null) +
                    '}';
        }
    }
}