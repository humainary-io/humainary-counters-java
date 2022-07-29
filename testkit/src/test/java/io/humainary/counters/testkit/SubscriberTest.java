/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.counters.testkit;

import io.humainary.counters.Counters.Context;
import io.humainary.counters.Counters.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.humainary.counters.Counters.context;
import static io.humainary.devkit.testkit.TestKit.capture;
import static io.humainary.devkit.testkit.TestKit.recorder;
import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test class for the {@link Subscriber} and {@link Subscription} interfaces.
 *
 * @author wlouth
 * @since 1.0
 */

final class SubscriberTest {

  private static final Name C1_NAME = name ( "counter" ).name ( "1" );
  private static final Name C2_NAME = name ( "counter" ).name ( "2" );

  private Context context;
  private Counter c1;
  private Counter c2;

  @BeforeEach
  void setup () {

    context =
      context (
        EMPTY
      );

    c1 =
      context.counter (
        C1_NAME
      );

    c2 =
      context.counter (
        C2_NAME
      );

  }

  @Test
  void subscribe () {

    final var recorder =
      recorder (
        context
      );

    recorder.start ();

    c1.inc ();
    c2.inc ();
    c2.inc ();

    final var capture =
      recorder
        .stop ()
        .orElseThrow (
          AssertionError::new
        );

    c1.inc ();
    c2.inc ();

    assertEquals (
      capture (
        c1,
        1L
      ).to (
        c2,
        1L
      ).to (
        c2,
        1L
      ),
      capture
    );

  }

}
