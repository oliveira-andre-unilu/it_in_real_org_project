package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.persister.TimestampEntryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/timestamps")
@Tag(name = "Timestamps", description = "Operations for managing timestamp entries")
public class TimestampEntryController {

    private final TimestampEntryRepository repository;

    public TimestampEntryController(TimestampEntryRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all timestamp entries", description = "Retrieve a list of all timestamp entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class)))
    })
    @GetMapping
    public List<TimestampEntry> getAll() {
        return repository.findAll();
    }

    @Operation(summary = "Create a new timestamp entry", description = "Add a new timestamp entry to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public TimestampEntry create(@RequestBody TimestampEntry entry) {
        return repository.save(entry);
    }

    @Operation(summary = "Get timestamp entry by ID", description = "Retrieve a single timestamp entry by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TimestampEntry> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update timestamp entry by ID", description = "Update an existing timestamp entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TimestampEntry> update(@PathVariable Long id, @RequestBody TimestampEntry updated) {
        return repository.findById(id)
                .map(e -> {
                    e.setEmployee(updated.getEmployee());
                    e.setProject(updated.getProject());
                    e.setTag(updated.getTag());
                    e.setStartingTime(updated.getStartingTime());
                    e.setDuration(updated.getDuration());
                    e.setLatitude(updated.getLatitude());
                    e.setLongitude(updated.getLongitude());
                    return ResponseEntity.ok(repository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete timestamp entry by ID", description = "Remove a timestamp entry by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Timestamp entry successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> {
                    repository.delete(e);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}