package org.gdggaborone.stemfestival2019.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.models.ScheduleModel
import org.gdggaborone.stemfestival2019.models.SessionModel
import org.gdggaborone.stemfestival2019.models.ExhibitorModel
import kotlinx.android.synthetic.main.activity_session_info.*

class SessionInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_info)

        val id = intent.getStringExtra("id")
        val sid = intent.getStringExtra("sid")
        val tid = intent.getStringExtra("tid")

        loadData(id, sid, tid)

    }

    private fun loadData(id: String, sid: String, tid: String) {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val schedule = firebaseDatabase.getReference(Constants.SCHEDULES).child(id)
        val sessionTimes = firebaseDatabase.getReference(Constants.SCHEDULES_TIMES)
                .child("0").child("timeslots").child(tid)
        val speaker = firebaseDatabase.getReference(Constants.SPEAKERS).child(sid)

        sessionTimes.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val scheduleModel = dataSnapshot.getValue(ScheduleModel::class.java)
                schedules_times!!.text = String.format("%s - %s", scheduleModel!!.startTime, scheduleModel.endTime)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        speaker.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val speakerModel = dataSnapshot.getValue(ExhibitorModel::class.java)

                speakerNameTextView!!.text = speakerModel!!.name
                locationTextView!!.text = speakerModel.country
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        schedule.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val sessionModel = dataSnapshot.getValue(SessionModel::class.java)

                val tag = sessionModel!!.tags!![0]
                tagsTextView!!.text = tag
                when (tag) {
                    "Android" -> bottomBg!!.setBackgroundColor(ContextCompat.getColor(this@SessionInfoActivity, R.color.android))
                    "Cloud" -> bottomBg!!.setBackgroundColor(ContextCompat.getColor(this@SessionInfoActivity, R.color.cloud))
                    "Web" -> bottomBg!!.setBackgroundColor(ContextCompat.getColor(this@SessionInfoActivity, R.color.web))
                }

                if (sessionModel.presentation != null) {
                    presentationAvailableTextView!!.visibility = View.VISIBLE
                } else {
                    presentationAvailableTextView!!.visibility = View.GONE
                }
                //72144431
                if (sessionModel.description != null) {
                    descriptionTextView!!.text = sessionModel.description
                } else {
                    descriptionTextView!!.visibility = View.GONE
                }
                titleTextView!!.text = sessionModel.title
                languageAndComplexityTextView!!.text = sessionModel.language + " / " + sessionModel.complexity

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    fun back() {
        finish()
    }
}
