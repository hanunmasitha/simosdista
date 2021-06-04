import com.example.simodista.core.data.CovidRepository
import com.example.simodista.core.data.source.remote.API
import com.example.simodista.core.data.source.remote.RemoteDataSource
import com.example.simodista.core.domain.repository.ICovidRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val baseUrl = "https://api.kawalcorona.com/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(API::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<ICovidRepository> { CovidRepository(get()) }
}