package org.gdggaborone.stemfestival2019.models

import android.arch.lifecycle.ViewModel
import java.util.*

/**
 * Created by dan on 07/10/17.
 */

class ExhibitorModel : ViewModel {

    var id: Int = 0
    var name: String? = null
    var gdgName: String? = null
    var interests: String? = null
    var bio: String? = null
    var photoUrl: String? = null
    var shortBio: String? = null
    var title: String? = null
    var country: String? = null
    var company: String? = null
    var socials: ArrayList<SocialModel>? = null
    var tags: ArrayList<String>? = null

    constructor() {}

    constructor(id: Int, name: String, gdgName: String, interests: String, bio: String, photoUrl: String, shortBio: String, title: String, country: String, company: String, socials: ArrayList<SocialModel>, tags: ArrayList<String>) {
        this.id = id
        this.name = name
        this.gdgName = gdgName
        this.interests = interests
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
