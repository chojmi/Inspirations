package com.github.chojmi.inspirations.data.entity.helpers

import com.google.gson.annotations.SerializedName

data class Timezone(val label: String,
                    val offset: String,
                    @SerializedName("timezone_id") val timezoneId: String)