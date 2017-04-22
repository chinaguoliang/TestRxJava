package com.example.chen.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testRxJava2();
        //testRxJava3();
//        testRxjava4();
        //testRxJava2();
        //testRxJava3();
        //testRxjava4();
        testRxJava1();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void testRxJava1() {
//        Observable.just("Hello, world!")
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("result", s);
//                    }
//                });

        Flowable.fromArray("Hello,I am China!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, s);
                    }
                });
    }

    private void testRxJava2() {
//        Observable<String> myObservable = Observable.create(
//                new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> sub) {
//                        sub.onNext("Hello, world!");
//                        sub.onNext("Hello, world!");
//                        sub.onComplete();
//
//                    }
//                }
//        );
//
//
//        Subscriber<String> mySubscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d("result", s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d("result", "finished");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//        };
//
//        myObservable.subscribe(mySubscriber);


        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    private void testRxJava3() {
//        final String[] infos = {"info1", "info2", "info3"};
//        final Observable observable = Observable.from(infos);
//        final Action0 onCompletedAction = new Action0() {
//            @Override
//            public void call() {
//                //相当于Subscriber的onCompleted（）
//                Log.d("result","onCompletedAction");
//            }
//        };
//
//
//        final Action1<String> onNextAction = new Action1<String>() {
//            @Override
//            public void call(String s) {
//                //相当于Subscriber的onNext(Sring s)
//                Log.d("result","onNextAction->" + s);
//            }
//        };
//
//
//        final Action1<Throwable> onErrorAction = new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                //相当于Subscriber的onError(Throwable e)
//                Log.d("result","onErrorAction");
//            }
//        };
//
//
//        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
//        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction, onErrorAction);
//        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
//        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
//
//
//        observable.unsubscribeOn(Schedulers.io());


        Flowable.just("map")
            .map(new Function<String, String>() {
                @Override
                public String apply(String s) throws Exception {
                    return s + " -ittianyu";
                }
            })
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Log.d(TAG,"->" + s);
            }
         });
    }


    private void testRxjava4() {

//        final Action1<String> onNextAction = new Action1<String>() {
//            @Override
//            public void call(String s) {
//                //相当于Subscriber的onNext(Sring s)
//                Log.d("result","onNextAction->" + s);
//            }
//        };
//
//        Observable.just("Hello, world!")
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return s + " -Dan";
//                    }
//                })
//                .subscribe(onNextAction);


        ArrayList<String[]> list=new ArrayList<>();
        String[] words1={"Hello,","I am","China!"};
        String[] words2={"Hello,","I am","Beijing!"};
        list.add(words1);
        list.add(words2);
        Flowable.fromIterable(list)
            .flatMap(new Function<String[], Publisher<String>>() {
                @Override
                public Publisher<String> apply(String[] strings) throws Exception {
                    return Flowable.fromArray(strings);
                }
            }).toList()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        Log.d(TAG,"the size:" + strings.size());
                    }
                });
        }
}
