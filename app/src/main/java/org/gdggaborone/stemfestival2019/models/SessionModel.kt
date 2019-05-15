package org.gdggaborone.stemfestival2019.models

import java.util.ArrayList

/**
 * Created by dan on 07/10/17.
 */

class SessionModel {

    var title: String? = null
    var description: String? = null
    var language: String? = null
    var presentation: String? = null
    var complexity: String? = null
    var speakers: ArrayList<Int>? = null
    var tags: ArrayList<String>? = null
    var id: Int = 0

    constructor() {}

    constructor(title: String, description: String, language: String, presentation: String,
                complexity: String, speakers: ArrayList<Int>, tags: ArrayList<String>, id: Int) {
        this.title = title
        this.description = description
        this.language = language
        this.presentation = presentation
        this.complexity = complexity
        this.speakers = speakers
        this.tags = tags
        this.id = id
    }

}
