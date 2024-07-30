package com.example.familysafety

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d4d5.myfamily.MyFamilyDatabase
import com.example.familysafety.Model.ContactModel
import com.example.familysafety.Model.MemberModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {

   lateinit var InviteAdapter : InviteAdapter

    private val listContacts :ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberModel>(
            MemberModel(
                "Shailesh",
                "9th buildind, 2nd floor, maldiv road, manali 9th buildind, 2nd floor 9th buildind, 2nd floor,",
                "90%",
                "220M"
            ),
            MemberModel(
                "Vinayak",
                "9th buildind, 3nd floor, maldiv road, manali 9th buildind, 2nd floor 9th buildind, 3nd floor,",
                "80%",
                "210M"
            ),
            MemberModel(
                "Ketan",
                "9th buildind, 4nd floor, maldiv road, manali 9th buildind, 2nd floor 9th buildind, 4nd floor,",
                "70%",
                "200M"
            ),
            MemberModel(
                "Sachin",
                "9th buildind, 5nd floor, maldiv road, manali 9th buildind, 2nd floor 9th buildind, 4nd floor,",
                "60%",
                "190M"
            ),
        )

        val adapter = MemberAdapter(listMembers)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

         InviteAdapter = InviteAdapter(listContacts)

        fetchDatabaseContacts()

        CoroutineScope(Dispatchers.IO).launch {

            insertdatabaseContacts(fetchContacts())

        }

        val invitRecyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        invitRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        invitRecyclerView.adapter = InviteAdapter

    }

    private fun fetchDatabaseContacts() {

        val database= MyFamilyDatabase.getDatabase(requireContext())
         database.contactDao().getAllContacts().observe(viewLifecycleOwner){
             listContacts.clear()
             listContacts.addAll(it)

             InviteAdapter.notifyDataSetChanged()
         }
    }

    private fun insertdatabaseContacts(listContacts: ArrayList<ContactModel>) {
        val database= MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().insertAll(listContacts)
    }

    private fun fetchContacts(): ArrayList<ContactModel> {
        val cr=requireActivity().contentResolver
        val cursors=cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)

        val listContacts : ArrayList<ContactModel> = ArrayList( )

        if (cursors!=null && cursors.count>0){

            while (cursors!=null && cursors.moveToNext()) {
                val id = cursors.getString(cursors.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = cursors.getString(cursors.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhone = cursors.getInt(cursors.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhone>0){

                    val pCur=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",
                        arrayOf(id),"")

                    if (pCur!=null && pCur.count>0){

                        while (pCur!=null && pCur.moveToNext()){
                            val phoneNumValue=pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            listContacts.add(ContactModel(name,phoneNumValue))
                        }
                        pCur.close()
                    }
                }

            }
            if (cursors!=null){
                cursors.close()
            }
        }
        return listContacts
    }

    companion object {

        @JvmStatic
        fun newInstance() = Home()

    }
}