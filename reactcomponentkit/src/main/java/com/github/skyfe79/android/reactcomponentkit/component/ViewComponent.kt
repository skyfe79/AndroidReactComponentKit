package com.github.skyfe79.android.reactcomponentkit.component

import android.view.View
import android.view.ViewGroup
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class ViewComponent(override var token: Token, override var receiveState: Boolean = true): AnkoComponent<ViewGroup>, ReactComponent {

    override var newStateEventBus: EventBus<ComponentNewStateEvent>? = if (receiveState) EventBus(token) else null
    override var dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)

    init {
        newStateEventBus?.let {
            it.on { event ->
                when (event) {
                    is ComponentNewStateEvent.On -> on(event.state)
                }
            }
        }
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return layout(ui)
    }

    abstract fun layout(ui: AnkoContext<ViewGroup>): View
    abstract fun on(state: State)
}