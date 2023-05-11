package dev.workout.infrastructure;

import dev.workout.data.repository.AgendaRepository;
import dev.workout.domain.models.AgendaEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgendaRepositoryImpl implements AgendaRepository, PanacheRepository<AgendaEntity> {

    @Override
    public Uni<AgendaEntity> persist(AgendaEntity agenda) {
        return persist(agenda);
    }

    @Override
    public Uni<AgendaEntity> findByHash(String hash) {
        return find("hash", hash).firstResult();
    }

}
