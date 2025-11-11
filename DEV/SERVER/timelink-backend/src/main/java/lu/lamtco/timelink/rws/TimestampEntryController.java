package lu.lamtco.timelink.rws;

import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timestamps")
public class TimestampEntryController {

    private final TimestampEntryRepository repository;

    public TimestampEntryController(TimestampEntryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TimestampEntry> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public TimestampEntry create(@RequestBody TimestampEntry entry) {
        return repository.save(entry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimestampEntry> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> { repository.delete(e); return ResponseEntity.noContent().build(); })
                .orElse(ResponseEntity.notFound().build());
    }
}