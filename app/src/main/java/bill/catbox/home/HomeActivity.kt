package bill.catbox.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bill.catbox.R
import bill.catbox.infra.toOrdinal
import bill.catbox.infra.toast
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeView {

    private val presenter by lazy { HomePresenter(this) }

    override val boxChosenEvent = PublishSubject.create<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach()
    }

    override fun onPause() {
        presenter.detach()
        super.onPause()
    }

    override fun onCatFound(attempts: Int) {
        toast(resources.getQuantityString(R.plurals.cat_found, attempts, attempts))
    }

    override fun onEmptyBox(attempts: Int) {
        toast(getString(R.string.empty_box, attempts.toOrdinal()))
    }

    private fun setupButtons() {
        this.box1.setOnClickListener { boxChosenEvent.onNext(0) }
        this.box2.setOnClickListener { boxChosenEvent.onNext(1) }
        this.box3.setOnClickListener { boxChosenEvent.onNext(2) }
        this.box4.setOnClickListener { boxChosenEvent.onNext(3) }
        this.box5.setOnClickListener { boxChosenEvent.onNext(4) }
    }

}
