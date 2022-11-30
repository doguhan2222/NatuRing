package com.example.naturing.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.naturing.R
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.databinding.AdapterRowBinding
import com.example.naturing.viewmodels.FreeRingtonesViewModel

//private val listener: CustomViewHolderListener,
class FreeAllAdapter (viewModel: FreeRingtonesViewModel): RecyclerView.Adapter<FreeAllAdapter.DeveloperViewHolder>() {

    private var allFreeRingtonesAdapterList: MutableList<AllRingtonesResponseModel>? = null

    var viewModel: FreeRingtonesViewModel = viewModel

  /*  interface CustomViewHolderListener{
        fun onCustomItemClicked(x: String)
    }*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val allFreeRingtonesListItemBinding = DataBindingUtil.inflate<AdapterRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_row, viewGroup, false
        )

        return DeveloperViewHolder(allFreeRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentAllFreeRingtones = allFreeRingtonesAdapterList!![i]

        mDeveloperViewHolder.allFreeRingtonesListItemBinding.sId.text = currentAllFreeRingtones.s_id
        mDeveloperViewHolder.allFreeRingtonesListItemBinding.sAdi.text = currentAllFreeRingtones.s_adi

        mDeveloperViewHolder.allFreeRingtonesListItemBinding.playButtonSatir.setOnClickListener(View.OnClickListener {
            //TODO("BUNU YAP BURADA")
            Log.e("ddd",allFreeRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+allFreeRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLUcretsizTamliste.postValue(audioUrl)
        })
      /*  mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
            listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
        })
*/


    }

    override fun getItemCount(): Int {
        return if (allFreeRingtonesAdapterList != null) {
            allFreeRingtonesAdapterList!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<AllRingtonesResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.allFreeRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

    inner class DeveloperViewHolder(var allFreeRingtonesListItemBinding: AdapterRowBinding) :
        RecyclerView.ViewHolder(allFreeRingtonesListItemBinding.root)
}