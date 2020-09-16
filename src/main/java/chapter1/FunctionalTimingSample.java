package chapter1;

import io.reactivex.rxjava3.core.Flowable;

public class FunctionalTimingSample {

  public static void main(String[] args) {
    final Flowable<Long> flowable = Flowable.just(System.currentTimeMillis());

    flowable.subscribe(System.out::println);
  }
}
