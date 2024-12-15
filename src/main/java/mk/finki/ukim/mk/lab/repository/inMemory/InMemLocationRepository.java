package mk.finki.ukim.mk.lab.repository.inMemory;

import mk.finki.ukim.mk.lab.Bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemLocationRepository {

    public List<Location> findAll() {
        return DataHolder.locations;
    }

    public Optional<Location> findById(Long id) {
        return DataHolder.locations.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

}
