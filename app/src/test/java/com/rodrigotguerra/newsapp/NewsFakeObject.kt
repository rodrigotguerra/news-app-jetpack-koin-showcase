package com.rodrigotguerra.newsapp

import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.models.NewsListResponseSchema

object NewsFakeObject {
    val newsResponse = NewsListResponseSchema(
        listOf<NewsData>(
            NewsData(
                "UOL",
                "Friaca em SP",
                "Pra você que está pensando em fazer um pião, pegue seu bombojaco e sua toca, porque hoje faz 10 graus em São Paulo",
                "www.uol.com.br/noticias/friaca-em-sp",
                "Clima",
                true,
                id = 0
            ),
            NewsData(
                "Estadão",
                "Fim da pandemia no Brasil",
                "Para a surpresa de todos, hoje, em Março de 2024, foi decretada a fim da pandemia no Brasil.",
                "www.estadao.com.br/noticias/fim-da-pandemia",
                "COVID-19",
                true,
                id = 1
            ),
            NewsData(
                "XP Investimentos",
                "Dólar finalmente cai",
                "Dólar hoje pode ser encontrado por 50 centavos de real e brasileiros finalmente decidem ir para a Disney ver o Mickey.",
                "www.xpinvestimentos.com.br/noticias/dolar-cai",
                "Economia",
                id = 2
            ),
            NewsData(
                "New York Times",
                "Sixers wins the NBA Finals",
                "After 37 years, the Philadelphia 76ers wins the championship.",
                "www.nytimes.com.br/news/sixers-wins-nba",
                "Sports",
                id = 3
            )
        )
    )

}