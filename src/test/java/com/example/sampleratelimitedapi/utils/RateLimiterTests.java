package com.example.sampleratelimitedapi.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 *
 *
 * <h1>RateLimiterTests</h1>
 *
 * <p>This class contains the Unit Tests for the RateLimiter class
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
public class RateLimiterTests {
  private void sleep(Long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {

    }
  }

  /**
   * Given a rate limit of max 5 requests in 1 second.
   * When we make five request, one every 200 millisecond.
   * When we make another request (after waiting another 200 millisecond).
   * Then the isAllowed() method should return true.
   */
  @Test
  public void testLastRequestAllowed() {
    RateLimiter rateLimiter = new RateLimiter(5L, 1 * 1000L);

    for (int i = 0; i < 5; i++) {
      assertThat(rateLimiter.isAllowed()).isEqualTo(true);
      sleep(200L);
    }

    // Last request should pass
    assertThat(rateLimiter.isAllowed()).isEqualTo(true);
  }

  /**
   * Given a rate limit of max 5 requests in 1 second.
   * When we make five request.
   * When we make another request.
   * Then the isAllowed() method should return false.
   */
  @Test
  public void testAllowedAfterBlockingTime() {
    RateLimiter rateLimiter = new RateLimiter(5L, 1 * 1000L);

    for (int i = 0; i < 5; i++) {
      assertThat(rateLimiter.isAllowed()).isEqualTo(true);
    }

    // Last request should fail
    assertThat(rateLimiter.isAllowed()).isEqualTo(false);

    // Wait blocking time
    sleep(RateLimiter.API_BLOCKED_TIME_MS);

    // Last request should pass
    assertThat(rateLimiter.isAllowed()).isEqualTo(true);
  }
}
