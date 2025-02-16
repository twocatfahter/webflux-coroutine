package com.study.webflux_coroutine.service

import com.study.webflux_coroutine.repository.ArticleRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.ReactiveTransaction
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@SpringBootTest
@ActiveProfiles("test")
class ArticleServiceTest (
  @Autowired private val service: ArticleService,
  @Autowired private val repository: ArticleRepository,
  @Autowired private val rxtx: TransactionalOperator,
): StringSpec({

    "get all" {
        rxtx.rollback {
            service.create(ReqRequest("title 1"))
            service.create(ReqRequest("title 2"))
            service.create(ReqRequest("title matched"))
            service.getAll().toList().size shouldBe 3
            service.getAll("matched").toList().size shouldBe 1
        }
    }


    "create and get" {

    }

    "update" {

    }

    "delete" {

    }
})


suspend fun <T> TransactionalOperator.rollback(f: suspend (ReactiveTransaction) -> T): T {
 return this.executeAndAwait { tx ->
  tx.setRollbackOnly()
  f.invoke(tx)
 }
}