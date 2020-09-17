package chapter1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableSample {

  public static void main(String[] args) throws Exception {
    Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
      @Override
      public void subscribe(@NonNull final FlowableEmitter<String> emitter) throws Throwable {
        String[] datas = {"Hello, World!!!", "안녕, RxJava!"};

        for (String data : datas) {
          if (emitter.isCancelled()) {
            return;
          }

          // 데이터를 통지
          emitter.onNext(data);
        }

        // 완료를 통지
        emitter.onComplete();
      }
    }, BackpressureStrategy.BUFFER); // 초과한 데이터는 버퍼링한다.

    flowable
        // Subscriber 처리를 개별 쓰레드에서 처리한다.
        .observeOn(Schedulers.computation())
        .subscribe(new Subscriber<String>() {

          // 데이터 개수 요청과 구독 해지를 하는 객체
          private Subscription subscription;

          @Override
          public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1L);
          }

          @Override
          public void onNext(final String s) {
            final String threadName = Thread.currentThread().getName();
            System.out.printf("%s : %s\r\n", threadName, s);
            // 다음에 받을 데이터 개수를 요청
            this.subscription.request(1L);
          }

          @Override
          public void onComplete() {
            final String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": 완료");
          }

          @Override
          public void onError(final Throwable t) {
            t.printStackTrace();
          }
        });
    Thread.sleep(500L);
  }
}
