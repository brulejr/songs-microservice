package io.jrb.labs.songs.rest

import com.github.fge.jsonpatch.JsonPatch
import io.jrb.labs.songs.resource.SongGroup
import io.jrb.labs.songs.service.SongGroupService
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
@RequestMapping("/api/v1/songGroups")
class SongGroupController(val songGroupService: SongGroupService) {

    private val log = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSongGroup(@RequestBody songGroup: SongGroup): Mono<EntityModel<SongGroup>> {
        return songGroupService.create(songGroup).map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
                    .add(collectionLink())
        }
    }

    @DeleteMapping("/{songGroupGuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSongGroup(@PathVariable songGroupGuid: UUID): Mono<Void> {
        return songGroupService.delete(songGroupGuid)
    }

    @GetMapping("/{songGroupGuid}")
    fun getSongGroupById(@PathVariable songGroupGuid: UUID): Mono<EntityModel<SongGroup>> {
        return songGroupService.findByGuid(songGroupGuid).map {
            EntityModel.of(it)
                    .add(selfLink(songGroupGuid))
                    .add(collectionLink())
        }
    }

    @GetMapping
    fun listSongGroups(): Flux<EntityModel<SongGroup>> {
        return songGroupService.listAll().map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
        }
    }

    @PatchMapping(path = ["/{songGroupGuid}"], consumes = ["application/json-patch+json"])
    fun patchSongGroup(@PathVariable songGroupGuid: UUID, @RequestBody taskPatch: JsonPatch): Mono<EntityModel<SongGroup>> {
        return songGroupService.update(songGroupGuid, taskPatch).map {
            EntityModel.of(it)
                    .add(selfLink(it.guid!!))
                    .add(collectionLink())
        }
    }

    private fun collectionLink(): Link {
        return linkTo(methodOn(javaClass).listSongGroups()).withRel("collection")
    }

    private fun selfLink(songGroupGuid: UUID): Link {
        return linkTo(methodOn(javaClass).getSongGroupById(songGroupGuid)).withSelfRel()
    }

}
