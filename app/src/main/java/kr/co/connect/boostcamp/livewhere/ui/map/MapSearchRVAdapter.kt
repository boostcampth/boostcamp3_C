package kr.co.connect.boostcamp.livewhere.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ItemEmptyRecyclerviewBinding
import kr.co.connect.boostcamp.livewhere.databinding.ItemHouseSearchRecyclerviewBinding
import kr.co.connect.boostcamp.livewhere.databinding.ItemPlaceSearchRecyclerviewBinding
import kr.co.connect.boostcamp.livewhere.model.Category
import kr.co.connect.boostcamp.livewhere.model.EmptyInfo
import kr.co.connect.boostcamp.livewhere.model.House
import kr.co.connect.boostcamp.livewhere.model.Place
import java.util.*

class MapSearchRVAdapter(private val itemList: List<Any>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Category.HOUSE.type -> {
                val itemHouseSearchRecyclerviewBinding: ItemHouseSearchRecyclerviewBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_house_search_recyclerview,
                        parent,
                        false
                    )
                return HouseViewHolder(itemHouseSearchRecyclerviewBinding)
            }
            Category.PLACE.type -> {
                val itemPlaceSearchRecyclerviewBinding: ItemPlaceSearchRecyclerviewBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_place_search_recyclerview,
                        parent,
                        false
                    )
                return PlaceViewHolder(itemPlaceSearchRecyclerviewBinding)
            }
            else -> {
                val itemEmptyRecyclerviewBinding: ItemEmptyRecyclerviewBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_empty_recyclerview,
                    parent,
                    false
                )
                return EmptyViewHolder(itemEmptyRecyclerviewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder.itemViewType == Category.HOUSE.type -> {
                val houseViewHolder = holder as HouseViewHolder
                val houseItem = itemList?.get(position) as House
                houseViewHolder.binding.houseInfo = houseItem
            }
            holder.itemViewType == Category.PLACE.type -> {
                val placeViewHolder = holder as PlaceViewHolder
                val placeItem = itemList?.get(position) as Place
                placeViewHolder.binding.place = placeItem
            }
            holder.itemViewType == Category.EMPTY.type -> {
                val placeViewHolder = holder as EmptyViewHolder
                val emptyItem = itemList?.get(position) as EmptyInfo
                placeViewHolder.binding.emptyInfo = emptyItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList?.size!! > 0) {
            when {
                itemList[0] is House -> return Category.HOUSE.type
                itemList[0] is Place -> {
                    //올림차순
                    Collections.sort(itemList as List<Place>) { o1, o2 -> o1.distance.toInt() - o2.distance.toInt() }
                    return Category.PLACE.type
                }
                itemList[0] is EmptyViewHolder -> return Category.EMPTY.type
            }
        }
        return super.getItemViewType(position)
    }


    class HouseViewHolder(val binding: ItemHouseSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
    class PlaceViewHolder(val binding: ItemPlaceSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
    class EmptyViewHolder(val binding: ItemEmptyRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
}