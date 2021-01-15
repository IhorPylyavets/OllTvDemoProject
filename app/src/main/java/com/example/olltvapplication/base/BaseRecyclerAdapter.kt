package com.example.olltvapplication.base

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

open class BaseRecyclerAdapter<T, V : View>(
    private val context: Context,
    items: List<T>?,
    private val factory: BaseViewHolder.Factory<T, V>
) : RecyclerView.Adapter<BaseViewHolder<T, V>>() {

    protected var filteredItems: MutableList<T>
    private var allItems: MutableList<T>
    var selectedPosition = -1
        protected set
    private var onItemClickListener: ((view: View, item: T, position: Int, previousSelectedPosition: Int) -> Unit)? =
        null
    private var onItemLongClickListener: ((view: View, item: T, position: Int, previousSelectedPosition: Int) -> Unit)? =
        null
    private var onBindListener: ((holder: BaseViewHolder<T, V>, item: T, position: Int) -> Unit)? =
        null
    protected var lastClickStamp: Long = 0L
    val selectedItem: T?
        get() = if (selectedPosition < 0 || filteredItems.size == 0) null else filteredItems[selectedPosition]
    val items: List<T>
        get() = filteredItems
    var prefetchDistance: Int = 5
    private var loadMoreListener: (() -> Unit)? = null

    fun getCurFactory() = factory

    init {
        filteredItems = ArrayList()
        allItems = ArrayList()
        items?.let {
            filteredItems.addAll(it)
            allItems.addAll(it)
        }
    }

    fun listSize() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, V> {
        val vh = factory.create(context)
        initClickListener(vh)
        return vh
    }

    @Suppress("unchecked_cast")
    protected fun initClickListener(vh: BaseViewHolder<T, V>) {
        vh.itemView.setOnLongClickListener {
            onItemLongClickListener?.let { listener ->
                val position = vh.adapterPosition
                val previousSelection = selectedPosition
                selectedPosition = position
                val item: T = it.tag as T
                listener.invoke(it, item, position, previousSelection)
            }
            return@setOnLongClickListener false
        }
        vh.itemView.setOnClickListener {
            val position = vh.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val previousSelection = selectedPosition
                selectedPosition = position
                val item: T = it.tag as T
                debounceClick(it, item, position, previousSelection)
            }
        }
    }

    private fun debounceClick(view: View, item: T, position: Int, previousSelectedPosition: Int) {
        val currentTimestamp = SystemClock.uptimeMillis()
        val lastTimestamp = lastClickStamp
        lastClickStamp = currentTimestamp
        if (currentTimestamp - lastTimestamp > 500) {
            onItemClickListener?.invoke(view, item, position, previousSelectedPosition)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, V>, position: Int) {
        filteredItems[position]?.let {
            holder.bind(holder.itemView as? V, it, position)
            holder.bindSelected(holder.itemView as? V, it, position, selectedPosition)
            holder.itemView.tag = it
        }
        onBindListener?.invoke(holder, filteredItems[position], position)
        if (position == itemCount - prefetchDistance)
            loadMoreListener?.invoke()
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    fun resetSelection() {
        selectedPosition = -1
    }

    fun setOnItemLongClickListener(onItemClickListener: ((view: View, item: T, position: Int, previousSelectedPosition: Int) -> Unit)?) {
        this.onItemLongClickListener = onItemClickListener
    }

    fun replace(list: List<T>?, animate: Boolean = false) {
        filteredItems = if (list != null) ArrayList(list) else ArrayList()
        allItems = if (list != null) ArrayList(list) else ArrayList()
        if (animate) {
            animateTo(filteredItems)
        } else {
            notifyDataSetChanged()
        }
    }

    fun replace(list: List<T>) {
        filteredItems.clear()
        allItems.clear()

        filteredItems.addAll(list)
        allItems.addAll(list)
        notifyDataSetChanged()
    }

    fun addItems(list: List<T>?, animate: Boolean = false) {
        list?.let {
            filteredItems.addAll(it)
            allItems.addAll(it)
        }
        if (animate) {
            animateTo(filteredItems)
        } else {
            notifyDataSetChanged()
        }
    }

    fun addItem(position: Int, e: T) {
        filteredItems.add(position, e)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val e = filteredItems.removeAt(fromPosition)
        filteredItems.add(toPosition, e)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun removeItemByPosition(position: Int): T {
        val e = filteredItems.removeAt(position)
        notifyItemRemoved(position)
        return e
    }

    fun removeItemByObj(obj: T): Int {
        var position = -1
        for (i in filteredItems.indices) {
            if (filteredItems[i] == obj) position = i
        }
        filteredItems.removeAt(position)
        notifyItemRemoved(position)
        return position
    }

    fun setOnItemClickListener(onItemClickListener: ((view: View, item: T, position: Int, previousSelectedPosition: Int) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnBindListener(onBindListener: ((holder: BaseViewHolder<T, V>, item: T, position: Int) -> Unit)?) {
        this.onBindListener = onBindListener
    }

    fun setLoadMoreListener(loadMoreListener: () -> Unit) {
        this.loadMoreListener = loadMoreListener
    }

    fun getObjIndexes(): IntRange {
        return filteredItems.indices
    }

    fun animateTo(inList: List<T>) {
        applyAndAnimateRemovals(inList)
        applyAndAnimateAdditions(inList)
        applyAndAnimateMovedItems(inList)
    }

    private fun applyAndAnimateRemovals(inList: List<T>) {
        for (i in filteredItems.indices.reversed()) {
            val e = filteredItems[i]
            if (!inList.contains(e)) {
                removeItemByPosition(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(inList: List<T>) {
        var i = 0
        val count = inList.size
        while (i < count) {
            val e = inList[i]
            if (!filteredItems.contains(e)) {
                addItem(i, e)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(inList: List<T>) {
        for (toPosition in inList.indices.reversed()) {
            val e = inList[toPosition]
            val fromPosition = filteredItems.indexOf(e)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun filter(query: String?, prediction: (model: T) -> Boolean) {
        val newFilteredItems = ArrayList<T>()
        if (query.isNullOrEmpty()) {
            newFilteredItems.addAll(allItems)
        } else {
            for (model in allItems) {
                if (prediction.invoke(model)) {
                    newFilteredItems.add(model)
                }
            }
        }
        animateTo(newFilteredItems)
        filteredItems.clear()
        filteredItems = newFilteredItems
    }

    fun clear() {
        filteredItems.clear()
        allItems.clear()
    }

    fun setSelected(position: Int) {
        if (position >= 0 && position < itemCount) {
            val prevSelected = selectedPosition
            selectedPosition = position
            notifyItemChanged(prevSelected)
            notifyItemChanged(selectedPosition)
        }
    }
}
