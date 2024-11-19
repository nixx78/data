package lv.nixx.poc.model.taskmanager;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class TaskDTO {
    private Long taskId;

    private String name;
    private String description;

    private Collection<String> dependencies;
    private Collection<String> dependentTasks;
}
