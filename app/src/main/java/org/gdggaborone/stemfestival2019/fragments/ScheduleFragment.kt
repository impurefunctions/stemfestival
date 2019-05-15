package org.gdggaborone.stemfestival2019.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.firebase.database.*
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.adapters.ScheduleAdapter
import org.gdggaborone.stemfestival2019.models.ExhibitorModel
import org.gdggaborone.stemfestival2019.models.ScheduleModel
import org.gdggaborone.stemfestival2019.models.SessionModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment : Fragment() {

    private val sessionModels: ArrayList<SessionModel> = ArrayList()
    private val exhibitorModels: ArrayList<ExhibitorModel> = ArrayList()
    private val scheduleModels: ArrayList<ScheduleModel> = ArrayList()
    private var scheduleAdapter: ScheduleAdapter? = null
    private val progressBar: ProgressBar? = null

    init {
        // Required empty public constructor

    }
    //For now we will make this point to a blank page, the one we just created!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        scheduleAdapter = ScheduleAdapter(context!!, sessionModels, exhibitorModels, scheduleModels)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_no_data, container, false)
    }

    internal fun loadData() {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val schedules = firebaseDatabase.getReference(Constants.SCHEDULES)
        val sessionTimes = firebaseDatabase.getReference(Constants.SCHEDULES_TIMES).child("0").child(Constants.SCHEDULES_TIMES)
        val speakers = firebaseDatabase.getReference(Constants.SPEAKERS)


        sessionTimes.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                speakers.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        schedules.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                progressBar!!.visibility = View.GONE
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                progressBar!!.visibility = View.GONE
                            }
                        })
                        schedules.addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                                val sessionModel = dataSnapshot.getValue(SessionModel::class.java)
                                sessionModels.add(sessionModel!!)
                                scheduleAdapter!!.notifyDataSetChanged()
                            }

                            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                            }

                            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                            }

                            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

                            }

                            override fun onCancelled(databaseError: DatabaseError) {

                            }
                        })
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        progressBar!!.visibility = View.GONE
                    }
                })
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        sessionTimes.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val scheduleModel = dataSnapshot.getValue(ScheduleModel::class.java)
                scheduleModels.add(scheduleModel!!)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        speakers.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val speakerModel = dataSnapshot.getValue(ExhibitorModel::class.java)
                exhibitorModels.add(speakerModel!!)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

}
