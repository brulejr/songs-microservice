package io.jrb.labs.songs.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.jrb.common.service.CrudService
import io.jrb.labs.songs.model.SongGroupEntity
import io.jrb.labs.songs.repository.SongGroupEntityRepository
import io.jrb.labs.songs.resource.SongGroup
import org.springframework.stereotype.Service

@Service
class SongGroupService(
        val songGroupEntityRepository: SongGroupEntityRepository,
        val objectMapper: ObjectMapper
) : CrudService<SongGroupEntity, SongGroup>(
        songGroupEntityRepository,
        "SongGroup",
        SongGroupEntity::class.java,
        SongGroupEntity.Builder::class.java,
        SongGroup::class.java,
        SongGroup.Builder::class.java,
        objectMapper
) {
}
