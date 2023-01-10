package com.example.naturing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.SearchScreenResponseModel
import com.example.naturing.databinding.AdapterPremiumRowBinding
import com.example.naturing.viewmodels.PremiumRingtonesViewModel
import com.example.naturing.viewmodels.SearchScreenViewModel

class SearchScreenPremiumAdapter (viewModel: SearchScreenViewModel): RecyclerView.Adapter<SearchScreenPremiumAdapter.DeveloperViewHolder>() {

    private var searchPremiumRingtonesAdapterList: MutableList<SearchScreenResponseModel>? = null

    var viewModel: SearchScreenViewModel = viewModel

    /*  interface CustomViewHolderListener{
          fun onCustomItemClicked(x: String)
      }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val searchPremiumRingtonesListItemBinding = DataBindingUtil.inflate<AdapterPremiumRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_premium_row, viewGroup, false
        )

        return DeveloperViewHolder(searchPremiumRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentPremiumFreeRingtones = searchPremiumRingtonesAdapterList!![i]

        mDeveloperViewHolder.searchPremiumRingtonesListItemBinding.sFiyat.text = currentPremiumFreeRingtones.s_fiyat
        mDeveloperViewHolder.searchPremiumRingtonesListItemBinding.sAdi.text = currentPremiumFreeRingtones.s_adi

        mDeveloperViewHolder.searchPremiumRingtonesListItemBinding.playButtonSatir.setOnClickListener(
            View.OnClickListener {

            Log.e("ddd",searchPremiumRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+searchPremiumRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLSearch.postValue(audioUrl)
        })

        /*  mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
              Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
              listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
          })
  */



    }


    override fun getItemCount(): Int {
        return if (searchPremiumRingtonesAdapterList != null) {
            searchPremiumRingtonesAdapterList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<SearchScreenResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.searchPremiumRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

    inner class DeveloperViewHolder(var searchPremiumRingtonesListItemBinding: AdapterPremiumRowBinding) :
        RecyclerView.ViewHolder(searchPremiumRingtonesListItemBinding.root)
}