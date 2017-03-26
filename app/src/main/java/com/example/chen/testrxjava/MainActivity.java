package com.example.chen.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testRxJava2();
        testRxJava3();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void testRxJava1() {
        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("result", s);
                    }
                });
    }

    private void testRxJava2() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onNext("Hello, world!");
                        sub.onCompleted();

                    }
                }
        );


        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("result", s);
            }

            @Override
            public void onCompleted() {
                Log.d("result", "finished");
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        myObservable.subscribe(mySubscriber);
    }

    private void testRxJava3() {
        final String[] infos = {"info1", "info2", "info3"};
        final Observable observable = Observable.from(infos);
        final Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                //相当于Subscriber的onCompleted（）
                Log.d("result","onCompletedAction");
            }
        };


        final Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                //相当于Subscriber的onNext(Sring s)
                Log.d("result","onNextAction");
            }
        };


        final Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //相当于Subscriber的onError(Throwable e)
                Log.d("result","onErrorAction");
            }
        };


        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);


        observable.unsubscribeOn(Schedulers.io());
    }
}
