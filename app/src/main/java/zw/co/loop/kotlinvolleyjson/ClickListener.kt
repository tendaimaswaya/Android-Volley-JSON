package zw.co.loop.kotlinvolleyjson

interface ClickListener {
    fun onContainerClicked(dataModel: DataModel)
    fun onLikeClicked(dataModel: DataModel)
    fun onCommentClicked(dataModel: DataModel)
    fun onMoreClicked(dataModel: DataModel)
}