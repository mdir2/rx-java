package chapter1;

import io.reactivex.rxjava3.core.Flowable;

public class MethodChainSample {

  public static void main(String[] args) {
    Flowable<Integer> flowable =
        // 인자의 데이터를 순서대로 통지하는 Flowable 생성
        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            // 짝수만 통지
            .filter(data -> data % 2 == 0)
            // 데이터를 100개로 변환
            .map(data -> data * 100);

    flowable.subscribe(System.out::println);
  }
}
