package com.study.webflux_coroutine.controller

import com.study.webflux_coroutine.model.Article
import com.study.webflux_coroutine.service.ArticleService
import com.study.webflux_coroutine.service.ReqRequest
import com.study.webflux_coroutine.service.ReqUpdate
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody request: ReqRequest): Article {
        return service.create(request)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun get(@PathVariable id: Long): Article {
        return service.get(id)
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    suspend fun get(@RequestParam title: String?): Flow<Article> {
        return service.getAll()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun update(
        @PathVariable id: Long,
        @RequestBody request: ReqUpdate
    ): Article {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable id: Long) {
        return service.delete(id)
    }
}