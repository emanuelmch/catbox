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

package androidx.recyclerview.widget

import io.mockk.every
import io.mockk.mockk
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class MockRecyclerView {

    private class MockAdapterDataObservable : RecyclerView.AdapterDataObservable() {
        override fun notifyChanged() = Unit
    }

    companion object {
        fun create(): RecyclerView {
            val view: RecyclerView = mockk()
            var adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>? = null

            every { view.adapter } answers { adapter }
            every {
                view.adapter = any()
            } propertyType RecyclerView.Adapter::class answers {
                val field = RecyclerView.Adapter::class.java.getDeclaredField("mObservable")
                field.isAccessible = true

                val modifiers = Field::class.java.getDeclaredField("modifiers")
                modifiers.isAccessible = true
                modifiers.setInt(field, field.modifiers and Modifier.FINAL.inv())

                field.set(value, MockAdapterDataObservable())
                adapter = value

                modifiers.setInt(field, field.modifiers or Modifier.FINAL)
                modifiers.isAccessible = false
                field.isAccessible = false
            }

            return view
        }
    }
}