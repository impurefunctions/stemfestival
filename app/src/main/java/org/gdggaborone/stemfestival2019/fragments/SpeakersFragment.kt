package org.gdggaborone.stemfestival2019.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.firebase.firestore.FirebaseFirestore
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.adapters.SpeakerAdapter
import org.gdggaborone.stemfestival2019.models.SpeakerModel
import java.util.*

class SpeakersFragment : Fragment() {

    private val mList: ArrayList<SpeakerModel>
    private var speakerAdapter: SpeakerAdapter? = null
    private var progressBar: ProgressBar? = null

    init {
        // Required empty public constructor
        mList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_exhibitor, container, false)

        progressBar = view.findViewById(R.id.progressBar)

        speakerAdapter = SpeakerAdapter(context, mList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = speakerAdapter

        loadData()

        // Inflate the layout for this fragment
        return view
    }

    private fun loadData() {
        val db = FirebaseFirestore.getInstance()

        db.collection(Constants.SPEAKERS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (documentSnapshot in task.result) {
                    val speakerModel = documentSnapshot.toObject(SpeakerModel::class.java)
                    Log.d("Speakers", "${speakerModel.name} added")
                    mList.add(speakerModel)
                    speakerAdapter!!.notifyDataSetChanged()
                    progressBar!!.visibility = View.GONE

                }
            }
        }

    }

}
