
/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.counters.perfkit;

import io.humainary.counters.Counters.Context;
import io.humainary.counters.Counters.Counter;
import io.humainary.devkit.perfkit.PerfKit;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

import static io.humainary.counters.Counters.context;
import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.*;

@State ( Scope.Benchmark )
public class Driver implements
                    PerfKit.Driver {

  private static final Outlet< Long >     OUTLET     = Outlet.empty ();
  private static final Subscriber< Long > SUBSCRIBER = subscriber ( OUTLET );

  private Name    name;
  private Context context;
  private Counter counter;

  @Setup ( Level.Trial )
  public final void setup ()
  throws NoSuchMethodException, IOException {

    final var configuration =
      configuration ();

    context =
      context (
        environment (
          lookup (
            path ->
              configuration.apply (
                path.toString ()
              )
          )
        )
      );

    counter =
      context.counter (
        name (
          "counter"
        )
      );

    final var method = getClass ().getMethod (
      "setup"
    );

    name =
      name (
        method
      );

  }

  /**
   * Lookup of an emitter.
   */

  @Benchmark
  public void context_get () {

    context.get (
      name
    );

  }


  @Benchmark
  public void context_counter () {

    context.counter (
      name
    );

  }


  @Benchmark
  public void context_iterator () {

    context.iterator ();

  }


  @Benchmark
  public void context_foreach () {

    for ( final Counter c : context ) {
      assert c != null;
    }

  }


  @Benchmark
  public void context_subscribe_cancel () {

    context.subscribe (
      SUBSCRIBER
    ).close ();

  }


  @Benchmark
  public void context_consume_cancel () {

    context.consume (
      OUTLET
    ).close ();

  }


  @Benchmark
  public void counters_context () {

    context ();

  }


  @Benchmark
  public void counters_context_environment () {

    context (
      EMPTY
    );

  }


  @Benchmark
  public void counter_inc () {

    counter.inc ();

  }

}