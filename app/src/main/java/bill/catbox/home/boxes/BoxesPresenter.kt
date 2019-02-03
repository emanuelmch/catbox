/*
 * Copyright (c) 2018 Emanuel Machado da Silva <emanuel.mch@gmail.com>
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

package bill.catbox.home.boxes

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import bill.catbox.game.GameEngine
import bill.catbox.game.GameState
import bill.catbox.game.GameStateContainer
import bill.catbox.settings.SettingsRepository
import bill.reaktive.SubscriptionBag
import timber.log.Timber

class BoxesPresenter(private val view: BoxesView,
                     private val game: GameStateContainer,
                     private val settings: SettingsRepository
) : LifecycleObserver {

    constructor(context: Context, view: BoxesView, game: GameStateContainer = GameStateContainer)
            : this(view, game, SettingsRepository(context))

    private val subscriptions = SubscriptionBag()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun attach() {
        Timber.d("Presenter::attach")

        subscriptions += settings.watchBoxCount()
                .subscribe { game.newGame(it) }

        subscriptions += game.gameStateChanged
                .filter(GameState::isNewGame)
                .signalOnForeground()
                .subscribe { view.startGame(it.boxCount) }

        subscriptions += view.boxChosenEvent
                .signalOnBackground()
                .map {
                    Timber.d("Box #$it chosen")
                    game.play(it)
                }
                .doOnNext {
                    if (it.isCatFound) {
                        Thread.sleep(2000)
                    }
                }
                .signalOnForeground()
                .subscribe { game ->
                    if (game.isCatFound) {
                        view.onCatFound(game.attempts)
                    } else {
                        view.onEmptyBox(game.attempts)
                    }
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun detach() {
        Timber.d("Presenter::detach")
        subscriptions.clear()
    }
}
