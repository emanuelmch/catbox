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

import android.view.View
import bill.reaktive.UiPublishers
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.hub_activity.view.*

class HubView(private val rootView: View) {

    val clicks = UiPublishers.clicks(rootView.catImage)

    fun loadImage(url: String) {
        Glide.with(rootView)
                .load(url)
                .centerCrop()
                .into(rootView.catImage)
    }

    // FIXME: Make this a list, instead of naming stuff "*2"
    val clicks2 = UiPublishers.clicks(rootView.infinityImage)

    fun loadImage2(url: String) {
        Glide.with(rootView)
                .load(url)
                .centerCrop()
                .into(rootView.infinityImage)
    }
}
