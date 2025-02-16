package com.study.webflux_coroutine.repository

import com.study.webflux_coroutine.model.Article
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ArticleRepository: CoroutineCrudRepository<Article, Long> {
    suspend fun findAllByTitleContains(title: String): Flow<Article>
}