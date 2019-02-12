package kr.co.connect.boostcamp.livewhere.repository

import kr.co.connect.boostcamp.livewhere.firebase.BookmarkUserDatabaseRepository
import kr.co.connect.boostcamp.livewhere.firebase.BookmarkUserMapper
import kr.co.connect.boostcamp.livewhere.model.BookmarkUser
import kr.co.connect.boostcamp.livewhere.model.entity.BookmarkUserEntity
import kr.co.connect.boostcamp.livewhere.util.ROOT_BOOKMARK_NAME


class BookmarkUserRepository : BookmarkUserDatabaseRepository<BookmarkUserEntity, BookmarkUser>(BookmarkUserMapper()) {

    override val rootNode: String
        get() = ROOT_BOOKMARK_NAME
}
