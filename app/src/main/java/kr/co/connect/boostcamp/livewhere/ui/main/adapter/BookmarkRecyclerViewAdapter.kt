package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.Bookmark

class BookmarkRecyclerViewAdapter(private val context: Context, private val list: ArrayList<Bookmark>) :
    RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_bookmark_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BookmarkRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val iv_building = itemView?.findViewById<ImageView>(R.id.iv_bookmark_image)
        val tv_building_contents = itemView?.findViewById<TextView>(R.id.tv_bookmark_building_contents)
        val tv_location_contents = itemView?.findViewById<TextView>(R.id.tv_bookmark_location_contents)
        val tv_pay_contents = itemView?.findViewById<TextView>(R.id.tv_bookmark_pay_contents)

        fun bind(context: Context, item: Bookmark) {
            //image 추후 추가
            if (iv_building != null)
                Glide.with(context)
                    .load(item.imgUrl)
                    .into(iv_building)
            /*
            type when default images are added
            else
             */
            tv_building_contents?.text = item.buildingName
            tv_location_contents?.text = item.address
            if (item.isRent) {
                tv_pay_contents?.text = item.pee.toString()
            } else {
                tv_pay_contents?.text = item.deposit.toString()
            }
        }
    }
}