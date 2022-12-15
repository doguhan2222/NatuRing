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
import com.example.naturing.databinding.AdapterFreeRowBinding
import com.example.naturing.viewmodels.FreeRingtonesViewModel
import com.example.naturing.viewmodels.SearchScreenViewModel

class SearchScreenFreeAdapter (viewModel: SearchScreenViewModel): RecyclerView.Adapter<SearchScreenFreeAdapter.DeveloperViewHolder>() {

    private var searchFreeRingtonesAdapterList: MutableList<SearchScreenResponseModel>? = null

    var viewModel: SearchScreenViewModel = viewModel

    /*  interface CustomViewHolderListener{
          fun onCustomItemClicked(x: String)
      }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val searchFreeRingtonesListItemBinding = DataBindingUtil.inflate<AdapterFreeRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_free_row, viewGroup, false
        )

        return DeveloperViewHolder(searchFreeRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentAllFreeRingtones = searchFreeRingtonesAdapterList!![i]

        mDeveloperViewHolder.searchFreeRingtonesListItemBinding.sFiyat.text = currentAllFreeRingtones.s_fiyat
        mDeveloperViewHolder.searchFreeRingtonesListItemBinding.sAdi.text = currentAllFreeRingtones.s_adi

        mDeveloperViewHolder.searchFreeRingtonesListItemBinding.playButtonSatir.setOnClickListener(View.OnClickListener {
            //TODO("BUNU YAP BURADA")
            Log.e("ddd",searchFreeRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+searchFreeRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLSearch.postValue(audioUrl)
        })
        mDeveloperViewHolder.searchFreeRingtonesListItemBinding.dowloandButtonSatirFree.setOnClickListener(
            View.OnClickListener {
            var audioUrlIndir = "http://www.doguhanay.fun/sesler/"+searchFreeRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLUcretsizSearchIndir.postValue((audioUrlIndir))
        })

        /*  mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
              Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
              listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
          })
  */


    }

    override fun getItemCount(): Int {
        return if (searchFreeRingtonesAdapterList != null) {
            searchFreeRingtonesAdapterList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<SearchScreenResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.searchFreeRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

    inner class DeveloperViewHolder(var searchFreeRingtonesListItemBinding: AdapterFreeRowBinding) :
        RecyclerView.ViewHolder(searchFreeRingtonesListItemBinding.root)
}