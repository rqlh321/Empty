package com.example.empty.database.dao

import androidx.room.Dao
import com.example.empty.database.entity.User

@Dao
interface UserDao : BaseDao<User>