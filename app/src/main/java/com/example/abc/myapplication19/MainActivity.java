package com.example.abc.myapplication19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rxJavaNormal();

        rxJavaLink();

    }


    public void rxJavaNormal()
    {


        Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        Observer<Integer> observer=new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("text", "onSubscribe: ");
                disposable=d;

            }

            @Override
            public void onNext(Integer value) {
                Log.d("text", "onNext: "+value);
                if(value==2)
                {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("text", "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d("text", "onComplete: ");
            }
        };

        observable.subscribe(observer);

    }

    public void rxJavaLink()
    {




        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("text", "开始采用subscribe连接");

                disposable=d;
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d("text", "对Next事件"+ value +"作出响应"  );
                if(value==2)
                {
                    disposable.dispose();
                    Log.d("text", "onNext: dispose:"+disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("text", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("text", "对Complete事件作出响应");
            }

        });

    }
}
