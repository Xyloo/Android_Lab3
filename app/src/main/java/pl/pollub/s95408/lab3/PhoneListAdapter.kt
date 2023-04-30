package pl.pollub.s95408.lab3

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PhoneListAdapter() : ListAdapter<Phone, PhoneListAdapter.PhoneViewHolder>(PhoneComparator())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder
    {
        return PhoneViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int)
    {
        val current = getItem(position)
        holder.bind(current.manufacturer, current.model)
        val intent = Intent(holder.itemView.context, NewPhoneActivity::class.java)
        intent.putExtra("id", current.id)
        intent.putExtra("manufacturer", current.manufacturer)
        intent.putExtra("model", current.model)
        intent.putExtra("os_version", current.os_version)
        intent.putExtra("website", current.website)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(intent)
        }

    }

    class PhoneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        private val manufacturerItemView = itemView.findViewById<TextView>(R.id.manufacturerTextView)
        private val modelItemView = itemView.findViewById<TextView>(R.id.modelTextView)

        fun bind(manufacturer: String?, model: String?)
        {
            manufacturerItemView.text = manufacturer
            modelItemView.text = model
        }

        companion object
        {
            fun create(parent: ViewGroup): PhoneViewHolder
            {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.phone_row, parent, false)
                return PhoneViewHolder(view)
            }
        }
    }

    class PhoneComparator: DiffUtil.ItemCallback<Phone>()
    {
        override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean
        {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean
        {
            return oldItem.id == newItem.id
        }
    }

}