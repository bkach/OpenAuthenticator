package com.github.bkach.openauthenticator.codelist

import android.content.Intent
import com.github.bkach.openauthenticator.MainActivity
import com.github.bkach.openauthenticator.R
import com.github.bkach.openauthenticator.base.BaseActivity
import com.github.bkach.openauthenticator.base.Presenter

class CodeListActivity : BaseActivity<CodeListPresenter.View>(), CodeListPresenter.View {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_code_list
    }

    override fun getPresenter(): Presenter<CodeListPresenter.View> {
        return CodeListPresenter()
    }

    override fun getPresenterView(): CodeListPresenter.View {
        return this
    }

    override fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}
