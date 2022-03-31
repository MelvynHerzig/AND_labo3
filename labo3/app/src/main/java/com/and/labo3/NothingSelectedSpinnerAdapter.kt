package com.and.labo3

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter


/**
 * Decorator Adapter to allow a Spinner to show a 'Sélectionner' initially
 * displayed instead of the first choice in the Adapter.
 * Adapted from source: https://stackoverflow.com/a/12221309
 */
class NothingSelectedSpinnerAdapter(
    private val adapter: SpinnerAdapter,
    private val nothingSelectedLayout: Int,
    private val context: Context?
) :
    SpinnerAdapter {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // This provides the View for the Selected Item in the Spinner, not
        // the dropdown (unless dropdownView is not set).
        return if (position == 0) {
            layoutInflater.inflate(nothingSelectedLayout, parent, false)
        } else {
            adapter.getView(position - EXTRA, convertView, parent)
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Android BUG! http://code.google.com/p/android/issues/detail?id=17128 -
        // Spinner does not support multiple view types
        return if (position == 0) {
            View(context)
        } else {
            adapter.getDropDownView(position - EXTRA, null, parent)
        }
    }


    override fun getCount(): Int {
        val count = adapter.count
        return if (count == 0) 0 else count + EXTRA
    }

    override fun getItem(position: Int): Any {
        return (if (position == 0) null else adapter.getItem(position - EXTRA))!!
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return if (position >= EXTRA) adapter.getItemId(position - EXTRA) else (position - EXTRA).toLong()
    }

    override fun hasStableIds(): Boolean {
        return adapter.hasStableIds()
    }

    override fun isEmpty(): Boolean {
        return adapter.isEmpty
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        adapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        adapter.unregisterDataSetObserver(observer)
    }

    companion object {
        protected const val EXTRA = 1
    }
}