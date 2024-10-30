package com.example.grandin

import com.example.grandin.Api.SheetDataItem

data class ExcelResponse(
    val message: String,
    val data: List<SheetDataItem>
)

data class ExcelFileData(
    val excelfileID: Int,
    val excelfile: String,
    val createdBy: String,
    val createdOn: String,
    val execelData : List<AgentData>
)



data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: AgentData  // Renamed "Data" to "data" to follow Kotlin naming conventions
)

data class ApiData(
    val name: String,
    val dateandtime: String,
    val comment: String,
    val assigned: String,
    val scheduled: String,
    val interested: String,
    val notinterested: String
)

data class AgentData(
//    val agentID: Int,
//    val name: String,
//    val email: String,
    val username: String,
    val password: String
//    val role: Int,
//    val createdBy: String,
//    val createdOn: String
)



data class Business(
    val name: String,
    val contactNumber: String,
    val emailId: String,
    val company: String,
    val website: String,
    val businessType: String
)