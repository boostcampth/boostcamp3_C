package kr.co.connect.boostcamp.livewhere.ui.map.interfaces

interface OnViewHistoryListener {
    fun startObservable(title: String, toolbar: androidx.appcompat.widget.Toolbar)
    fun stopObservable()
}