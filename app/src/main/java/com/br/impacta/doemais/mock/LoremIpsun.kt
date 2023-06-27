package com.br.impacta.doemais.mock

import com.br.impacta.doemais.commom.toBulletedList

class LoremIpsun {

    private val topicList = listOf("lorem","ipsum","deleniti","labore","suscipit").toBulletedList()

    companion object {
        const val SENTENCE_LOREM_IPSUN = "Lorem veniam deleniti molestiae delectus veritatis et."
    }

    fun getTopicsLoremIpsun(): CharSequence {
        return topicList
    }

}