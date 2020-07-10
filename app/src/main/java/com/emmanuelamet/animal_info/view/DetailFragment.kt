package com.emmanuelamet.animal_info.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emmanuelamet.animal_info.R
import com.emmanuelamet.animal_info.model.Animal
import com.emmanuelamet.animal_info.util.getProgressDrawable
import com.emmanuelamet.animal_info.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    var animal : Animal? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }
        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalDiet.text = animal?.diet
        animalLifespan.text = animal?.lifeSpan

        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }

    }

    private fun setUpBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate(){
                            palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            animalDetailLayout.setBackgroundColor(intColor)
                        }
                }

            })
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonList.setOnClickListener {
            val action = DetailFragmentDirections.actionActionDetailToListFragment2()
            Navigation.findNavController(it).navigate(action)
        }
    }

     */

}