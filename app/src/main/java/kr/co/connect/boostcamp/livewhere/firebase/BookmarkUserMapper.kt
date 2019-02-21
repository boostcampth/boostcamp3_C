package kr.co.connect.boostcamp.livewhere.firebase

import kr.co.connect.boostcamp.livewhere.model.BookmarkUser
import kr.co.connect.boostcamp.livewhere.model.entity.BookmarkUserEntity

class BookmarkUserMapper : FirebaseMapper<BookmarkUserEntity, BookmarkUser>() {
    override fun map(from: BookmarkUserEntity): BookmarkUser {
        val bookmarkUser = BookmarkUser()
        bookmarkUser.uuid = from.uuid
        return bookmarkUser
    }
}