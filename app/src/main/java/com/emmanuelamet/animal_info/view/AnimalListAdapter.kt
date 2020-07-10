package com.emmanuelamet.animal_info.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.emmanuelamet.animal_info.R
import com.emmanuelamet.animal_info.databinding.ItemAnimalBinding
import com.emmanuelamet.animal_info.model.Animal
import com.emmanuelamet.animal_info.util.getProgressDrawable
import com.emmanuelamet.animal_info.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    class AnimalViewHolder(val view: ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        /*
        holder.view.animalName.text = animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))
         */
        holder.view.animal = animalList[position]
        holder.view.listener = this
//        holder.view.animalLayout.setOnClickListener {
//            var action = ListFragmentDirections.actionListFragmentToDetailFragment(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }
    }

    override fun onClick(v: View) {
        for(animal in animalList){
            if(v.tag == animal.name){
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(animal)
            Navigation.findNavController(v).navigate(action)
            }
        }
    }
}