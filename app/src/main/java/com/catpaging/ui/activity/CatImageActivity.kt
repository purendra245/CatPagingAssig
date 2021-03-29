package com.catpaging.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.RequestManager
import com.catpaging.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_image.*
import javax.inject.Inject
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.catpaging.constants.IntentConstants


@AndroidEntryPoint
class CatImageActivity : AppCompatActivity() {

    @field:[Inject]
    lateinit var glide: RequestManager


    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        supportPostponeEnterTransition()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra(IntentConstants.CAT_EXTRA_URL)) {
            intent.getStringExtra(IntentConstants.CAT_EXTRA_URL)?.let { loadInto(it) }
        }


    }

    private fun loadInto( url:String) {
        if (url.startsWith("http")) {
            imgViewCat.visibility = View.VISIBLE
            glide.load(url)
                .placeholder(R.drawable.ic_cat_half_transparent)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        scheduleStartPostponedTransition(imgViewCat);
                        return false
                    }
                })
                .into(imgViewCat)
        } else {
            imgViewCat.visibility = View.GONE
            glide.clear(imgViewCat)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        supportFinishAfterTransition()
        onBackPressed()
        return true
    }

    private fun scheduleStartPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return true
                }
            })
    }



}





