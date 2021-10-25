package com.caitlykate.randomusersdagger2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.randomusersdagger2.databinding.ListItemRandomUserBinding
import com.caitlykate.randomusersdagger2.model.Result
import com.squareup.picasso.Picasso
import java.lang.String

class RandomUserAdapter (private val picasso: Picasso): RecyclerView.Adapter<RandomUserAdapter.ResultsHolder>() {
    private val resultList = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsHolder {
        val binding =  ListItemRandomUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultsHolder(binding, picasso)
    }

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
        holder.setData(resultList[position])
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    fun updateAdapter(newList: List<Result>) {
        //resultList.clear()
        resultList.addAll(newList)
        notifyDataSetChanged()
    }


    class ResultsHolder(private val binding: ListItemRandomUserBinding, private val picasso: Picasso) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(result: Result) = with(binding) {

            name.text = String.format("%s %s", result.name.first, result.name.last)
            picasso.load(result.picture.large).into(imageView)


        }
    }

}