package org.gdggaborone.stemfestival2019.models

/**
 * Created by dan on 08/10/17.
 */

class ScheduleModel {

    var startTime: String? = null
    var endTime: String? = null

    constructor() {}

    constructor(startTime: String, endTime: String) {
        this.startTime = startTime
        this.endTime = endTime
    }
}
