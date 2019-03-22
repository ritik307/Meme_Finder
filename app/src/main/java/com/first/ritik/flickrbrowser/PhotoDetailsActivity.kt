package com.first.ritik.flickrbrowser

import android.os.Bundle
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activeToolbar(true  )

        val photo = intent.extras.getParcelable("PHOTO_TRANSFER") as Photo

        photo_title.text = photo.title
        photo_tag.text = photo.tag
        photo_author.text = photo.author

        Picasso.get().load(photo.link)
            .error(R.drawable.baseline_terrain_black_48dp)
            .placeholder(R.drawable.baseline_terrain_black_48dp)
            .into(photo_image)
    }

}
