package com.d4d5.myfamily

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.familysafety.Model.ContactModel

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(contactModel: ContactModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(contactModelList: List<ContactModel>)

    @Query("SELECT * FROM ContactModel")
    fun getAllContacts(): LiveData<List<ContactModel>>
}
