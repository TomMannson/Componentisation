package com.tommannson.apps.componentisation.arch.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.*;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static androidx.lifecycle.Lifecycle.State.RESUMED;

public class RxLifecycleCreator {

    static Map<LifecycleOwner, Observable<Lifecycle.State>> scopedMap = new HashMap<>();


    private static synchronized Observable<androidx.lifecycle.Lifecycle.State> create(final LifecycleOwner context) {

        Observable<androidx.lifecycle.Lifecycle.State> observable = scopedMap.get(context);
        if (observable == null) {
            observable = Observable.create(new ObservableOnSubscribe<androidx.lifecycle.Lifecycle.State>() {
                @Override
                public void subscribe(ObservableEmitter<androidx.lifecycle.Lifecycle.State> emitter) throws Exception {

                    if (emitter.isDisposed()) {
                        return;
                    }

                    final RxLifecycleObserver observer = new RxLifecycleObserver(emitter, context);
                    context.getLifecycle().addObserver(observer);

                    emitter.setCancellable(new Cancellable() {
                        @Override
                        public void cancel() throws Exception {
                            context.getLifecycle().removeObserver(observer);
                            scopedMap.remove(context);
                        }
                    });
                }

            }).share();
            scopedMap.put(context, observable);
        }

        return observable;
    }

    static class RxLifecycleObserver implements LifecycleObserver {

        private ObservableEmitter<androidx.lifecycle.Lifecycle.State> emiter;
        private WeakReference<LifecycleOwner> weakOwner;

        RxLifecycleObserver(ObservableEmitter<Lifecycle.State> emiter, LifecycleOwner owner) {
            this.emiter = emiter;
            weakOwner = new WeakReference<>(owner);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onAny() {
            LifecycleOwner owner = weakOwner.get();
            if (owner != null) {
                emiter.onNext(owner.getLifecycle().getCurrentState());
            }
        }
    }

    public static class LifecycleWraper<T> {

        T data;
        androidx.lifecycle.Lifecycle.State lifecycleState;

        public LifecycleWraper(T data, Lifecycle.State lifecycleState) {
            this.data = data;
            this.lifecycleState = lifecycleState;
        }

        public T getData() {
            return data;
        }

        public Lifecycle.State getLifecycleState() {
            return lifecycleState;
        }
    }


    public static class CombineLifecycleWithData<T> implements BiFunction<T, androidx.lifecycle.Lifecycle.State, LifecycleWraper<T>> {

        @Override
        public LifecycleWraper<T> apply(T data, Lifecycle.State event) throws Exception {

            return new LifecycleWraper<>(data, event);
        }
    }

    public static class LifeCycleComposer<T> implements ObservableTransformer<T, T> {

        private LifecycleOwner view;
        private Lifecycle.State activationState;

        public LifeCycleComposer(LifecycleOwner view, Lifecycle.State activationState) {
            this.view = view;
            this.activationState = activationState;
        }

        public LifeCycleComposer(LifecycleOwner view) {
            this(view, RESUMED);
        }

        @Override
        public ObservableSource<T> apply(Observable<T> upstream) {
            return Observable.combineLatest(upstream,
                    RxLifecycleCreator.create(view),
                    new RxLifecycleCreator.CombineLifecycleWithData<T>())
                    .filter(new Predicate<LifecycleWraper<T>>() {
                        @Override
                        public boolean test(RxLifecycleCreator.LifecycleWraper<T> listLifecycleWraper) throws Exception {
                            return listLifecycleWraper.getLifecycleState().isAtLeast(activationState);
                        }
                    })
                    .map(new Function<LifecycleWraper<T>, T>() {
                        @Override
                        public T apply(LifecycleWraper<T> wraper) throws Exception {
                            return wraper.getData();
                        }
                    })
                    .distinctUntilChanged();
        }
    }

    public static class EmptyComposer<T> implements ObservableTransformer<T, T> {


        @Override
        public ObservableSource<T> apply(Observable<T> upstream) {
            return upstream.distinctUntilChanged();
        }
    }
}
