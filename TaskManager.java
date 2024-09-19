import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TaskManager {
    private static final String FILE_PATH = "tasks.json";
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    public void addTask(String description) {
        int id = tasks.size() + 1;
        Task newTask = new Task(id, description);
        tasks.add(newTask);
        saveTasks();
    }

    public void updateTask(int id, String description, String status) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.setStatus(status);
                saveTasks();
                return;
            }
        }
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        saveTasks();
    }

    public void listTasks(String status) {
        for (Task task : tasks) {
            if (status == null || task.getStatus().equals(status)) {
                System.out.println(task);
            }
        }
    }

    private void loadTasks() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();
                tasks = parseTasks(json.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTasks() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH));
            writer.write(tasksToJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Task> parseTasks(String json) {
        List<Task> tasks = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Task task = new Task(
                jsonObject.getInt("id"),
                jsonObject.getString("description")
            );
            task.setStatus(jsonObject.getString("status"));
            tasks.add(task);
        }
        return tasks;
    }

    private String tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", task.getId());
            jsonObject.put("description", task.getDescription());
            jsonObject.put("status", task.getStatus());
            jsonObject.put("createdAt", task.getCreatedAt().toString());
            jsonObject.put("updatedAt", task.getUpdatedAt().toString());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
