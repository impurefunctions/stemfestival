package org.gdggaborone.stemfestival2019.models

/**
 * Created by dan on 07/10/17.
 */

class SocialModel {

    var name: String? = null
    var link: String? = null
    var icon: String? = null

    constructor() {}

    constructor(name: String, link: String, icon: String) {
        this.name = name
        this.link = link
        this.icon = icon
    }
}
