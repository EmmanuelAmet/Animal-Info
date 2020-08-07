package com.emmanuelamet.animal_info.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.emmanuelamet.animal_info.R
import com.emmanuelamet.animal_info.model.Animal
import com.emmanuelamet.animal_info.viewmodel.ListViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>>{
        list -> list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean>{
        isLoading -> loadingView.visibility = if(isLoading)  View.VISIBLE else View.GONE
        if(isLoading){
            listError.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLoadingLiveDataObserver = Observer<Boolean>{
        isError -> listError.visibility = if(isError)  View.VISIBLE    else    View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animal.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLoadingLiveDataObserver)

        viewModel.refresh()
        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> { share() }
            R.id.feedback -> {launch("mailto:feedback.app.developer@gmail.com?subject=%20Animal%20%20Info%20App%20Feedback")}
            R.id.rate_app -> {launch("https://play.google.com/store/apps/details?id=com.emmanuelamet.animal_info")}
            R.id.more_apps -> {launch("https://play.google.com/store/apps/developer?id=EMMANUEL+AMETEPEE")}

        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "Hi there, Get the new Animal Info App to get the latest information about animals" +
                    " from Google Play Store via the link " +
                    "below:\n\nhttps://play.google.com/store/apps/details?id=com.emmanuelamet.animal_info")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun launch(link:String){
        val url = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonDetail.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
     */

}