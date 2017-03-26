package com.example.chen.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testRxJava2();
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

}
