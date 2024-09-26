package org.raphaelstats.raphaelStatistics

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter
import java.util.*

@Serializable
class DBData(
    var User_ID: String,
    var Type: Int,
    var Time: String,
    var Data: String
)
