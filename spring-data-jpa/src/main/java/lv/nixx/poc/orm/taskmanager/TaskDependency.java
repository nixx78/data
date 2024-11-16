package lv.nixx.poc.orm.taskmanager;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TASK_DEPENDENCY")
public class TaskDependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dependency_id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task; // Задача, которая зависит от другой

    @ManyToOne
    @JoinColumn(name = "depends_on_task_id", nullable = false)
    private Task dependsOn; // Задача, от которой зависит текущая

    private String dependencyType; // Тип зависимости: "BLOCKING", "OPTIONAL", и т.д.

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}