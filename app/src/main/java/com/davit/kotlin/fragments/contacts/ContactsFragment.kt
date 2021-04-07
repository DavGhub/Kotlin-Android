package com.davit.kotlin.fragments.contacts

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.davit.kotlin.fragments.R
import com.davit.kotlin.fragments.databinding.FragmentContactsBinding


class ContactsFragment : Fragment(), ContactsAdapter.ContactClickListener {

    lateinit var binding:FragmentContactsBinding
    private val fragmentPermissionHelper = FragmentPermissionHelper()
    private lateinit var contactAdapter:ContactsAdapter
    private val contactList:MutableList<Contact> = ArrayList()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentPermissionHelper.startPermissionRequest(this,object :FragmentPermissionInterface {
            override fun onGranted(isGranted: Boolean) {
                if (isGranted) {
                    readContacts()
                    Toast.makeText(requireActivity(),"Granted",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(),"Not Granted",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.main_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)

        binding.readContacts.setOnClickListener{
            fragmentPermissionHelper.lunchPermission(Manifest.permission.READ_CONTACTS)
        }

        binding.addContact.setOnClickListener {
            addContact(Contact("Գարեգին Նժդեհ","011223344",""))
        }

        binding.removeContact.setOnClickListener {
            removeContact()
        }

        return binding.root
    }

    private fun readContacts(){
        val phones: Cursor? = activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,null,null,null)

        if(phones != null){
            if(contactList.isNotEmpty()) contactList.clear()
            while (phones.moveToNext()){
                val name:String = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number:String = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val photoUri:String? = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                val contact = Contact(name,number,photoUri)
                contactList.add(contact)
                Log.e("Cursor","Contact:$contact")
            }

             initAdapter(contactList)
        }
    }

    private fun initAdapter(list:MutableList<Contact>){
        contactAdapter = ContactsAdapter(requireActivity(),list)
        contactAdapter.setOnContactClickListener(this)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.contactsRecyclerview.layoutManager = layoutManager
        binding.contactsRecyclerview.adapter = contactAdapter
    }

    private fun addContact(contact: Contact){
        contactAdapter.addContact(contact)
        binding.contactsRecyclerview.scrollToPosition(0)
    }

    private fun removeContact(){
        contactAdapter.removeContact(0)
    }

    override fun onContactClick(contact: Contact) {
        val action = ContactsFragmentDirections.actionContactsFragmentToContactDetailFragment(contact)
        navController.navigate(action)
    }
}