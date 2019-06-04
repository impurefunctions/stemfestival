package org.gdggaborone.stemfestival2019.models

import android.arch.lifecycle.ViewModel
import java.util.*

/**
 * Created by dan on 07/10/17.
 */

class SpeakerModel : ViewModel {

    var id: Int = 0
    var name: String? = null
    var bio: String? = null
    var photoUrl: String? = "https://firebasestorage.googleapis.com/v0/b/devfestbotswana.appspot.com/o/stem_logo.png?alt=media&token=52eb063f-b28c-4328-82c0-cc7931ca0628"
    var shortBio: String? = null
    var title: String? = null
    var country: String? = ""
    var company: String? = null
    var socials: ArrayList<SocialModel>? = null
    var tags: List<String>? = null

    constructor() {}

    constructor(id: Int, name: String,  bio: String, photoUrl: String, shortBio: String, title: String, country: String, company: String, socials: ArrayList<SocialModel>, tags: List<String>) {
        this.id = id
        this.name = name
        this.bio = bio
        this.photoUrl = photoUrl
        this.shortBio = shortBio
        this.title = title
        this.country = country
        this.company = company
        this.socials = socials
        this.tags = tags
    }
}
