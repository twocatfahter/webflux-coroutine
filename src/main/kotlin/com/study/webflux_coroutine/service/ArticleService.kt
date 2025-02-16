package com.study.webflux_coroutine.service

import com.study.webflux_coroutine.exception.NoArticleFound
import com.study.webflux_coroutine.model.Article
import com.study.webflux_coroutine.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val repository: ArticleRepository,
) {

    suspend fun create(request: ReqRequest): Article {
        return repository.save(request.toArticle())
    }

    suspend fun get(id: Long): Article {
        return repository.findById(id) ?: throw NoArticleFound("id: $id")
    }

    suspend fun getAll(title: String? = null): Flow<Article> {
        return if (title.isNullOrBlank()) {
            repository.findAll()
        } else {
            repository.findAllByTitleContains(title)
        }
    }

    suspend fun update(id: Long, request: ReqUpdate): Article {
        val article = repository.findById(id) ?: throw NoArticleFound("id: $id")
        return repository.save(article.apply {
            request.title?.let { title = it }
            request.body?.let { body = it }
            request.authorId?.let { authorId = it }
        })
    }

    suspend fun delete(id: Long) {
        return repository.deleteById(id)
    }
}

data class ReqUpdate (
    val title: String? = null,
    val body: String? = null,
    val authorId: Long? = null,
)
data class ReqRequest(
    val title: String,
    val body: String? = null,
    val authorId: Long? = null,
) {
    fun toArticle(): Article =
        Article(
            title = title,
            body = body,
            authorId = authorId,
        )
}