import com.example.simodista.core.domain.usecase.CovidInteractor
import com.example.simodista.core.domain.usecase.ICovidUseCase
import com.example.simodista.presenter.user.home.UserHomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ICovidUseCase> { CovidInteractor(get()) }
}

val viewModelModule = module {
    viewModel { UserHomeViewModel(get()) }
}