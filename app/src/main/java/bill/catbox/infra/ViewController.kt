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

package bill.catbox.infra

import android.content.Context
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import bill.reaktive.OpenPublisher
import bill.reaktive.Publisher
import bill.reaktive.Publishers

interface ViewController {
    val context: Context
    val optionsItemSelected: Publisher<Int>
}

abstract class ViewControllerActivity : AppCompatActivity(), ViewController {
    private val optionsItemSelectedProcessor: OpenPublisher<Int> by lazy { Publishers.open<Int>() }

    final override val context
        get() = this

    final override val optionsItemSelected: Publisher<Int>
        get() = optionsItemSelectedProcessor

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let { optionsItemSelectedProcessor.onNext(it) }
        return true
    }
}

val Context.optionsItemSelected: Publisher<Int>
    get() = (this as? ViewController)?.optionsItemSelected ?: Publishers.empty()
