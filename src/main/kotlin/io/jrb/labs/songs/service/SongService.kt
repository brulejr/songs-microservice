package io.jrb.labs.songs.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.jrb.common.service.CrudService
import io.jrb.labs.songs.model.SongEntity
import io.jrb.labs.songs.repository.SongEntityRepository
import io.jrb.labs.songs.resource.Song
import org.springframework.stereotype.Service

@Service
class SongService(
        val songEntityRepository: SongEntityRepository,
        val objectMapper: ObjectMapper
) : CrudService<SongEntity, Song>(
        songEntityRepository,
        "Song",
        SongEntity::class.java,
        SongEntity.Builder::class.java,
        Song::class.java,
        Song.Builder::class.java,
        objectMapper
) {
}
