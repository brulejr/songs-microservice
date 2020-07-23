package io.jrb.labs.songs.resource

import com.fasterxml.jackson.annotation.JsonInclude
import io.jrb.common.resource.Resource
import io.jrb.common.resource.ResourceBuilder
import io.jrb.labs.songs.model.SongGroupEntity
import java.time.Instant
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SongGroup(
        override val guid: UUID?,
        val title: String,
        val description: String,
        val songs: List<UUID>,
        override val createdOn: Instant?,
        override val createdBy: String?,
        override val modifiedOn: Instant?,
        override val modifiedBy: String?
) : Resource {

    data class Builder(
            private var title: String? = null,
            private var description: String? = null,
            private var songs: List<UUID>? = null
    ) : ResourceBuilder<SongGroup, SongGroupEntity>() {

        constructor(songGroupEntity: SongGroupEntity): this() {
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

        override fun build() = SongGroup(
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
