public class TaskTrackerApp {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TaskTrackerApp <command> [<args>]");
            return;
        }

        TaskManager manager = new TaskManager();
        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: java TaskTrackerApp add <description>");
                } else {
                    manager.addTask(args[1]);
                }
                break;
            case "update":
                if (args.length < 4) {
                    System.out.println("Usage: java TaskTrackerApp update <id> <description> <status>");
                } else {
                    int id = Integer.parseInt(args[1]);
                    manager.updateTask(id, args[2], args[3]);
                }
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: java TaskTrackerApp delete <id>");
                } else {
                    int id = Integer.parseInt(args[1]);
                    manager.deleteTask(id);
                }
                break;
            case "list":
                if (args.length < 2) {
                    manager.listTasks(null);
                } else {
                    manager.listTasks(args[1]);
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }
}

