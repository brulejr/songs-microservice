package io.jrb.labs.songs.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import com.google.common.base.Verify.verify
import io.jrb.common.service.CrudService
import io.jrb.labs.songs.model.SongGroupEntity
import io.jrb.labs.songs.repository.SongEntityRepository
import io.jrb.labs.songs.repository.SongGroupEntityRepository
import io.jrb.labs.songs.resource.SongGroup
import org.apache.commons.lang3.StringUtils.isNotBlank
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class SongGroupService(
        val songGroupEntityRepository: SongGroupEntityRepository,
        val songEntityRepository: SongEntityRepository,
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

    fun createSongGroup(songGroup: SongGroup): Mono<SongGroup> {
        return Mono.just(validateForCreate(songGroup))
                .flatMap(this::create)
    }

    fun deleteSongGroup(guid: UUID): Mono<Void> {
        return super.delete(guid)
    }

    fun findSongGroupByGuid(guid: UUID): Mono<SongGroup> {
        return super.findByGuid(guid)
    }

    fun listAllSongGroups(): Flux<SongGroup> {
        return super.listAll()
    }

    fun updateSongGroup(guid: UUID, patch: JsonPatch): Mono<SongGroup> {
        return super.update(guid, patch)
    }

    private fun validateForCreate(songGroup: SongGroup): SongGroup {
        verify(songGroup.guid == null, "SongGroup.guid must be null")
        verify(isNotBlank(songGroup.title), "SongGroup.title must be non-blank")
        verify(!songGroup.songs.isNullOrEmpty(), "SongGroup.songs must be non-empty")
        return songGroup
    }

}
