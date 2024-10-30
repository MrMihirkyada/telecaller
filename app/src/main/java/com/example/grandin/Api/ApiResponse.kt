package com.example.grandin.Api

import com.google.gson.annotations.SerializedName

data class ApiResponse(

	@field:SerializedName("sheetData")
	val sheetData: List<SheetDataItem?>?,

	@field:SerializedName("data")
	val data: Data?,

	@field:SerializedName("message")
	val message: String?
)

data class Data(

	@field:SerializedName("excelfileID")
	val excelfileID: Int?,

	@field:SerializedName("excelfile")
	val excelfile: String?,

	@field:SerializedName("createdBy")
	val createdBy: String?,

	@field:SerializedName("createdOn")
	val createdOn: String?
)

data class SheetDataItem(

	@field:SerializedName("contactnumber")
	val contactNumber: String?,

	@field:SerializedName("youid")
	val slNo: Int?,

	@field:SerializedName("email")
	val emailId: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("website")
	val website: String?,

	@field:SerializedName("bussinesstype")
	val businessType: String?,

	@field:SerializedName("Name")
	val name: String?
)
