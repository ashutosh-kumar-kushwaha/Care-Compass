package me.ashutoshkk.carecompass.alarm

import me.ashutoshkk.carecompass.domain.model.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}