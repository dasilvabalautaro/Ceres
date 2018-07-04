package com.globalhiddenodds.ceres.dagger

import com.globalhiddenodds.ceres.presentation.view.fragment.BaseFragment
import dagger.Subcomponent

@Subcomponent(modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(baseFragment: BaseFragment)
}