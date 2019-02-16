package kr.co.connect.boostcamp.livewhere.ui.map.adapter

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
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.Place
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity

class MapSearchRVAdapter(private var itemList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                return HouseViewHolder(
                    itemHouseSearchRecyclerviewBinding
                )
            }
            Category.PLACE.type -> {
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
            else -> {
                val itemEmptyRecyclerviewBinding: ItemEmptyRecyclerviewBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_empty_recyclerview,
                    parent,
                    false
                )
                return EmptyViewHolder(
                    itemEmptyRecyclerviewBinding
                )
            }
        }
    }

    fun setItemChange(itemList: List<Any>) {
        this.itemList = itemList
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder.itemViewType == Category.HOUSE.type -> {
                val houseViewHolder = holder as HouseViewHolder
                val markerInfo = itemList.get(position) as MarkerInfo
                houseViewHolder.binding.markerInfo = markerInfo
            }
            holder.itemViewType == Category.PLACE.type -> {
                val placeViewHolder = holder as PlaceViewHolder
                val placeItem = itemList.get(position) as Place
                placeViewHolder.binding.place = placeItem
                placeViewHolder.binding.lifecycleOwner = placeViewHolder.binding.root.context as MapActivity
            }
            holder.itemViewType == Category.EMPTY.type -> {
                val emptyViewHolder = holder as EmptyViewHolder
                val emptyItem = itemList.get(position) as EmptyInfo
                emptyViewHolder.binding.emptyInfo = emptyItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList.isNotEmpty()) {
            when {
                itemList[0] is MarkerInfo -> return Category.HOUSE.type
                itemList[0] is Place -> {
                    //올림차순
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