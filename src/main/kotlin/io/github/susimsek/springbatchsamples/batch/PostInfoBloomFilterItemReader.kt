package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.model.PostTitle
import org.springframework.batch.item.ItemReader

class PostInfoBloomFilterItemReader : ItemReader<PostTitle> {

    @Volatile private var results: MutableList<PostTitle>? = null

    private var lock = Any()

    @Volatile private var current = 0

    override fun read(): PostTitle? {
        synchronized(lock) {
            val nextPageNeeded = results != null && this.current >= results!!.size
            if (results == null || nextPageNeeded) {
                results = this.doPageRead()
                if (results!!.isEmpty()) {
                    return null
                }
            }
            return if (this.current < results!!.size) {
                val curLine: PostTitle = results!![this.current]
                ++this.current
                curLine
            } else {
                null
            }
        }
    }

    @Throws(Exception::class)
    fun doPageRead(): MutableList<PostTitle> {
        var content = mutableListOf<PostTitle>()
        for (i in 1..1_000_000) {
            content.add(PostTitle("test$i"))
        }
        return content
    }
}
