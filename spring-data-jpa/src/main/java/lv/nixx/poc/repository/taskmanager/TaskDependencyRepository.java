package lv.nixx.poc.repository.taskmanager;

import lv.nixx.poc.orm.taskmanager.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
}
