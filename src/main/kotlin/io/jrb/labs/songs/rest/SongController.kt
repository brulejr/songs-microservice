package io.jrb.labs.songs.rest

import com.github.fge.jsonpatch.JsonPatch
import io.jrb.labs.songs.resource.Song
import io.jrb.labs.songs.service.SongService
import mu.KotlinLogging
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/api/v1/songs")
class SongController(val songService: SongService) {

    private val log = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSong(@RequestBody song: Song): Mono<EntityModel<Song>> {
        return songService.createSong(song).map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
                    .add(collectionLink())
        }
    }

    @DeleteMapping("/{songGuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSong(@PathVariable songGuid: UUID): Mono<Void> {
        return songService.deleteSong(songGuid)
    }

    @GetMapping("/{songGuid}")
    fun getSongById(@PathVariable songGuid: UUID): Mono<EntityModel<Song>> {
        return songService.findSongByGuid(songGuid).map {
            EntityModel.of(it)
                    .add(selfLink(songGuid))
                    .add(collectionLink())
        }
    }

    @GetMapping
    fun listSongs(): Flux<EntityModel<Song>> {
        return songService.listAllSongs().map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
        }
    }

    @PatchMapping(path = ["/{songGuid}"], consumes = ["application/json-patch+json"])
    fun updateSong(@PathVariable songGuid: UUID, @RequestBody taskPatch: JsonPatch): Mono<EntityModel<Song>> {
        return songService.updateSong(songGuid, taskPatch).map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
                    .add(collectionLink())
        }
    }

    private fun collectionLink(): Link {
        return linkTo(methodOn(javaClass).listSongs()).withRel("collection")
    }

    private fun selfLink(songGuid: UUID): Link {
        return linkTo(methodOn(javaClass).getSongById(songGuid)).withSelfRel()
    }

}
