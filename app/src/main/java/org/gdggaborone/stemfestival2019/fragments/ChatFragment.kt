package org.gdggaborone.stemfestival2019.fragments


import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.adapters.MessageAdapter
import org.gdggaborone.stemfestival2019.models.MessageModel
import java.util.*



/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment(), View.OnClickListener {


    private var db: FirebaseFirestore? = null
    private val messages: ArrayList<MessageModel> = ArrayList()
    private var messageAdapter: MessageAdapter? = null

    private lateinit var inputEditText: TextInputEditText
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private var progressBar: ProgressBar? = null
    private lateinit var editTextString: String

    init {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        db!!.firestoreSettings = settings

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        db = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar!!.visibility = View.VISIBLE


        recyclerView = view.findViewById(R.id.chat_recyclerview)
        fab = view.findViewById(R.id.sendFloatingActionButton)
        inputEditText = view.findViewById(R.id.messageTextInputEditText)
        recyclerView.layoutManager = LinearLayoutManager(context)
        messageAdapter = MessageAdapter(context!!, messages)
        recyclerView.adapter = messageAdapter
        fab.setOnClickListener(this)
        loadFirestoreData()
        return view
    }

    private fun loadFirestoreData() {

        db?.collection(Constants.CHATS)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (documentSnapshot in task.result) {
                    val messageModel = documentSnapshot.toObject(MessageModel::class.java)
                    messages.add(0, messageModel)
                    messageAdapter!!.notifyDataSetChanged()
                    progressBar!!.visibility = View.GONE

                }
            }
        }
    }

    private fun saveFirestoreData() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val username = FirebaseAuth.getInstance().currentUser!!.displayName
        val guid = FirebaseAuth.getInstance().currentUser!!.providerData[0].email!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        val message =  inputEditText.text.toString()
        //val timestamp = snapshot.getTimestamp("created_at")
       // val date = timestamp.toDate()
        val profileImg = FirebaseAuth.getInstance().currentUser!!.photoUrl!!.toString()
        val messageModel = MessageModel(Date().toString(), username!!, uid, message, guid, profileImg)

        db?.collection(Constants.CHATS)?.add(messageModel)?.addOnSuccessListener {
            Log.d(TAG, "Message from  ${messageModel.username} delivered")
            recyclerView.scrollToPosition(0)
            closeKeyboard()
            inputEditText.text?.clear()
            messageAdapter?.notifyDataSetChanged()
        }?.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
            inputEditText.setText(message)
        }


    }

    override fun onClick(v: View) {
        editTextString = inputEditText.text.toString()
        when (v.id) {

            R.id.sendFloatingActionButton -> if (editTextString.trim { it <= ' ' }.isNotEmpty()) {
                saveFirestoreData()
            } else {
                Toast.makeText(context, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun closeKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    companion object {
        private const val TAG = "ChatFragment"
    }
}
