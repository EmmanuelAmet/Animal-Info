package com.emmanuelamet.animal_info.view

import android.database.DatabaseUtils
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emmanuelamet.animal_info.R
import com.emmanuelamet.animal_info.databinding.FragmentDetailBinding
import com.emmanuelamet.animal_info.databinding.FragmentDetailBinding.inflate
import com.emmanuelamet.animal_info.model.Animal
import com.emmanuelamet.animal_info.model.AnimalPalette
import com.emmanuelamet.animal_info.util.getProgressDrawable
import com.emmanuelamet.animal_info.util.loadImage
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    var animal : Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            mInterstitialAd = InterstitialAd(it)
            mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }


        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        /*
        context?.let {
            dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }
         */

        dataBinding.animal = animal
        /*
        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalDiet.text = animal?.diet
        animalLifespan.text = animal?.lifeSpan

         */
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
                            dataBinding.palette = AnimalPalette(intColor)
                            //dataBinding.animalDetailLayout.setBackgroundColor(intColor)
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