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
import com.example.naturing.viewmodels.AllRingtonesViewModel

//private val listener: CustomViewHolderListener,
class FreeAdapterHomePage(viewModel: AllRingtonesViewModel): RecyclerView.Adapter<FreeAdapterHomePage.DeveloperViewHolder>() {

    private var freeRingtonesAdapterList: MutableList<AllRingtonesResponseModel>? = null

    var viewModel:AllRingtonesViewModel = viewModel

  /*  interface CustomViewHolderListener{
        fun onCustomItemClicked(x: String)
    }
*/
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val freeRingtonesListItemBinding = DataBindingUtil.inflate<AdapterFreeRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_free_row, viewGroup, false
        )

        return DeveloperViewHolder(freeRingtonesListItemBinding)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        //tekrarlanan veriyi engellemek için
        mDeveloperViewHolder.setIsRecyclable(false)

        val currentFreeMusic = freeRingtonesAdapterList!![i]

        mDeveloperViewHolder.freeRingtonesListItemBinding.sFiyat.text = currentFreeMusic.s_fiyat
        mDeveloperViewHolder.freeRingtonesListItemBinding.sAdi.text = currentFreeMusic.s_adi

        mDeveloperViewHolder.freeRingtonesListItemBinding.playButtonSatir.setOnClickListener(View.OnClickListener {
            Log.e("ddd",freeRingtonesAdapterList!!.get(i).s_yolu)
            var audioUrl = "http://www.doguhanay.fun/sesler/"+freeRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLAnasayfa.postValue(audioUrl)
        })
        mDeveloperViewHolder.freeRingtonesListItemBinding.dowloandButtonSatirFree.setOnClickListener(View.OnClickListener {
            var audioUrlIndir = "http://www.doguhanay.fun/sesler/"+freeRingtonesAdapterList!!.get(i).s_yolu
            viewModel.listeTiklananMuzikURLUcretsizAnasayfaIndir.postValue((audioUrlIndir))
            })
       /* mDeveloperViewHolder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("bbb",mDeveloperModel!!.get(i).s_yolu)
            listener.onCustomItemClicked(mDeveloperModel!!.get(i).s_yolu)
        })
*/


    }

    override fun getItemCount(): Int {
        return if (freeRingtonesAdapterList != null) {
            if(freeRingtonesAdapterList!!.size >=5){
                freeRingtonesAdapterList!!.take(5).size
            }else{
                freeRingtonesAdapterList!!.size
            }

        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<AllRingtonesResponseModel>) {
        Log.e("denem11",mDeveloperModel.toString())
        this.freeRingtonesAdapterList = mDeveloperModel
        Log.e("denem111",mDeveloperModel.toString())
        notifyDataSetChanged()
        Log.e("denem1111",mDeveloperModel.toString())
    }

     inner class DeveloperViewHolder(var freeRingtonesListItemBinding: AdapterFreeRowBinding) :
        RecyclerView.ViewHolder(freeRingtonesListItemBinding.root)
}



























/*:  RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var mDeveloperModel: MutableList<AllRingtonesResponseModel>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<AdapterRowBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.adapter_row, viewGroup, false
        )

        return DeveloperViewHolder(mDeveloperListItemBinding)
    }


    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = mDeveloperModel!![i]
        mDeveloperViewHolder.mDeveloperListItemBinding.AllRingtonesResponseModel = currentStudent


    }

    override fun getItemCount(): Int {
        return if (mDeveloperModel != null) {
            mDeveloperModel!!.size
        } else {
            0
        }
    }

    fun setDeveloperList(mDeveloperModel: MutableList<AllRingtonesResponseModel>) {
        this.mDeveloperModel = mDeveloperModel
        notifyDataSetChanged()
    }

    inner class DeveloperViewHolder(var mDeveloperListItemBinding: RowListItemBinding) :
        RecyclerView.ViewHolder(mDeveloperListItemBinding.root)
}*/