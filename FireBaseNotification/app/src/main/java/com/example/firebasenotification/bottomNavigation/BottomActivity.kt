package com.example.firebasenotification.bottomNavigation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.firebasenotification.R
import com.example.firebasenotification.R.id.*
import com.example.firebasenotification.bottomNavigation.fragments.NotificationFragment
import com.example.firebasenotification.bottomNavigation.fragments.ObjectBoxFragment
import com.example.firebasenotification.bottomNavigation.fragments.SQLiteFragment
import com.example.firebasenotification.databinding.ActivityBottomBinding
import com.google.android.material.navigation.NavigationBarView


class BottomActivity(private var TAG: String = "") : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityBottomBinding
    private var titleTag: String = ""
    private var notificationFragment = NotificationFragment()
    private var sQLiteFragment = SQLiteFragment()
    private var objectBoxFragment = ObjectBoxFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom)
        setSupportActionBar(binding.toolbar)
        supportFragmentManager.beginTransaction().add(frame, notificationFragment).commit()
        binding.btmAll.menu.getItem(0).isChecked = true
        binding.btmAll.setOnItemSelectedListener(this)
    }

    fun checkActiveState(tag: String) {
        when (tag) {
            "Notification" -> {
                binding.btmAll.menu.getItem(0).isChecked = true
            }
            "Sqlite Database" -> {
                binding.btmAll.menu.getItem(1).isChecked = true
            }
            "Object Box Db" -> {
                binding.btmAll.menu.getItem(2).isChecked = true
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            finish()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                Log.e("backstackDiscard", supportFragmentManager.backStackEntryCount.toString())
            }
        }

    }

    private fun setTitle(tag: String) {
        titleTag = tag
        binding.toolbar.title = titleTag
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        var fragments = fragment
        TAG = tag
        val transaction = supportFragmentManager.beginTransaction()
        val curFrag: Fragment? = supportFragmentManager.primaryNavigationFragment
        val cacheFrag = supportFragmentManager.findFragmentByTag(TAG)
        if (cacheFrag != null) {
            Log.d("cache", cacheFrag.tag.toString())
        }
        if (curFrag != null)
            transaction.hide(curFrag)
        if (cacheFrag == null) transaction.replace(frame, fragments, TAG)
            else {
            transaction.show(cacheFrag)
            fragments = cacheFrag
        }
        transaction.setPrimaryNavigationFragment(fragments)
        transaction.addToBackStack(TAG)
        transaction.commit()
        Log.e("backstack", supportFragmentManager.backStackEntryCount.toString())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.e("backstackEnter", supportFragmentManager.backStackEntryCount.toString())
        when (item.itemId) {
            bNotification -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(frame, notificationFragment)
                    .addToBackStack(Fragment::class.java.simpleName)
                    .commit()
                return true
            }
            bSqliteDb -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(frame, sQLiteFragment)
                    .addToBackStack(Fragment::class.java.simpleName)
                    .commit()
                return true
            }
            bObjectBoxDb -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(frame, objectBoxFragment)
                    .addToBackStack(Fragment::class.java.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }
}










































//                val index: Int = supportFragmentManager.backStackEntryCount -1
//                val backEntry: FragmentManager.BackStackEntry =
//                    supportFragmentManager.getBackStackEntryAt(index)
//                val stackId: Int = backEntry.id
//                binding.btmAll.menu.getItem(stackId).isChecked = true
//                val fragments = supportFragmentManager.fragments
//                for (fragment in fragments) {
//                    if (fragment != null && fragment.isVisible) {
//                        selectedFragment = fragment
//                        Log.e("frag",selectedFragment.toString())
//                        break
//                    }
//                }
//                if (notificationFragment is NotificationFragment) {
//                    binding.btmAll.selectedItemId = bNotification;
//                }
//                if (sQLiteFragment is SQLiteFragment) {
//                    binding.btmAll.selectedItemId = bSqliteDb
//                }
//                if (objectBoxFragment is ObjectBoxFragment) {
//                    binding.btmAll.selectedItemId = bObjectBoxDb
//                }
//                when {
//                    notificationFragment.isResumed -> {
//                        binding.btmAll.menu.getItem(0).isChecked = true
//                    }
//                    sQLiteFragment.isResumed -> {
//                        binding.btmAll.menu.getItem(1).isChecked = true
//                    }
//                    objectBoxFragment.isResumed -> {
//                        binding.btmAll.menu.getItem(2).isChecked = true
//                    }
//                }