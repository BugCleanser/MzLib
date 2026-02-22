package mz.mzlib.minecraft.bukkit.entity

import mz.mzlib.minecraft.entity.Entity

val Entity.bukkit: org.bukkit.entity.Entity get() = BukkitEntityUtil.toBukkit(this)
val org.bukkit.entity.Entity.mz: Entity get() = BukkitEntityUtil.fromBukkit(this)
