package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import java.util.concurrent.TimeUnit;

public class EffectedSample {

  private enum State {
    ADD, MULTIPLY;
  }

  private static State calcMethod;

  public static void main(String[] args) throws InterruptedException {

    // 계산 방법을 덧셈으로 설정
    calcMethod = State.ADD;

    Flowable<Long> flowable =
        // 300밀리초마다 데이터를 통지하는 Flowable을 생성
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            // 7건까지 통지
            .take(7)
            .scan((sum, data) -> {
              if (calcMethod == State.ADD) {
                return sum + data;
              } else {
                return sum * data;
              }
            });

    flowable.subscribe(data -> System.out.println("data = " + data));

    // 잠시 기다렸다가 곱셈으로 변경
    Thread.sleep(1000);

    System.out.println("계산 방법 변경");
    calcMethod = State.MULTIPLY;

    // 잠시 기다림.
    Thread.sleep(2000);
  }
}
