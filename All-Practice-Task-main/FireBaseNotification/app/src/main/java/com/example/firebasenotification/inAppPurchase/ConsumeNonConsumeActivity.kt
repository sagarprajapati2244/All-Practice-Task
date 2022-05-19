package com.example.firebasenotification.inAppPurchase

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.billingclient.api.*
import com.example.firebasenotification.R
import com.example.firebasenotification.databinding.ActivityConsumeNonconsumeBinding

class ConsumeNonConsumeActivity : AppCompatActivity(), PurchasesUpdatedListener {
    private lateinit var binding:ActivityConsumeNonconsumeBinding
    private lateinit var billingClient: BillingClient
    private val skuList:MutableList<String> =ArrayList()
    private val list:MutableList<SkuDetails> = ArrayList()
    private val strWeek = "Per Purchase \t"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_consume_nonconsume)

        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(this)
            .build()

        establishConnection()
    }

    private fun establishConnection() {
        billingClient.startConnection(object :BillingClientStateListener{
            override fun onBillingServiceDisconnected() {
                Toast.makeText(this@ConsumeNonConsumeActivity, "Your Service is Disconnected!!", Toast.LENGTH_SHORT).show()
            }

            override fun onBillingSetupFinished(p0: BillingResult) {
                val queryPurchase = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
                val queryPurchases = queryPurchase.purchasesList
                if (queryPurchases != null && queryPurchases.size > 0) {
                    handlePurchase(queryPurchases)
                } else {
                    initPurchase()
                }
            }

        })
    }


    private fun initPurchase() {
        skuList.add(CREDITS10)
        skuList.add(CREDITS20)
        skuList.add(CREDITS50)
        skuList.add(CREDITS100)
        skuList.add(CREDITS200)
        skuList.add(CREDITS500)
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)
        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK)
            {
                if (skuDetailsList!=null)
                {
                    list.addAll(skuDetailsList)
                }
                if (list.isNotEmpty()) {
                    //This Function Is Specially use for background service reflect on Ui Screen
                    runOnUiThread {
                        forLoop(list)
                        Log.e("listData", "Msg")
                    }

                }

            }
            else
            {
                Toast.makeText(this, "Purchase Is Not Found!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun forLoop(list: MutableList<SkuDetails>) {
        for (i in list) {
            when (i.sku) {
                CREDITS10 -> {
                    binding.btnConsume10.text = strWeek.plus(i.price)
                    binding.btnConsume10.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                CREDITS20 -> {
                    binding.btnConsume20.text = strWeek.plus(i.price)
                    binding.btnConsume20.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                CREDITS50 ->{
                    binding.btnConsume50.text = strWeek.plus(i.price)
                    binding.btnConsume50.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                CREDITS100 ->{
                    binding.btnConsume100.text = strWeek.plus(i.price)
                    binding.btnConsume100.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                CREDITS200 ->{
                    binding.btnConsume200.text = strWeek.plus(i.price)
                    binding.btnConsume200.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
                CREDITS500 ->{
                    binding.btnConsume500.text = strWeek.plus(i.price)
                    binding.btnConsume500.setOnClickListener {
                        launchPurchaseFlow(i)
                    }
                }
            }
        }
    }

    private fun launchPurchaseFlow(i: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(i)
            .build()
        billingClient.launchBillingFlow(this, flowParams)
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        if (p0.responseCode == BillingClient.BillingResponseCode.OK && p1 != null) {
            handlePurchase(p1)
        }
        else if (p0.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchaseResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
            val alreadyPurchases = queryAlreadyPurchaseResult.purchasesList
            Log.e("alreadyList", alreadyPurchases.toString())
            handlePurchase(alreadyPurchases!!)
            Toast.makeText(this, "Purchase Already", Toast.LENGTH_SHORT).show()
        } else if (p0.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(this, "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Purchase Update Error : - ${p0.debugMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun handlePurchase(queryPurchases: List<Purchase>) {
        for (purchase in queryPurchases) {
            val pToken = purchase.skus[0]
            Log.e("pToken", pToken.toString())
            val pTok = purchase.orderId
            when (purchase.purchaseState) {
                Purchase.PurchaseState.PURCHASED -> {
                    when (pToken) {
                        CREDITS10 -> {
                                runOnUiThread {
                                    binding.btnConsume10.text = pTok
                                    binding.btnConsume10.setBackgroundColor(Color.GRAY)
                                    binding.btnConsume10.isClickable = false
                                }

                                Toast.makeText(this, "Item Consume $CREDITS10", Toast.LENGTH_SHORT).show()
                        }
                        CREDITS20 ->{
                            runOnUiThread {
                                binding.btnConsume20.text = pTok
                                binding.btnConsume20.setBackgroundColor(Color.DKGRAY)
                                binding.btnConsume20.isClickable = false
                            }
                            Toast.makeText(this, "Item Consume $CREDITS20", Toast.LENGTH_SHORT).show()
                        }
                        CREDITS50 ->{
                            runOnUiThread {
                                binding.btnConsume50.text = pTok
                                binding.btnConsume50.setBackgroundColor(Color.DKGRAY)
                                binding.btnConsume50.isClickable = false
                            }
                            Toast.makeText(this, "Item Consume $CREDITS50", Toast.LENGTH_SHORT).show()
                        }
                        CREDITS100 ->{
                            runOnUiThread {
                                binding.btnConsume100.text = pTok
                                binding.btnConsume100.setBackgroundColor(Color.DKGRAY)
                                binding.btnConsume100.isClickable = false
                            }
                            Toast.makeText(this, "Item Consume $CREDITS100", Toast.LENGTH_SHORT).show()
                        }
                        CREDITS200 ->{
                            runOnUiThread {
                                binding.btnConsume200.text = pTok
                                binding.btnConsume200.setBackgroundColor(Color.DKGRAY)
                                binding.btnConsume200.isClickable = false
                            }
                            Toast.makeText(this, "Item Consume $CREDITS200", Toast.LENGTH_SHORT).show()
                        }
                        CREDITS500 ->{
                            runOnUiThread {
                                binding.btnConsume500.text = pTok
                                binding.btnConsume500.setBackgroundColor(Color.DKGRAY)
                                binding.btnConsume500.isClickable = false
                            }
                            Toast.makeText(this, "Item Consume $CREDITS500", Toast.LENGTH_SHORT).show()
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
            val consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            billingClient.consumeAsync(consumeParams, consumeListener)
        }
    }
    private var consumeListener = ConsumeResponseListener { billingResult, s ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            Toast.makeText(applicationContext, "Item Consumed$s", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val CREDITS10 = "com.second.phone.number.text.now.sideline_2ndline.credits10"
        const val CREDITS20 = "com.second.phone.number.text.now.sideline_2ndline.credits20"
        const val CREDITS50 = "com.second.phone.number.text.now.sideline_2ndline.credits50"
        const val CREDITS100 = "com.second.phone.number.text.now.sideline_2ndline.credits100"
        const val CREDITS200 = "com.second.phone.number.text.now.sideline_2ndline.credits200"
        const val CREDITS500 = "com.second.phone.number.text.now.sideline_2ndline.credits500"

    }
}