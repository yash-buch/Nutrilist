package com.binc.nutrilist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binc.nutrilist.viewmodels.RestrictionListViewModel
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.restriction_item.view.*

class UserProfileActivity : AppCompatActivity() {
    lateinit var rvRestrictions: RecyclerView
    lateinit var viewModel: RestrictionListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        viewModel = ViewModelProviders.of(this)
            .get(RestrictionListViewModel::class.java)

        rvRestrictions = findViewById(R.id.rvRestrictions)
        rvRestrictions.layoutManager = LinearLayoutManager(this)
        rvRestrictions.adapter = RestrictionsAdapter(viewModel, this@UserProfileActivity)
    }

    fun saveUserProfile(view: View) {
        //validate age
        //validate sex
        //validate doctor - check if handled by inputType
    }

    fun addRestriction(view: View) {
        var etRestriction = etRestriction
        var restriction: String = etRestriction.text.toString()
        viewModel.addToList(restriction)
        rvRestrictions.adapter?.notifyDataSetChanged()
    }

    class RestrictionsAdapter(val vm: RestrictionListViewModel, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        lateinit var recyclerView: RecyclerView

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view: View = LayoutInflater.from(context).inflate(R.layout.restriction_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return vm.getList().size
        }

        override fun onAttachedToRecyclerView(rv: RecyclerView) {
            super.onAttachedToRecyclerView(rv)
            recyclerView = rv
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = holder as ViewHolder
            viewHolder?.restrictionName?.text = vm.getList().get(position)
            viewHolder?.delBtn.setOnClickListener {
                //should delete this item
                vm.removeFromList(position)
                recyclerView.removeViewAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, vm.getList().size)

            }
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val restrictionName = view.tvRestriction
        val delBtn = view.btnDel
    }
}
