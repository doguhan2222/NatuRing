package com.example.naturing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.databinding.AdapterFreeRowBinding
import com.example.naturing.databinding.AdapterPremiumRowBinding


import com.example.naturing.viewmodels.AllRingtonesViewModel
//private val listener: CustomViewHolderListener,
class PremiumAdapterHomePage (viewModel: AllRingtonesViewModel): RecyclerView.Adapter<PremiumAdapterHomePage.DeveloperViewHolder>() {

    private var premiumRingtonesAdapterList: MutableList<AllRingtonesResponseModel>? = null

    var viewModel: AllRingtonesViewModel = viewModel
/*
    interface CustomViewHolderListener{
        fun onCustomItemClicked(x: String)
    }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val premiumRingtonesListItemBinding = DataBindingUtil.inflate<AdapterPremiumRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_premium_row, viewGroup, false
        )


        return DeveloperViewHolder(premiumRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        mDeveloperViewHolder.setIsRecyclable(false)
        val currentPremiumMusic = premiumRingtonesAdapterList!![i]

        mDeveloperViewHolder.premiumRingtonesListItemBinding.sFiyat.text = currentPremiumMusic.s_fiyat
        mDeveloperViewHolder.premiumRingtonesListItemBinding.sAdi.text = currentPremiumMusic.s_adi

        mDeveloperViewHolder.premiumRingtonesListItemBinding.playButtonSatir.setOnClickListener(View.OnClickListener {
            Log.e("ddd",premiumRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+premiumRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLAnasayfa.postValue(audioUrl)
        })
        mDeveloperViewHolder.premiumRingtonesListItemBinding.buyButtonSatirPremium.setOnClickListener(View.OnClickListener {
                viewModel.listeTiklananMuzikId.postValue(premiumRingtonesAdapterList!!.get(i).s_id)
            })
       /* mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
            listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
        })
*/


    }

    override fun getItemCount(): Int {
        return if (premiumRingtonesAdapterList != null) {
            if(premiumRingtonesAdapterList!!.size >=5){
                premiumRingtonesAdapterList!!.take(5).size
            }else{
                premiumRingtonesAdapterList!!.size
            }
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<AllRingtonesResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.premiumRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

    inner class DeveloperViewHolder(var premiumRingtonesListItemBinding: AdapterPremiumRowBinding) :
        RecyclerView.ViewHolder(premiumRingtonesListItemBinding.root)
}















