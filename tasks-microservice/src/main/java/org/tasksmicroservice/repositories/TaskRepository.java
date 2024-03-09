package org.tasksmicroservice.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tasksmicroservice.entities.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    boolean existsById(Long id); // Method to check if a task ID exists
    public List<Task> findByUserId(Long userId);
    void deleteTasksByUserId(Long userId);
}
