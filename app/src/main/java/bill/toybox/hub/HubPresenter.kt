/*
 * Copyright (c) 2019 Emanuel Machado da Silva <emanuel.mch@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package bill.toybox.hub

import bill.toybox.catbox.home.HomeActivity
import bill.toybox.infinity.InfinityActivity
import bill.toybox.infra.ObservableActivity

class HubPresenter(private val view: HubView) {

    fun observe(activity: ObservableActivity) {
        activity.doOnResume {
            view.loadImage("https://i.imgur.com/guEhXZP.jpg")

            view.clicks
                    .doOnNext { HomeActivity.startActivity(activity) }
                    .subscribeUntilPause()

            view.loadImage2("https://free-images.com/or/7e0a/cat_mom_kittens_cats.jpg")

            view.clicks2
                    .doOnNext { InfinityActivity.startActivity(activity) }
                    .subscribeUntilPause()
        }
    }
}
