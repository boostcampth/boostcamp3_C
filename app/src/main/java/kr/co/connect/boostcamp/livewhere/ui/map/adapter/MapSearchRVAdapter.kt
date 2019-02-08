package kr.co.connect.boostcamp.livewhere.ui.map.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ItemHouseSearchRecyclerviewBinding
import kr.co.connect.boostcamp.livewhere.databinding.ItemPlaceSearchRecyclerviewBinding
import kr.co.connect.boostcamp.livewhere.model.Category
import kr.co.connect.boostcamp.livewhere.model.House
import kr.co.connect.boostcamp.livewhere.model.Place
import java.util.*

class MapSearchRVAdapter(private var itemList: List<Any>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Category.HOUSE.type) {
            val itemHouseSearchRecyclerviewBinding: ItemHouseSearchRecyclerviewBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_house_search_recyclerview,
                    parent,
                    false
                )
            return HouseViewHolder(
                itemHouseSearchRecyclerviewBinding
            )
        } else if (viewType == Category.PLACE.type) {
            val itemPlaceSearchRecyclerviewBinding: ItemPlaceSearchRecyclerviewBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_place_search_recyclerview,
                    parent,
                    false
                )
            return PlaceViewHolder(
                itemPlaceSearchRecyclerviewBinding
            )
        }
        val adViewInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_empty_recyclerview, parent, false)
        return EmptyViewHolder(adViewInflater)

    }

    override fun getItemCount(): Int {
        return itemList?.size!!
    }

    fun changeItemList(itemList: List<Any>) {
        this.itemList = itemList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Category.HOUSE.type) {
            val houseViewHolder = holder as HouseViewHolder
            val houseItem = itemList?.get(position) as House
            houseViewHolder.binding.house = houseItem
        } else if (holder.itemViewType == Category.PLACE.type) {
            val placeViewHolder = holder as PlaceViewHolder
            val placeItem = itemList?.get(position) as Place
            placeViewHolder.binding.place = placeItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList != null) {
            if (itemList?.size!! > 0) {
                if (itemList!![0] is House) {
                    return Category.HOUSE.type
                } else if (itemList!![0] is Place) {
                    //올림차순
                    Collections.sort(itemList as List<Place>) { o1, o2 -> o1.distance.toInt() - o2.distance.toInt() }
                    return Category.PLACE.type
                }
            }
        }

        return super.getItemViewType(position)
    }

    class HouseViewHolder(val binding: ItemHouseSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
    class PlaceViewHolder(val binding: ItemPlaceSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
    class EmptyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}