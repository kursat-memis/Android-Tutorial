package com.kursatmemis.dagger.dependency_injection.components

import com.kursatmemis.dagger.dependency_injection.modules.AppModule
import com.kursatmemis.dagger.MainActivity
import dagger.Component

/**
 * @Component: @Component ile işaretlenen arayüz, bağımlılıkları enjekte etmek ve bağımlılıkları
 * sağlamak için dagger kütüohanesi tarafından kullanılır.
 *
 * @Component(modules = [AppModule::class]): modules parametresine argüman olarak bağımlılıkların
 * nasıl başlatılacağını ve sunulacağını belirttiğimiz 'module' class'ları verilir.
 * Yani dagger, uygulamadaki bağımlılıkları burada belirtilen modül class'larındaki methodlardan
 * sağlayacaktır.
 *
 * inject(mainActivity: MainActivity): inject metoduna parametre olarak MainActivity sınıfını geçirdik.
 * Bu, Dagger'ın MainActivity sınıfının içindeki @Inject annotation'u ile işaretlenmiş bağımlılıkları
 * otomatik olarak enjekte etmesini sağlar.
 */

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}