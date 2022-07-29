/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.counters.spi;

import io.humainary.counters.Counters.Context;
import io.humainary.spi.Providers.Provider;
import io.humainary.substrates.Substrates.Environment;

/**
 * The service provider interface for the humainary counters runtime.
 * <p>
 * Note: An SPI implementation of this interface is free to override
 * the default methods implementation included here.
 *
 * @author wlouth
 * @since 1.0
 */

public interface CountersProvider
  extends Provider {

  Context context ();

  Context context (
    Environment environment
  );

}
