package bsa.services;

import java.util.UUID;

public interface TaskService {
    UUID create(String title);

    void updateTitle(UUID taskId, String title);
    void updateDescription(UUID taskId, String description);
    void updateRemainingEstimate(UUID taskId, int estimate);
    void setAssignee(UUID taskId, UUID userId);
}
