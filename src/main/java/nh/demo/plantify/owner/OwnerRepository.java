package nh.demo.plantify.owner;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends Repository<Owner, UUID> {
    Optional<Owner> getById(UUID ownerId);
}
