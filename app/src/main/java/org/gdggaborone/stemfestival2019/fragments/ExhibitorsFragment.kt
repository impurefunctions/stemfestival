package org.gdggaborone.stemfestival2019.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_exhibitor.*
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.adapters.ExhibitorAdapter
import org.gdggaborone.stemfestival2019.models.SpeakerModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ExhibitorsFragment : Fragment() {

    private val mList: ArrayList<SpeakerModel> = ArrayList()
    private var exhibitorAdapter: ExhibitorAdapter? = null
    private var progressBar: ProgressBar? = null
    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var comingSoon: TextView

    init {
        // Required empty public constructor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        firestoreDB = FirebaseFirestore.getInstance()

        val view = inflater.inflate(R.layout.fragment_exhibitor, container, false)

        progressBar = view.findViewById(R.id.progressBar)

        exhibitorAdapter = ExhibitorAdapter(context!!, mList)
        comingSoon = view.findViewById(R.id.coming_soon_txt)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = exhibitorAdapter
        progressBar!!.visibility = View.GONE
        comingSoon.visibility = View.GONE
        loadDataFirestore()





        // Inflate the layout for this fragment
        return view
    }

    private fun loadDataFirestore(){
        firestoreDB.collection(Constants.EXHIBITORS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                coming_soon_txt!!.visibility = View.GONE
                for (documentSnapshot in task.result!!) {
                    val exhibitorModel = documentSnapshot.toObject(SpeakerModel::class.java)
                    mList.add(exhibitorModel)
                    if (mList.isNotEmpty()){
                        exhibitorAdapter!!.notifyDataSetChanged()
                        progressBar!!.visibility = View.GONE
                    }else{
                        progressBar!!.visibility = View.GONE
                        coming_soon_txt!!.visibility = View.VISIBLE
                    }
            }


            }
        }

    }

    companion object {
        private val SPEAKER_ID_KEY = "speaker_id"
    }

}
