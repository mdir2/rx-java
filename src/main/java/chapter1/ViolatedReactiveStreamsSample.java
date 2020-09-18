package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ViolatedReactiveStreamsSample {

  public static void main(String[] args) {
    Flowable.range(1, 3)
        .subscribe(new Subscriber<Integer>() {
          @Override
          public void onSubscribe(final Subscription subscription) {
            System.out.println("onSubscribe: start");
            subscription.request(Long.MAX_VALUE);
            System.out.println("onSubscribe: end");
          }

          @Override
          public void onNext(final Integer data) {
            System.out.println(data);
          }

          @Override
          public void onComplete() {
            System.out.println("완료");
          }

          @Override
          public void onError(final Throwable error) {
            System.out.println("에러=" + error);
          }
        });
  }
}
