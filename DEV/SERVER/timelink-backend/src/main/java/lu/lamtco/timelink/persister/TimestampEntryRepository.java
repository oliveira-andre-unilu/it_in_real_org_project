package lu.lamtco.timelink.persister;

import lu.lamtco.timelink.domain.TimestampEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimestampEntryRepository extends JpaRepository<TimestampEntry, Long> {}
