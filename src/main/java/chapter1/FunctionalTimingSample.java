package chapter1;

import io.reactivex.rxjava3.core.Flowable;

public class FunctionalTimingSample {

  public static void main(String[] args) {
    final Flowable<Long> justFlowable = Flowable.just(System.currentTimeMillis());
    final Flowable<Long> flowable = Flowable.fromCallable(() -> System.currentTimeMillis());

    flowable.subscribe(System.out::println);
    flowable.subscribe(System.out::println);
    flowable.subscribe(System.out::println);
    flowable.subscribe(System.out::println);
    flowable.subscribe(System.out::println);

    System.out.println("----");

    justFlowable.subscribe(System.out::println);
    justFlowable.subscribe(System.out::println);
    justFlowable.subscribe(System.out::println);
    justFlowable.subscribe(System.out::println);
    justFlowable.subscribe(System.out::println);
  }
}
