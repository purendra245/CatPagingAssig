package com.catpaging.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catpaging.R
import com.catpaging.model.CatsResponse
import com.catpaging.ui.activity.CatImageActivity
import com.catpaging.ui.viewmodel.CatsViewModel
import com.catpaging.ui.adapter.CatListAdapter
import com.catpaging.ui.adapter.CatLoadStateAdapter
import com.example.mypaging.utils.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.catpaging.constants.IntentConstants


@ExperimentalPagingApi
@AndroidEntryPoint
class CatFragment : Fragment(R.layout.fragment_cat) , CatListAdapter.OnCellClickListener{

    lateinit var rvCat: RecyclerView
    @ExperimentalCoroutinesApi
    private val catViewModel: CatsViewModel by viewModels()
    lateinit var adapter: CatListAdapter
    lateinit var loaderStateAdapter: CatLoadStateAdapter

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecylerView()
        setUpRecycler(view)
        fetchCatImages()
    }

    @ExperimentalCoroutinesApi
    private fun fetchCatImages() {
        lifecycleScope.launch {
            catViewModel.fetchCatData().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initRecylerView() {
        val glide = GlideApp.with(this)
        adapter = CatListAdapter(glide)
        adapter.setOnClickListener(this)
        loaderStateAdapter = CatLoadStateAdapter (adapter)
    }

    private fun setUpRecycler(view: View) {
        rvCat = view.findViewById(R.id.rvCat)
        rvCat.layoutManager = GridLayoutManager(context, 3)
        rvCat.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    override fun onCellClicked(image: CatsResponse, view: View) {


        activity?.let{
            val intent = Intent (it, CatImageActivity::class.java)
            intent.putExtra(IntentConstants.CAT_EXTRA_URL,image.url)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view, "cat_image"
            )
            it.startActivity(intent,options.toBundle())
        }
    }
}