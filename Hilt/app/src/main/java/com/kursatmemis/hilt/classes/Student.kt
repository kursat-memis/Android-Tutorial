package com.kursatmemis.hilt.classes

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

class Student @Inject constructor(val name: String, @ActivityContext val context: Context) {

    fun study() {
        Log.w("mKm - hilt", "Student is studying. Student Name: $name")
        Log.w("mKm - hilt", "Context Package Name: ${context.packageName}")
    }

}

/**
 * Module Oluşturma:
 * 1. Bir class oluşturulur ve onun başına @Module annotation'ı eklenir.
 *    @Module: Hilt, dependency'lerin nasıl sağlanacağını bilmediği durumlarda, bu annotation'a
 *    sahip yapıların içindeki methodlara göre bu dependency'leri sağlar.
 *
 * 2. Bu class'ın başına @InstallIn annotation'ı eklenir ve bu annotation'ın parametresinde,
 *    dependency'lerin hangi bileşenlerde kullanılacağını ve hangi yaşam döngüsüne sahip
 *    olacaklarını belirtiriz.
 *    Bir modülde scope belirlemek, o modül içinde tanımlanan bağımlılıkların hangi kapsamda
 *    oluşturulacağını belirler.
 *    Detaylı bilgi 'Hilt-Scopes' dosyasında açıklandı.
 *
 * 3. Dependency'nin nasıl sağlanacağını belirttiğimiz method oluşturulur. (provideName())
 *    Bu methodun başına @Provides annotation'ı ve @InstallIn annotation'ında parametre olarak
 *    belirttiğimiz component'in scope annotation'ı eklenir.
 *    ('SingletonComponent' component'inin scope annotation'ı @Singleton'dır.)
 *    @Provides annotation'ı da hilt'in bu methodun dependency'leri provide eden(sağlayan) bir
 *    method olduğunu anlamasını sağlar.
 *
 *    Hilt, @Provide ile belirtilmiş methodun return ettiği tipe bakar, bu tipten dependency
 *    sağlanması gereken yerlerde bu methodun return edeceği değeri sağlar.
 *
 *    Not: Eğer ki dependency olarak bir context sağlanması gerkeiyorsa, modüle içindeki
 *    methodan parametresinde @ApplicationContext, @ActivityContext gibi
 *    annotation'lar kullanarak, bunu sağlayabiliriz. 'StudentModule' içindeki provideContext()
 *    methodunda bu durumun kullanımı gösterilmiştir. Bu sayede Student class'ındaki 'context'
 *    dependency'si hilt tarafından otomatik olarak sağlanmış olacaktır.
 *
 *   @ApplicationContext: Eğer dependency uygulamanın tamamında aynı context'i kullanacaksa
 *   bunu seçebilirsin.
 *   @ActivityContext: Eğer dependency bir activity'nin life cycle'ında kullanılacaksa
 *   bunu seçebilirsin.
 *
 *
 *    Eğer tüm class'lardaki String tipindeki dependency'lere aynı değeri değil de bazılarında
 *    farklı değeri inject etmek istiyorsak; @Qualifier ve @Retention(AnnotationRetention.BINARY)
 *    annotation'larını kullanarak kendimize ait bir annotation oluşturur ve bu annotation'lar
 *    ile hangi dependency'e hangi değerin atanması gerektiğini belirleyebiliriz.
 *    Bunun gerçekleştirilmesi aşağıdaki Test1 ve Test2 class'larında gösterildi.
 *
 *    Not: @Provides annotation'ı yerine @Binds annotation'ı da kullanılabilir. Ancak @Provides
 *    kullanımı daha kolay geldi.
 *    Diğer kullanımı da görmek istersen;
 *    https://developer.android.com/training/dependency-injection/hilt-android#inject-interfaces
 */

@Module
@InstallIn(SingletonComponent::class)
class StudentModule {

    @Provides
    @Singleton
    fun provideName() : String {
        return "Kursat"
    }

    @Provides
    @Singleton
    fun provideContext(@ActivityContext applicationContext: Context): Context {
        return applicationContext
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyProviderEnglishValue

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyProviderTurkishValue

class Test1 @Inject constructor(@MyProviderEnglishValue val value: String)

class Test2 @Inject constructor(@MyProviderTurkishValue val value: String)

@Module
@InstallIn(SingletonComponent::class)
class TestModule{
    @Provides
    @Singleton
    @MyProviderEnglishValue
    fun provideEnglishValue() : String {
        return "This sentence is in English."
    }

    @Provides
    @Singleton
    @MyProviderTurkishValue
    fun provideTurkishValue() : String {
        return "Bu cümle Türkçe'dir."
    }

}