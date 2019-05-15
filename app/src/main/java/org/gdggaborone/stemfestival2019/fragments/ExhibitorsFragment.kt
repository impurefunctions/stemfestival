package org.gdggaborone.stemfestival2019.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.firebase.database.*
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.adapters.ExhibitorAdapter
import org.gdggaborone.stemfestival2019.models.ExhibitorModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ExhibitorsFragment : Fragment() {

    private val mList: ArrayList<ExhibitorModel> = ArrayList()
    private var exhibitorAdapter: ExhibitorAdapter? = null
    private var progressBar: ProgressBar? = null

    init {
        // Required empty public constructor
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //String speakerId = getArguments().getString(SPEAKER_ID_KEY);

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_exhibitor, container, false)

        progressBar = view.findViewById(R.id.progressBar)

        exhibitorAdapter = ExhibitorAdapter(context!!, mList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = exhibitorAdapter

        loadData()

        // Inflate the layout for this fragment
        return view
    }


    internal fun loadData() {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val speakers = firebaseDatabase.getReference(Constants.SPEAKERS)
        speakers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressBar!!.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressBar!!.visibility = View.GONE
            }
        })
        speakers.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val speakerModel = dataSnapshot.getValue(ExhibitorModel::class.java)
                mList.add(speakerModel!!)
                exhibitorAdapter!!.notifyDataSetChanged()
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

    companion object {
        private val SPEAKER_ID_KEY = "speaker_id"
    }

}
