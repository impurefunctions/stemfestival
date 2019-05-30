package org.gdggaborone.stemfestival2019.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.activities.SessionInfoActivity
import org.gdggaborone.stemfestival2019.models.SpeakerModel
import org.gdggaborone.stemfestival2019.models.ScheduleModel
import org.gdggaborone.stemfestival2019.models.SessionModel
import java.util.*

/**
 * Created by dan on 07/10/17.
 */

class ScheduleAdapter(private val mContext: Context, private val sessionModels: ArrayList<SessionModel>, private val speakerModels: ArrayList<SpeakerModel>, private val scheduleModels: ArrayList<ScheduleModel>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.session_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val sessionModel = sessionModels[position]

        val speakerId = sessionModel.speakers!![0]

        val scheduleModel = scheduleModels[position]
        val speakerModel = speakerModels[speakerId - 1]

        val tag = sessionModel.tags!![0]
        holder.tagsTextView.text = tag
        when (tag) {
            "Android" -> holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.android))
            "Cloud" -> holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.cloud))
            "Web" -> holder.bottomBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.web))
        }

        if (sessionModel.presentation != null) {
            holder.presentationAvailableTextView.visibility = View.VISIBLE
        } else {
            holder.presentationAvailableTextView.visibility = View.GONE
        }

        if (sessionModel.description != null) {
            holder.descriptionTextView.text = sessionModel.description
        } else {
            holder.descriptionTextView.visibility = View.GONE
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(mContext, SessionInfoActivity::class.java)
            intent.putExtra("id", sessionModel.id.toString())
            intent.putExtra("sid", speakerId.toString())
            intent.putExtra("tid", position.toString())
            mContext.startActivity(intent)
        }

        holder.schedulesTimes.text = String.format("%s - %s", scheduleModel.startTime, scheduleModel.endTime)
        holder.speakerNameTextView.text = speakerModel.name
        holder.locationTextView.text = speakerModel.country
        holder.titleTextView.text = sessionModel.title
        holder.languageAndComplexityTextView.text = String.format("%s / %s", sessionModel.language, sessionModel.complexity)

    }

    override fun getItemCount(): Int {
        return sessionModels.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardView: CardView = itemView.findViewById(R.id.cardView)
        var bottomBg: RelativeLayout = itemView.findViewById(R.id.bottomBg)
        var speakerNameTextView: TextView = itemView.findViewById(R.id.speakerNameTextView)
        var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        var languageAndComplexityTextView: TextView = itemView.findViewById(R.id.languageAndComplexityTextView)
        var tagsTextView: TextView = itemView.findViewById(R.id.tagsTextView)
        var presentationAvailableTextView: TextView = itemView.findViewById(R.id.presentationAvailableTextView)
        var locationTextView: TextView = itemView.findViewById(R.id.locationTV)
        var descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        var schedulesTimes: TextView = itemView.findViewById(R.id.schedules_times)

    }

}
