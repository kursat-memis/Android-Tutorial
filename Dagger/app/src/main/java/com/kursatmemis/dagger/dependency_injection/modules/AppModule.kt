package com.kursatmemis.dagger.dependency_injection.modules

import com.kursatmemis.dagger.Adres
import com.kursatmemis.dagger.dependency_injection.consumers.Kargo
import dagger.Module
import dagger.Provides

/**
 * @Module: @Module annotation'u, bağımlılıkları nasıl oluşturacağınızı belirten sınıfları
 * işaretlemek için kullanılır. Dagger'a bir modül sınıfı, bağımlılıkların nasıl oluşturulacağını
 * ve sunulacağını öğretir.
 *
 * @Provides: @Provides annotation'u, bir modül sınıfı içinde ihtiyaç duyulan bağımlılıkların
 * oluşturulmasını sağlayan methodlar için kullanılır. Dagger, bu yöntemleri çağırarak
 * bağımlılıkları oluşturur.
 */

@Module
class AppModule {

    @Provides
    fun provideAdres(): Adres {
        return Adres("Orhangazi/Bursa")
    }

}