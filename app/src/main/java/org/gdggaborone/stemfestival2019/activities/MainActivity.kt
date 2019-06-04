package org.gdggaborone.stemfestival2019.activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.crashlytics.android.Crashlytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.*
import org.gdggaborone.stemfestival2019.Constants
import org.gdggaborone.stemfestival2019.R
import org.gdggaborone.stemfestival2019.fragments.ChatFragment
import org.gdggaborone.stemfestival2019.fragments.ExhibitorsFragment
import org.gdggaborone.stemfestival2019.fragments.ScheduleFragment
import org.gdggaborone.stemfestival2019.fragments.SpeakersFragment
import org.gdggaborone.stemfestival2019.models.ExhibitorModel
import org.gdggaborone.stemfestival2019.models.MessageModel
import org.gdggaborone.stemfestival2019.models.SessionModel
import org.gdggaborone.stemfestival2019.models.SpeakerModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var firestoreDB: FirebaseFirestore
    val exhibitorList: ArrayList<ExhibitorModel> = ArrayList()
    val speakerList: ArrayList<SpeakerModel> = ArrayList()
    val chatList: ArrayList<MessageModel> = ArrayList()
    val sessionList: ArrayList<SessionModel> = ArrayList()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        // fragment = null
        when (item.itemId) {
            R.id.navigation_schedule -> fragment = ScheduleFragment()
            R.id.navigation_exhibitors -> fragment = ExhibitorsFragment()
            R.id.navigation_chat -> fragment = ChatFragment()
            R.id.navigation_speakers -> fragment = SpeakersFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
        return@OnNavigationItemSelectedListener true
        /// false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this,Crashlytics())


        if (FirebaseAuth.getInstance().currentUser == null) {
            //finish()
            startActivity(Intent(this@MainActivity, GoogleSignInActivity::class.java))
        }

        firestoreDB = FirebaseFirestore.getInstance()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)

        if (savedInstanceState == null) {
            fragment = ScheduleFragment()
            supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
            navigation.selectedItemId = R.id.navigation_schedule
        }

        profilePictureImageView.setOnClickListener { launchDialog() }

        val uri = if (FirebaseAuth.getInstance().currentUser != null) FirebaseAuth.getInstance().currentUser!!.photoUrl else null

        Glide.with(this)
                .asBitmap()
                .load(uri)
                .apply(bitmapTransform(CircleCrop()))
                .into(profilePictureImageView)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivity(0)
        System.exit(0)
    }

    private fun launchDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        val viewGroup = findViewById<ViewGroup>(R.id.container)
        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, viewGroup, false)


        view.logout_button_d.setOnClickListener {
           // finish()
            val intent = Intent(this@MainActivity, GoogleSignInActivity::class.java)
            intent.putExtra("logout", "zzz")
            startActivity(intent)
        }
        builder.setTitle("Credits and stuff")
        builder.setView(view)
        builder.setNegativeButton("Ok", null)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun launchMeetup() {
        startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.meetup.com/GDG-Gaborone/events/252870386/")))
    }

    fun loadAllData(){
        firestoreDB.collection(Constants.CHATS)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        for (documentSnapshot in task.result!!) {
                            val messageModel = documentSnapshot.toObject(MessageModel::class.java)
                            chatList.add(0, messageModel)

                        }
                    }
                }
        firestoreDB.collection(Constants.SPEAKERS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (documentSnapshot in task.result!!) {
                    val speakerModel = documentSnapshot.toObject(SpeakerModel::class.java)
                    Log.d("Speakers", "${speakerModel.name} added")
                    speakerList.add(speakerModel)

                }
            }
        }

        firestoreDB.collection(Constants.EXHIBITORS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (documentSnapshot in task.result!!) {
                    val exhibitorModel = documentSnapshot.toObject(ExhibitorModel::class.java)
                    Log.d("Speakers", "${exhibitorModel.name} added")
                    exhibitorList.add(exhibitorModel)

                }
            }
        }




    }
}
