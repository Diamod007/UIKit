package my.itgungnir.ui.list_footer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.itgungnir.ui.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

class FooterAdapter(
    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    private var status: FooterStatus.Status,
    private var colorPair: Pair<Int, Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = if (adapter.itemCount > 0) {
        adapter.itemCount + 1
    } else {
        adapter.itemCount
    }

    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) Int.MAX_VALUE
        else adapter.getItemViewType(position)

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        adapter.setHasStableIds(hasStableIds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            Int.MAX_VALUE ->
                FooterViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.view_list_footer, parent, false
                    )
                )
            else ->
                adapter.onCreateViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            Int.MAX_VALUE ->
                (holder as FooterViewHolder).applyStatus(status)
            else ->
                adapter.onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) =
        when (payloads.isNullOrEmpty()) {
            true ->
                this.onBindViewHolder(holder, position)
            else ->
                adapter.onBindViewHolder(holder, position, payloads)
        }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder.itemViewType) {
            Int.MAX_VALUE -> super.onViewRecycled(holder)
            else -> adapter.onViewRecycled(holder)
        }
    }

    fun notifyStatusChanged(status: FooterStatus.Status) {
        if (this.status == status) {
            return
        } else {
            this.status = status
        }
        this.notifyDataSetChanged()
    }

    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)!!

        fun applyStatus(status: FooterStatus.Status) {
            title.text = status.title
            title.backgroundColor = colorPair.first
            title.textColor = colorPair.second
            itemView.invalidate()
        }
    }
}