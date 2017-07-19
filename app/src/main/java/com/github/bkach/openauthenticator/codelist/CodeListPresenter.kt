package com.github.bkach.openauthenticator.codelist

import com.github.bkach.openauthenticator.base.Presenter

/**
 * {Description}
 *
 * @author Boris Kachscovsky
 */
class CodeListPresenter : Presenter<CodeListPresenter.View>() {

    override fun onAttach(view: View) {
        super.onAttach(view)
        view.goToMainActivity();
    }

    interface View {
        fun goToMainActivity()
    }
}