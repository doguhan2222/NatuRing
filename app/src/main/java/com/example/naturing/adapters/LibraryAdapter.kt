package com.example.naturing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.entities.LibraryResponseModel
import com.example.naturing.databinding.AdapterLibraryRowBinding
import com.example.naturing.databinding.AdapterPremiumRowBinding
import com.example.naturing.viewmodels.LibraryViewModel

class LibraryAdapter  (viewModel: LibraryViewModel): RecyclerView.Adapter<LibraryAdapter.DeveloperViewHolder>() {

    private var allLibraryRingtones: MutableList<LibraryResponseModel>? = null

    var viewModel: LibraryViewModel = viewModel

    /*  interface CustomViewHolderListener{
          fun onCustomItemClicked(x: String)
      }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val allLibraryRingtoesListItemBinding = DataBindingUtil.inflate<AdapterLibraryRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_library_row, viewGroup, false
        )

        return DeveloperViewHolder(allLibraryRingtoesListItemBinding)
    }


    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentPremiumFreeRingtones = allLibraryRingtones!![i]

        mDeveloperViewHolder.allLibraryRingtoesListItemBinding.sAdiLibrary.text = currentPremiumFreeRingtones.s_adi

        mDeveloperViewHolder.allLibraryRingtoesListItemBinding.playButtonSatirLibrary.setOnClickListener(
            View.OnClickListener {
            Log.e("ddd",allLibraryRingtones!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+allLibraryRingtones!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLKutuphaneDinle.postValue(audioUrl)
        })
        mDeveloperViewHolder.allLibraryRingtoesListItemBinding.dowloandButtonSatirLibrary.setOnClickListener(View.OnClickListener {
            var audioUrlIndir = "http://www.doguhanay.fun/sesler/"+allLibraryRingtones!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLKutuphaneIndir.postValue((audioUrlIndir))
        })

    }


    override fun getItemCount(): Int {
        return if (allLibraryRingtones != null) {
            allLibraryRingtones!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<LibraryResponseModel>) {
        this.allLibraryRingtones = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var allLibraryRingtoesListItemBinding: AdapterLibraryRowBinding) :
        RecyclerView.ViewHolder(allLibraryRingtoesListItemBinding.root)
}