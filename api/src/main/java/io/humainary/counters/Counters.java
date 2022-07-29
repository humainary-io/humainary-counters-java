/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.counters;

import io.humainary.counters.spi.CountersProvider;
import io.humainary.spi.Providers;
import io.humainary.substrates.Substrates;
import io.humainary.substrates.Substrates.Environment;
import io.humainary.substrates.Substrates.Instrument;
import io.humainary.substrates.Substrates.Name;
import io.humainary.substrates.Substrates.Type;

import static io.humainary.substrates.Substrates.type;


/**
 * An open and extensible interface offering a highly efficient implementation of contextually bound counters
 * that can be used as a building block for other observability instrument libraries and technologies,
 * such as activity-based resource metering.
 */

public final class Counters {

  private static final CountersProvider PROVIDER =
    Providers.create (
      "io.humainary.counters.spi.factory",
      "io.calculis.counters.spi.alpha.ProviderFactory",
      CountersProvider.class
    );

  private Counters () {
  }


  /**
   * Returns the default {@link Context}.
   *
   * @return The default {@link Context}
   * @see CountersProvider#context()
   */

  public static Context context () {

    return
      PROVIDER.context ();

  }


  /**
   * Returns a {@link Context} mapped based on one or more properties within {@link Environment} provided.
   * <p>
   * Implementation Notes:
   * — The context returned should equal a context returned with the same environment properties.
   *
   * @param environment the configuration used in the mapping and construction of a {@link Context}
   * @return A {@link Context} constructed from and mapped to the {@link Environment}
   * @see CountersProvider#context(Environment)
   */

  public static Context context (
    final Environment environment
  ) {

    return
      PROVIDER.context (
        environment
      );

  }

  /**
   * A context represents some configured boundary within a process space.
   * <p>
   * Note: An SPI implementation of this interface is free to override
   * the default methods implementation included here.
   *
   * @see Counters#context(Environment)
   */

  public interface Context
    extends Substrates.Context< Counter, Long > {

    /**
     * Returns a counter associated with this provided name within the scope of this context.
     *
     * @param name the name used by all change events published by the returned counter
     * @return A counter, specific to this context, that can be used for incrementing an underlying numeric value
     */

    Counter counter (
      final Name name
    );

  }


  /**
   * An interface that represents an instrument used for incrementing a named numeric
   */

  public interface Counter
    extends Instrument {

    /**
     * The {@link Type} used to identify the interface type of this referent
     */

    Type TYPE = type ( Counter.class );

    /**
     * Increments the underlying value by 1.
     */

    void inc ();

  }

}
