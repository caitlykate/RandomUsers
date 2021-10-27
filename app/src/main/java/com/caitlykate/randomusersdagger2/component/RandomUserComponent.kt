package com.caitlykate.randomusersdagger2.component

import com.caitlykate.randomusersdagger2.interfaces.RandomUserApplicationScope
import com.caitlykate.randomusersdagger2.interfaces.RandomUsersApi
import com.caitlykate.randomusersdagger2.module.PicassoModule
import com.caitlykate.randomusersdagger2.module.RandomUsersModule
import com.squareup.picasso.Picasso
import dagger.Component

// Component будет интерфейсом для всего графа зависимостей.
// Лучшей практикой использования Component является объявление
// только верхнеуровневых зависимостей в нём и скрытие остальных зависимостей

@RandomUserApplicationScope
@Component(modules = [RandomUsersModule::class, PicassoModule::class])
interface RandomUserComponent {
    val randomUsersApi: RandomUsersApi
    val picasso: Picasso
}