package com.example.firebasenotification.inAppPurchase

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.billingclient.api.*
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivitySubscriptionBinding
import com.example.firebasenotification.inAppPurchase.SubscriptionActivity.SubSub.MONTHLY
import com.example.firebasenotification.inAppPurchase.SubscriptionActivity.SubSub.WEEKLY
import com.example.firebasenotification.inAppPurchase.SubscriptionActivity.SubSub.YEARLY


class SubscriptionActivity : AppCompatActivity(), PurchasesUpdatedListener {

    private lateinit var binding: ActivitySubscriptionBinding
    private lateinit var billingClient: BillingClient
    private val skuList: MutableList<String> = ArrayList()
    private val list: MutableList<SkuDetails> = ArrayList()
    val strWeek = "Per Weekly"
    val strMonth = "Per Monthly"
    val strYear = "Per Yearly"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)

        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(this)
            .build()

        establishConnection()

    }

    private fun establishConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Toast.makeText(
                    this@SubscriptionActivity,
                    "Connection Is Lost!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onBillingSetupFinished(p0: BillingResult) {
                val queryPurchase = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
                val queryPurchases = queryPurchase.purchasesList
                if (queryPurchases != null && queryPurchases.size > 0) {
                    handlePurchase(queryPurchases)
                } else {
                    initPurchase()
                }
            }

        })
    }

    val listener: PurchaseHistoryResponseListener =
        PurchaseHistoryResponseListener { _, p1 ->
            if (p1 != null) {
                for (purchase in p1) {
                    if (purchase.skus.equals(WEEKLY)) {
                        val purchaseTime = purchase.purchaseTime
                        binding.btnWeekly.text = purchaseTime.toString()
                        // boolean expired = purchaseTime + period < now
                    }
                }
            }
        }

    private fun initPurchase() {
        skuList.add(WEEKLY)
        skuList.add(MONTHLY)
        skuList.add(YEARLY)
        Log.e("skuList", skuList.toString())
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.SUBS)
        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (skuDetailsList != null) {
                    list.addAll(skuDetailsList)
                }
                Log.e("List", list.toString())


                if (list.isNotEmpty()) {
                    //This Function Is Specially use for background service reflect on Ui Screen
                    runOnUiThread {
                        forLoop(list)
                        Log.e("listData", "Msg")
                    }

                }
            } else {
                Toast.makeText(this, "Content Is Not Found!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun forLoop(list: MutableList<SkuDetails>) {
        for (i in list) {
            when (i.sku) {
                WEEKLY -> {
                    binding.btnWeekly.text = strWeek.plus(i.price)
                    binding.btnWeekly.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                MONTHLY -> {
                    binding.btnMonthly.text = strMonth.plus(i.price)
                    binding.btnMonthly.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                YEARLY -> {
                    binding.btnYearly.text = strYear.plus(i.price)
                    binding.btnYearly.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
            }
        }
    }

    private fun launchPurchaseFlow(i: SkuDetails?) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(i!!)
            .build()
        billingClient.launchBillingFlow(this, flowParams)
//        handlePurchase(queryList)
    }


    override fun onPurchasesUpdated(p0: BillingResult, p1: List<Purchase>?) {
        if (p0.responseCode == BillingClient.BillingResponseCode.OK && p1 != null) {
            handlePurchase(p1)
        } else if (p0.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchaseResult =
                billingClient.queryPurchases(BillingClient.SkuType.SUBS)
            val alreadyPurchases = queryAlreadyPurchaseResult.purchasesList
            Log.e("alreadyList", alreadyPurchases.toString())
            handlePurchase(alreadyPurchases!!)
            Toast.makeText(this, "Purchase Already", Toast.LENGTH_SHORT).show()
        } else if (p0.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(this, "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Purchase Update Error : - ${p0.debugMessage}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun handlePurchase(queryPurchases: List<Purchase>?) {
        for (purchase in queryPurchases!!) {
            when (purchase.purchaseState) {
                Purchase.PurchaseState.PURCHASED -> {
                    val pToken = purchase.skus[0]
                    Log.e("pToken", pToken.toString())
                    val pTok = purchase.orderId

                    when (pToken) {
                        WEEKLY -> {
                            runOnUiThread {
                                binding.btnWeekly.text = pTok
                                binding.btnWeekly.setBackgroundColor(Color.CYAN)
                                binding.btnWeekly.isEnabled = false
                            }
                            if (pToken != MONTHLY || pToken != YEARLY) {
                                if (pToken != MONTHLY || pToken != YEARLY) {
                                    billingClient.startConnection(object :
                                        BillingClientStateListener {
                                        override fun onBillingServiceDisconnected() {
                                            Toast.makeText(
                                                this@SubscriptionActivity,
                                                "Connection Is Lost!!!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        override fun onBillingSetupFinished(p0: BillingResult) {
                                            skuList.add(WEEKLY)
                                            skuList.add(MONTHLY)
                                            skuList.add(YEARLY)
                                            val params = SkuDetailsParams.newBuilder()
                                                .setSkusList(skuList)
                                                .setType(BillingClient.SkuType.SUBS)
                                            billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
                                                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                                    if (skuDetailsList != null) {
                                                        list.addAll(skuDetailsList)
                                                    }
                                                    Log.e("List", list.toString())
                                                    for (i in list) {
                                                        when (i.sku) {
                                                            MONTHLY -> {
                                                                    runOnUiThread {
                                                                        binding.btnMonthly.text =
                                                                            strMonth.plus(i.price)
                                                                        binding.btnMonthly.setOnClickListener {
                                                                            launchPurchaseFlow(i)
                                                                        }
                                                                }

                                                            }
                                                            YEARLY -> {
                                                                runOnUiThread {
                                                                    binding.btnYearly.text =
                                                                        strYear.plus(i.price)
                                                                    binding.btnYearly.setOnClickListener {
                                                                        launchPurchaseFlow(i)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }


                                                }
                                            }
                                        }
                                    })
                                }

                            }
                        }
                        MONTHLY -> {
                            runOnUiThread {
                                binding.btnMonthly.text = pTok
                                binding.btnMonthly.setBackgroundColor(Color.GREEN)
                                binding.btnMonthly.isEnabled = false
                            }
                            if (pToken != WEEKLY || pToken != YEARLY) {
                                billingClient.startConnection(object : BillingClientStateListener {
                                    override fun onBillingServiceDisconnected() {
                                        Toast.makeText(
                                            this@SubscriptionActivity,
                                            "Connection Is Lost!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onBillingSetupFinished(p0: BillingResult) {
                                        skuList.add(WEEKLY)
                                        skuList.add(MONTHLY)
                                        skuList.add(YEARLY)
                                        val params = SkuDetailsParams.newBuilder()
                                            .setSkusList(skuList)
                                            .setType(BillingClient.SkuType.SUBS)
                                        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
                                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                                if (skuDetailsList != null) {
                                                    list.addAll(skuDetailsList)
                                                }
                                                Log.e("List", list.toString())
                                                for (i in list) {
                                                    when (i.sku) {
                                                        WEEKLY -> {
                                                                runOnUiThread {
                                                                    binding.btnWeekly.text =
                                                                        strWeek.plus(i.price)
                                                                    binding.btnWeekly.setOnClickListener {
                                                                        launchPurchaseFlow(i)
                                                                    }
                                                            }
                                                        }
                                                        YEARLY -> {
                                                            runOnUiThread {
                                                                binding.btnYearly.text =
                                                                    strYear.plus(i.price)
                                                                binding.btnYearly.setOnClickListener {
                                                                    launchPurchaseFlow(i)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }


                                            }
                                        }
                                    }
                                })
                            }

                        }
                        YEARLY -> {
                            runOnUiThread {
                                binding.btnYearly.text = pTok
                                binding.btnYearly.setBackgroundColor(Color.YELLOW)
                                binding.btnYearly.isEnabled = false
                            }
                            if (pToken != WEEKLY || pToken != MONTHLY) {
                                billingClient.startConnection(object : BillingClientStateListener {
                                    override fun onBillingServiceDisconnected() {
                                        Toast.makeText(
                                            this@SubscriptionActivity,
                                            "Connection Is Lost!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onBillingSetupFinished(p0: BillingResult) {
                                        skuList.add(WEEKLY)
                                        skuList.add(MONTHLY)
                                        skuList.add(YEARLY)
                                        val params = SkuDetailsParams.newBuilder()
                                            .setSkusList(skuList)
                                            .setType(BillingClient.SkuType.SUBS)
                                        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
                                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                                if (skuDetailsList != null) {
                                                    list.addAll(skuDetailsList)
                                                }
                                                Log.e("List", list.toString())
                                                for (i in list) {
                                                    when (i.sku) {
                                                        WEEKLY -> {
                                                            runOnUiThread {
                                                                binding.btnWeekly.text =
                                                                    strWeek.plus(i.price)
                                                                binding.btnWeekly.setOnClickListener {
                                                                    launchPurchaseFlow(i)
                                                                }
                                                            }
                                                        }
                                                        MONTHLY -> {
                                                            runOnUiThread {
                                                                binding.btnMonthly.text =
                                                                    strMonth.plus(i.price)
                                                                binding.btnMonthly.setOnClickListener {
                                                                    launchPurchaseFlow(i)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }


                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }

                }
                Purchase.PurchaseState.PENDING -> {
                    Toast.makeText(this,
                        "Purchase Is Pending ,Please Complete Transaction",
                        Toast.LENGTH_SHORT).show()
                }
                Purchase.PurchaseState.UNSPECIFIED_STATE -> {
                    Toast.makeText(this, "Purchase Status Unknown", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Handle Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

//    private var ackPurchase = AcknowledgePurchaseResponseListener { billingResult ->
//        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//            recreate()
//        }
//    }

    private object SubSub {
        const val WEEKLY = "com.test.app.sku0098abc.weekly"
        const val MONTHLY = "com.test.app.sku0098abc.monthly"
        const val YEARLY = "com.test.app.sku0098abc.yearly"



    }

}



















