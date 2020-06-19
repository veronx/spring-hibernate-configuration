package com.veronx;

import com.veronx.entity.Task;
import com.veronx.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import javax.persistence.EntityManager;

@ExtendWith({SpringExtension.class})
@DataJpaTest
public class SpringHibernateConfigurationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testEntityManager() {
        entityManager.persist(new Task("Do the dishes"));
        entityManager.persist(new Task("Study JPA"));
        entityManager.persist(new Task("Prepare the dinner"));

        List<Task> tasks = entityManager.createQuery("select t from Task t", Task.class).getResultList();

        Assertions.assertEquals(3, tasks.size(),
                                "Three tasks should have been returned from the query");

        int rowRemoved = entityManager.createQuery("delete from Task").executeUpdate();

        Assertions.assertEquals(3, rowRemoved,
                                "Three tasks should have been removed after executing the delete query");
    }

    @Test
    public void testSpringDataRepository() {
        taskRepository.save(new Task("Do the dishes"));
        taskRepository.save(new Task("Study Spring Data"));
        taskRepository.save(new Task("Prepare the dinner"));

        List<Task> tasks = taskRepository.findAll();

        Assertions.assertEquals(3, tasks.size(),
                                "Three tasks should have been returned from the query");

        // DeleteAllInBatch will execute the delete query only once
        taskRepository.deleteAllInBatch();

        Assertions.assertEquals(0, taskRepository.findAll().size(),
                                "Three tasks should have been removed after executing the delete query");
    }
}
