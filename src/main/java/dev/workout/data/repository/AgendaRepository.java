package dev.workout.data.repository;

import dev.workout.domain.models.AgendaEntity;
import io.smallrye.mutiny.Uni;

public interface AgendaRepository {
    Uni<AgendaEntity> persist(AgendaEntity agenda);
    Uni<AgendaEntity> findByHash(String hash);
    Uni<Long> deleteAgenda(AgendaEntity agenda);
}
