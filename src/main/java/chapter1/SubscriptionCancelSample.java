package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SubscriptionCancelSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable.interval(200L, TimeUnit.MILLISECONDS)
        .subscribe(new Subscriber<Long>() {

          private Subscription subscription;
          private long startTime;

          @Override
          public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            this.startTime = System.currentTimeMillis();
            this.subscription.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(final Long data) {
            // 구독 시작부터 500밀리초가 지나면 구독을 해지하고 처리를 중단
            if ((System.currentTimeMillis() - startTime) > 500) {
              subscription.cancel(); // 구독을 해지한다
              System.out.println("구독 해지");
              return;
            }
            System.out.println("data = " + data);
          }

          @Override
          public void onError(final Throwable t) {

          }

          @Override
          public void onComplete() {

          }
        });

    // 잠시 기다린다
    Thread.sleep(2000L);
  }
}
