package io.jrb.labs.songs.resource

import com.fasterxml.jackson.annotation.JsonInclude
import io.jrb.common.resource.Resource
import io.jrb.common.resource.ResourceBuilder
import io.jrb.labs.songs.model.SongEntity
import java.time.Instant
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Song(
        override val guid: UUID?,
        val title: String,
        val authors: List<String>,
        val additionalTitles: List<String>,
        val themes: List<String>,
        val lyrics: Map<String, List<String>>,
        val lyricOrder: List<String>,
        val sourceId: String,
        val sourceSystem: String,
        override val createdOn: Instant?,
        override val createdBy: String?,
        override val modifiedOn: Instant?,
        override val modifiedBy: String?
) : Resource {

    data class Builder(
            private var title: String? = null,
            private var authors: List<String>? = null,
            private var additionalTitles: List<String>? = null,
            private var themes: List<String>? = null,
            private var lyrics: Map<String, List<String>>? = null,
            private var lyricOrder: List<String>? = null,
            private var sourceId: String? = null,
            private var sourceSystem: String? = null
    ) : ResourceBuilder<Song, SongEntity>() {

        constructor(songEntity: SongEntity): this() {
            this.guid = songEntity.guid
            this.title = songEntity.title
            this.authors = songEntity.authors
            this.additionalTitles = songEntity.additionalTitles
            this.themes = songEntity.themes
            this.lyrics = songEntity.lyrics
            this.lyricOrder = songEntity.lyricOrder
            this.sourceId = songEntity.sourceId
            this.sourceSystem = songEntity.sourceSystem
            this.createdOn = songEntity.createdOn
            this.createdBy = songEntity.createdBy
            this.modifiedOn = songEntity.modifiedOn
            this.modifiedBy = songEntity.modifiedBy
        }

        fun title(title: String?) = apply { this.title = title }
        fun authors(authors: List<String>?) = apply { this.authors = authors }
        fun additionalTitles(additionalTitles: List<String>?) = apply { this.additionalTitles = additionalTitles }
        fun themes(themes: List<String>?) = apply { this.themes = themes }
        fun lyrics(lyrics: Map<String, List<String>>?) = apply { this.lyrics = lyrics }
        fun lyricOrder(lyricOrder: List<String>?) = apply { this.lyricOrder = lyricOrder }
        fun sourceId(sourceId: String?) = apply { this.sourceId = sourceId }
        fun sourceSystem(sourceSystem: String?) = apply { this.sourceSystem = sourceSystem }

        override fun build() = Song(
                guid = this.guid,
                title = this.title!!,
                authors = this.authors!!,
                additionalTitles = this.additionalTitles!!,
                themes = this.themes!!,
                lyrics = this.lyrics!!,
                lyricOrder = this.lyricOrder!!,
                sourceId = this.sourceId!!,
                sourceSystem = this.sourceSystem!!,
                createdOn = this.createdOn,
                createdBy = this.createdBy,
                modifiedOn = this.modifiedOn,
                modifiedBy = this.modifiedBy
        )
    }

}
