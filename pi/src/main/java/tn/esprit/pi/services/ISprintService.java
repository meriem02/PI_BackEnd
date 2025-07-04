package tn.esprit.pi.services;

import tn.esprit.pi.dto.CalendarEventDto;
import tn.esprit.pi.dto.CreateSprintDto;
import tn.esprit.pi.dto.SprintWithTasksDTO;
import tn.esprit.pi.entities.Projet;
import tn.esprit.pi.entities.Sprint;
import tn.esprit.pi.entities.Tache; // Keep Tache if createTaskForSprint still uses it directly

import java.util.List;
import java.util.Optional;

public interface ISprintService {
    // Basic CRUD
    Sprint saveSprint(Sprint sprint);
    Sprint createSprint(CreateSprintDto createSprintDto);
    Sprint getSprintById(Long id);
    List<Sprint> getAllSprints();
    void deleteSprint(Long id);
    Sprint updateSprint(Long id, Sprint sprintDetails);

    // Student Assignment
    Sprint affecterEtudiantAuSprint(Long sprintId, String etudiantEmail);
    Sprint supprimerEtudiantDuSprint(Long sprintId, String etudiantEmail);
    List<String> getEtudiantsAffectesAuSprint(Long sprintId);

    // Calendar
    List<CalendarEventDto> getAllCalendarEvents();

    // Search
    List<Sprint> searchSprintsByNom(String nom);

    // Tasks and DTOs
    Optional<SprintWithTasksDTO> getSprintWithTasks(Long sprintId);
    Tache createTaskForSprint(Long sprintId, Tache tache); // If you're using a DTO for task creation, this might change

    // Velocity
    double calculateSprintVelocity(Long sprintId); // Velocity for a single sprint

    // --- NEW: For the Velocity Chart ---
    /**
     * Retrieves historical velocity data for all relevant sprints.
     * This data is used to populate the velocity chart on the frontend.
     * Expected return format: List of objects, each containing sprintName, committedPoints, and completedPoints.
     */
    List<Object[]> getVelocityHistory(); // Or a specific DTO if you create one, e.g., List<VelocityHistoryDTO>

    // Initial Sprint Generation
    Sprint generateInitialSprintForProject(Projet projet);

    // Project-specific sprints
    List<Sprint> findByProjetId(Long projetId);

    // Deadline Notifications
    List<Sprint> getSprintsWithUpcomingDeadlines();

    public List<Sprint> getSprintsByStudentEmail(String studentEmail);
    // --- NEW: For Automatic Sprint Completion ---
    /**
     * Checks if all tasks associated with a given sprint are in a 'DONE' status.
     * If all tasks are done, the sprint's status is automatically updated to 'COMPLETED'.
     * This method is typically called by the TacheService after a task's status changes.
     *
     * @param sprintId The ID of the sprint to check.
     */
    void checkAndCompleteSprintIfAllTasksDone(Long sprintId);
    void checkAndCompleteProjectIfAllSprintsDone(Long projectId);

    public List<Sprint> getSprintsByProjetId(Long projetId);
}
