package com.example.naturing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.databinding.AdapterPremiumRowBinding
import com.example.naturing.viewmodels.PremiumRingtonesViewModel

class PremiumAllAdapter (viewModel: PremiumRingtonesViewModel): RecyclerView.Adapter<PremiumAllAdapter.DeveloperViewHolder>() {

    private var allPremiumRingtonesAdapterList: MutableList<AllRingtonesResponseModel>? = null

    var viewModel: PremiumRingtonesViewModel = viewModel

    /*  interface CustomViewHolderListener{
          fun onCustomItemClicked(x: String)
      }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val allPremiumRingtonesListItemBinding = DataBindingUtil.inflate<AdapterPremiumRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_premium_row, viewGroup, false
        )

        return DeveloperViewHolder(allPremiumRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentPremiumFreeRingtones = allPremiumRingtonesAdapterList!![i]

        mDeveloperViewHolder.allPremiumRingtonesListItemBinding.sFiyat.text = currentPremiumFreeRingtones.s_fiyat
        mDeveloperViewHolder.allPremiumRingtonesListItemBinding.sAdi.text = currentPremiumFreeRingtones.s_adi

        mDeveloperViewHolder.allPremiumRingtonesListItemBinding.playButtonSatir.setOnClickListener(View.OnClickListener {
            //TODO("BUNU YAP BURADA")
            Log.e("ddd",allPremiumRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+allPremiumRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLUcretliTamliste.postValue(audioUrl)
        })
        mDeveloperViewHolder.allPremiumRingtonesListItemBinding.buyButtonSatirPremium.setOnClickListener(View.OnClickListener {
            viewModel.listeTiklananMuzikId.postValue(allPremiumRingtonesAdapterList!!.get(i).s_id)
        })

        /*  mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
              Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
              listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
          })
  */


        //TODO("PREMIUM VE FREE ADAPTOR TASARIMINI DUZELT,INDIRME ISLEVI EKLE , ÖDEME SAYFASI YAP VE KITAPLIK EKLE")
    }


    override fun getItemCount(): Int {
        return if (allPremiumRingtonesAdapterList != null) {
            allPremiumRingtonesAdapterList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<AllRingtonesResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.allPremiumRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

    inner class DeveloperViewHolder(var allPremiumRingtonesListItemBinding: AdapterPremiumRowBinding) :
        RecyclerView.ViewHolder(allPremiumRingtonesListItemBinding.root)
}