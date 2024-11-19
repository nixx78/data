package lv.nixx.poc.service.taskmanager;

import lv.nixx.poc.model.taskmanager.TaskDTO;
import lv.nixx.poc.orm.taskmanager.Task;
import lv.nixx.poc.repository.taskmanager.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findByIdWithDependencies(taskId).orElseThrow(() -> new IllegalArgumentException("Task with ID [" + taskId + "] not found"));

        Collection<String> dependentTask = task.getDependentTasks().stream().map(t -> t.getTask().getName()).toList();
        Collection<String> dependencies = task.getDependencies().stream().map(t -> t.getTask().getName()).toList();

        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .name(task.getName())
                .description(task.getDescription())
                .dependentTasks(dependentTask)
                .dependencies(dependencies)
                .build();
    }

}
