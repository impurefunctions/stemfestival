package org.gdggaborone.stemfestival2019.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth

import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.Utils
import org.gdggaborone.stemfestival2019.models.MessageModel

import java.util.ArrayList
import java.util.Date

import com.bumptech.glide.request.RequestOptions.bitmapTransform

/**
 * Created by dan on 23/06/17.
 */

class MessageAdapter(private val mContext: Context, private val messages: ArrayList<MessageModel>?) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {




    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        var viewHolder: ViewHolder? = null
        when (i) {
            LEFT -> viewHolder = ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_left, viewGroup, false))
            RIGHT -> viewHolder = ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_right, viewGroup, false))
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        Glide.with(mContext)
                .asBitmap()
                .load(messages!![i].profileImg)
                .apply(bitmapTransform(CircleCrop()))
                .into(viewHolder.profilePictureImageView)

        viewHolder.usernameTextView.text = messages[i].username
        viewHolder.timeStampTextView.text = Utils.getDate(Date(messages[i].timestamp))
        viewHolder.messageTextView.text = messages[i].message
    }

    override fun getItemCount(): Int {
        return messages?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages!![position].userId == uid) RIGHT else LEFT
    }
    companion object {

        private const val LEFT = 0
        private const val RIGHT = 1

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var timeStampTextView: TextView = itemView.findViewById(R.id.timeStampTextView)
        var usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        var messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        var profilePictureImageView: ImageView = itemView.findViewById(R.id.profilePictureImageView)

    }


}
