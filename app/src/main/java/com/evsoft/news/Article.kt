package com.evsoft.news


data class Article(
    val title: String,
    val link: String,
    val creator: Array<String>,
    val description: String,
    val content: String,
    val pubDate: String,
    val fullDescription: String,
    val country: Array<String>,
    val category: Array<String>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (title != other.title) return false
        if (link != other.link) return false
        if (!creator.contentEquals(other.creator)) return false
        if (description != other.description) return false
        if (content != other.content) return false
        if (pubDate != other.pubDate) return false
        if (fullDescription != other.fullDescription) return false
        if (!country.contentEquals(other.country)) return false
        return category.contentEquals(other.category)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + link.hashCode()
        result = 31 * result + creator.contentHashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + pubDate.hashCode()
        result = 31 * result + fullDescription.hashCode()
        result = 31 * result + country.contentHashCode()
        result = 31 * result + category.contentHashCode()
        return result
    }

}