package com.example.smarthulumente

import android.app.ProgressDialog.show
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.material.navigation.NavigationView
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import java.nio.channels.AsynchronousFileChannel.open

class SplashActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private val TAG = "SplashActivity"

    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        MobileAds.initialize(this)
        bannerAd()


        val button: Button = findViewById<Button>(R.id.login)

        button.setOnClickListener{
            val intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
        }

        val button2: Button = findViewById<Button>(R.id.signUpSplash)

        button2.setOnClickListener{
            val intent = Intent( this, SingupActivity::class.java)
            startActivity(intent)
        }

        val button3: Button = findViewById<Button>(R.id.loginMe)

        button3.setOnClickListener{
            val intent = Intent( this, LogInActivity::class.java)
            startActivity(intent)
        }

        drawerLayout = findViewById(R.id.drawlayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

             when(it.itemId){

                R.id.nav_home -> replaceFragment(HomeFragment(),it.title.toString())
                R.id.nav_message -> replaceFragment(MessageFragment(),it.title.toString())
                R.id.nav_sync -> Toast.makeText(applicationContext,"Clicked Sync",Toast.LENGTH_SHORT).show()
                R.id.nav_trash -> replaceFragment(DeleteFragment(),it.title.toString())
                R.id.nav_setting -> replaceFragment(SettingFragment(),it.title.toString())
                R.id.nav_login -> replaceFragment(LogoutFragment(),it.title.toString())
                R.id.nav_share -> Toast.makeText(applicationContext,"Clicked Share",Toast.LENGTH_SHORT).show()



            }
            true

        }


    }

    private fun bannerAd() {
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d(TAG, "Ad loaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, "Ad failed to laod")
            }

            override fun onAdOpened() {
                Log.d(TAG,"Ad opened")
            }

            override fun onAdClicked() {
                Log.d(TAG,"Ad Clicked")
            }

            fun onAdLeftApplication(){
                Log.d(TAG,"User left app on ad")
            }

            override fun onAdClosed() {
                Log.d(TAG, "Ad Closed")
            }

        }
    }


    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true

        }
        return super.onOptionsItemSelected(item)
    }

}