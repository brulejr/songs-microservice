package io.jrb.labs.songs.model

import io.jrb.common.model.Entity
import io.jrb.common.model.EntityBuilder
import io.jrb.labs.songs.resource.Song
import io.jrb.labs.songs.resource.SongGroup
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document
data class SongGroupEntity(
        @Id override val id: String?,
        @Indexed(unique = true) override val guid: UUID?,
        val title: String,
        val description: String,
        val songs: List<UUID>,
        override val createdOn: Instant?,
        override val createdBy: String?,
        override val modifiedOn: Instant?,
        override val modifiedBy: String?
) : Entity {

    data class Builder(
            private var title: String? = null,
            private var description: String? = null,
            private var songs: List<UUID>? = null
    ) : EntityBuilder<SongGroupEntity, SongGroup>() {
        constructor(songGroup: SongGroup): this() {
            this.guid = songGroup.guid
            this.title = songGroup.title
            this.description = songGroup.description
            this.songs = songGroup.songs
            this.createdOn = songGroup.createdOn
            this.createdBy = songGroup.createdBy
            this.modifiedOn = songGroup.modifiedOn
            this.modifiedBy = songGroup.modifiedBy
        }

        constructor(songGroupEntity: SongGroupEntity): this() {
            this.id = songGroupEntity.id
            this.guid = songGroupEntity.guid
            this.title = songGroupEntity.title
            this.description = songGroupEntity.description
            this.songs = songGroupEntity.songs
            this.createdOn = songGroupEntity.createdOn
            this.createdBy = songGroupEntity.createdBy
            this.modifiedOn = songGroupEntity.modifiedOn
            this.modifiedBy = songGroupEntity.modifiedBy
        }

        fun title(title: String?) = apply { this.title = title }
        fun description(description: String?) = apply { this.description = description }
        fun songs(songs: List<UUID>?) = apply { this.songs = songs }

        override fun build() = SongGroupEntity(
                id = this.id,
                guid = this.guid,
                title = this.title!!,
                description = this.description!!,
                songs = this.songs!!,
                createdOn = this.createdOn,
                createdBy = this.createdBy,
                modifiedOn = this.modifiedOn,
                modifiedBy = this.modifiedBy
        )
    }

}
