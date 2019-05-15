package org.gdggaborone.stemfestival2019.models

/**
 * Created by dan on 23/06/17.
 */

class MessageModel {

    var timestamp: String? = null
    var username: String? = null
    var userId: String? = null
    private val postId: String? = null
    /* public String getPostId() {
        return postId;
    }*/

    /* public void setPostId(String postId) {
        this.postId = postId;
    }*/

    var message: String? = null
    var gplusId: String? = null
    var profileImg: String? = null

    // Empty Constructor Required
    constructor() {}

    constructor(timestamp: String, username: String, userId: String, message: String, gplusId: String, profileImg: String) {
        this.timestamp = timestamp
        this.username = username
        this.userId = userId
        //this.postId = postId;
        this.message = message
        this.gplusId = gplusId
        this.profileImg = profileImg
    }
}

