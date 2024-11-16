package lv.nixx.poc.rest.taskmanager;

import lv.nixx.poc.orm.taskmanager.Task;
import lv.nixx.poc.orm.taskmanager.TaskDependency;
import lv.nixx.poc.repository.taskmanager.TaskDependencyRepository;
import lv.nixx.poc.repository.taskmanager.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class TaskManagerController {

    private final TaskRepository taskRepository;
    private final TaskDependencyRepository taskDependencyRepository;

    public TaskManagerController(TaskRepository taskRepository, TaskDependencyRepository taskDependencyRepository) {
        this.taskRepository = taskRepository;
        this.taskDependencyRepository = taskDependencyRepository;
    }

    @GetMapping("/task")
    public Collection<Task> getAllTasks() {
        List<Task> all = taskRepository.findAll();
        return all;
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findByIdWithDependencies(id).orElseThrow(() -> new IllegalArgumentException("Task with ID [" + id + "] not found"));
        return task;
    }

    @GetMapping("/task_dependencies")
    public Collection<TaskDependency> getAllDependencies() {
        List<TaskDependency> all = taskDependencyRepository.findAll();
        return all;
    }

}
