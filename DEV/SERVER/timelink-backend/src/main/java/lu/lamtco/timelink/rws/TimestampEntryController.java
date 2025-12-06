package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.dto.TimeStampEntryDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.business.TimestampEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lu.lamtco.timelink.domain.TimestampEntry;

import java.util.List;

/**
 * REST controller for managing timestamp entries.
 * Provides endpoints to create, read, update, and delete timestamp entries.
 *
 * @version 0.1
 */
@RestController
@RequestMapping("/api/timestamps")
@Tag(name = "Timestamps", description = "Operations for managing timestamp entries")
public class TimestampEntryController {

    private final TimestampEntryService service;

    /**
     * Constructor for TimestampEntryController.
     * @param service Service layer handling timestamp entry logic
     */
    public TimestampEntryController(TimestampEntryService service) {
        this.service = service;
    }

    /**
     * Retrieves all timestamp entries.
     * @param jwtToken JWT token for authentication
     * @return List of all timestamp entries or 498 if JWT token is invalid
     */
    @Operation(summary = "Get all timestamp entries", description = "Retrieve a list of all timestamp entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @GetMapping
    public ResponseEntity<List<TimestampEntry>> getAll(@RequestHeader String jwtToken) {
        try {
            return ResponseEntity.ok(service.getAllTimeStamps(jwtToken));
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        }
    }

    /**
     * Creates a new timestamp entry.
     * @param jwtToken JWT token for authentication
     * @param newEntry TimestampEntryDTO containing details of the new entry
     * @return Created TimestampEntry or relevant error response
     */
    @Operation(summary = "Create a new timestamp entry", description = "Add a new timestamp entry to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Referenced entity not found"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @PostMapping
    public ResponseEntity<TimestampEntry> create(@RequestHeader String jwtToken, @RequestBody TimeStampEntryDTO newEntry) {
        try {
            TimestampEntry result = service.createTimeStamp(jwtToken, newEntry);
            return ResponseEntity.ok(result);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Retrieves a timestamp entry by ID.
     * @param jwtToken JWT token for authentication
     * @param id ID of the timestamp entry
     * @return TimestampEntry if found, or relevant error response
     */
    @Operation(summary = "Get timestamp entry by ID", description = "Retrieve a single timestamp entry by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TimestampEntry> getById(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            TimestampEntry result = service.getTimeStampById(jwtToken, id);
            return ResponseEntity.ok(result);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Updates an existing timestamp entry.
     * @param jwtToken JWT token for authentication
     * @param id ID of the timestamp entry to update
     * @param updated DTO containing updated entry data
     * @return Updated TimestampEntry or relevant error response
     */
    @Operation(summary = "Update timestamp entry by ID", description = "Update an existing timestamp entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timestamp entry successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimestampEntry.class))),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TimestampEntry> update(@RequestHeader String jwtToken, @PathVariable Long id, @RequestBody TimeStampEntryDTO updated) {
        try {
            TimestampEntry result = service.updateTimeStamp(jwtToken, updated, id);
            return ResponseEntity.ok(result);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Deletes a timestamp entry by ID.
     * @param jwtToken JWT token for authentication
     * @param id ID of the timestamp entry to delete
     * @return 204 if deletion succeeded, or relevant error response
     */
    @Operation(summary = "Delete timestamp entry by ID", description = "Remove a timestamp entry by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Timestamp entry successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Timestamp entry not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "498", description = "Invalid JWT token")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader String jwtToken, @PathVariable Long id) {
        try {
            boolean deleted = service.deleteTimeStamp(jwtToken, id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.internalServerError().build();
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAuthentication e) {
            return ResponseEntity.status(498).build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(401).build();
        }
    }
}