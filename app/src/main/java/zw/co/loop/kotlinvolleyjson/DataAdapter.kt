package zw.co.loop.kotlinvolleyjson

import android.content.Context
import android.opengl.Visibility
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import zw.co.loop.kotlinvolleyjson.R.layout.item_feed


class DataAdapter(feedModelList: List<DataModel>, context: Context?, clickListener: ClickListener) : RecyclerView.Adapter<DataAdapter.FeedModelViewHolder>() {
    private val feedList: List<DataModel>? = feedModelList
    private val listener: ClickListener? = clickListener
    private val mContext: Context? = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedModelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(item_feed, parent, false)
        return FeedModelViewHolder(v)
    }

    override fun onBindViewHolder(holder: FeedModelViewHolder, position: Int) {
        val dataModel = feedList!![position]
        holder.title.setText(dataModel.title)
        holder.date.text =dataModel.date

        holder.like.setOnClickListener { _ -> listener?.onLikeClicked(dataModel)
            holder.like.setImageDrawable(ContextCompat.getDrawable(mContext!!, R.drawable.ic_like_filled))
            holder.like.setColorFilter(ContextCompat.getColor(mContext, R.color.red_like))
        }

        holder.comment.setOnClickListener {
            listener?.onCommentClicked(dataModel)
        }

        holder.container.setOnClickListener {
            listener?.onContainerClicked(dataModel)
        }
        holder.more.setOnClickListener{
            listener?.onMoreClicked(dataModel)
        }
            if (!TextUtils.isEmpty(dataModel.image_url)) {
                Picasso.get().load(dataModel.image_url.trim()).fit().centerCrop()
                    .into(holder.image)
            }
        }

    override fun getItemCount(): Int {
        return feedList!!.size
    }

    inner class FeedModelViewHolder(feedView: View) : RecyclerView.ViewHolder(feedView) {
        val title: TextView
        val date: TextView
        val image: ImageView
        val container: LinearLayout
        val like : ImageButton
        val comment : ImageButton
        val more : ImageButton
        init {
            title = feedView.findViewById(R.id.feed_title)
            date = feedView.findViewById(R.id.feed_date)
            image = feedView.findViewById(R.id.thumbnail)
            container = feedView.findViewById(R.id.article_card_root)
            like = feedView.findViewById(R.id.item_like)
            comment = feedView.findViewById(R.id.item_comment)
            more = feedView.findViewById(R.id.item_more)
             }
    }

}
