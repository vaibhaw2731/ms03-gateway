package org.jhipster.microservice.service.impl;

import org.jhipster.microservice.service.TaskService;
import org.jhipster.microservice.domain.Task;
import org.jhipster.microservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Save a task.
     *
     * @param task the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    @Override
    public List<Task> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll();
    }


    /**
     * Get one task by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Task> findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id);
    }

    /**
     * Delete the task by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
}
