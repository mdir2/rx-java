package chapter1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ObservableSample {

  public static void main(String[] args) throws InterruptedException {
    // 인사말을 통지하는  Observable 생성
    Observable<String> observable =
        Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Throwable {
            // 통지 데이터
            String[] datas = {"Hello, World!", "안녕, RxJava"};

            for (String data : datas) {
              // 구독이 해지되면 처리를 중단
              if (emitter.isDisposed()) {
                return;
              }

              // 데이터를 통지
              emitter.onNext(data);
            }
            // 완료를 통지한다
            emitter.onComplete();
          }
        });

    observable
        .observeOn(Schedulers.computation())
        .subscribe(new Observer<String>() {
          @Override
          public void onSubscribe(@NonNull final Disposable d) {
            // 아무것도 하지 않는다
          }

          @Override
          public void onNext(@NonNull final String s) {
            // 데이터를 받을 때의 처리
            String threadName = Thread.currentThread().getName();
            System.out.println("threadName = " + threadName);
          }

          @Override
          public void onComplete() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": 완료");
          }

          @Override
          public void onError(@NonNull final Throwable e) {
            e.printStackTrace();
          }
        });

    Thread.sleep(500L);
  }
}
