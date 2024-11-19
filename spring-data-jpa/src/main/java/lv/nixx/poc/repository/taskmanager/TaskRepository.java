package lv.nixx.poc.repository.taskmanager;

import lv.nixx.poc.orm.taskmanager.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.dependencies d LEFT JOIN FETCH t.dependentTasks dt WHERE t.id = :id")
    Optional<Task> findByIdWithDependencies(@Param("id") Long id);
}
