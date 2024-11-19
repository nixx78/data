package lv.nixx.poc.rest.taskmanager;

import lv.nixx.poc.model.taskmanager.TaskDTO;
import lv.nixx.poc.orm.taskmanager.Task;
import lv.nixx.poc.orm.taskmanager.TaskDependency;
import lv.nixx.poc.repository.taskmanager.TaskDependencyRepository;
import lv.nixx.poc.repository.taskmanager.TaskRepository;
import lv.nixx.poc.service.taskmanager.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TaskManagerController {

    private final TaskRepository taskRepository;
    private final TaskDependencyRepository taskDependencyRepository;
    private final TaskService taskService;


    public TaskManagerController(TaskRepository taskRepository, TaskDependencyRepository taskDependencyRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskDependencyRepository = taskDependencyRepository;
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public Collection<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/task/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/task_dependencies")
    public Collection<TaskDependency> getAllDependencies() {
        return taskDependencyRepository.findAll();
    }

}
