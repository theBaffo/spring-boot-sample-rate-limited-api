package com.example.sampleratelimitedapi.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 *
 * <h1>RateLimiter</h1>
 *
 * <p>A simple implementation of a Rate Limiter (Max N requests in M milliseconds).
 *
 * <p>This class keeps reference of the timing of the last N requests using a Queue: when we reach
 * the Nth request, if the time difference between the 0th and the Nth requests is less than M, then
 * the request gets blocked. Also, that endpoint becomes unavailable for
 * __RateLimiter.API_BLOCKED_TIME_MS__.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
public class RateLimiter {
  // The interval (in millisecond) in which the endpoint will be unavailable.
  public static final Long API_BLOCKED_TIME_MS = 5 * 1000L;

  // The maximum amount of requests allowed for the given interval.
  private Long maxRequests;

  // If the endpoint gets "blocked", this is time when the endpoint should be "unblocked"
  private Long nextRequestAtMs = 0L;

  // The interval (in milliseconds) in which the number of max requests is valid.
  private Long timeIntervalMs;

  // The Queue that holds the time of the last {maxRequest} requests
  private Queue<Long> queue = new LinkedList<>();

  /** Creates a RateLimiter with the default maxRequests (50) and timeIntervalMs (10000) values. */
  public RateLimiter() {
    this.maxRequests = 50L;
    this.timeIntervalMs = 10 * 1000L;
  }

  /**
   * Creates a RateLimiter with the specified maxRequests and timeIntervalMs values.
   *
   * @param maxRequests The maximum amount of requests allowed for the given interval.
   * @param timeIntervalMs The interval (in milliseconds) in which the number of max requests is
   *     valid.
   */
  public RateLimiter(Long maxRequests, Long timeIntervalMs) {
    this.maxRequests = maxRequests;
    this.timeIntervalMs = timeIntervalMs;
  }

  /**
   * This method is used to verify if a request is allowed based on the limit of requests per ms
   * that we applied.
   *
   * @return boolean The request should be allowed or not.
   */
  public boolean isAllowed() {
    Long currentTimeMillis = System.currentTimeMillis();

    // If currentTimeMillis is less than nextRequestAtMs
    // then the API is "blocked" and the request should
    // not be allowed
    if (currentTimeMillis < this.nextRequestAtMs) return false;

    // We should only persist the latest {maxRequests} requests
    if (this.queue.size() >= this.maxRequests) {
      Long timeDifference = currentTimeMillis - this.queue.remove();
      this.queue.add(currentTimeMillis);

      // If the time difference between the first request in the queue
      // and the latest one is less than {timeIntervalMs}, then the request
      // should be blocked
      if (timeDifference < this.timeIntervalMs) {
        // Clear queue
        this.queue.clear();

        // block endpoint for API_BLOCKED_TIME_MS
        this.nextRequestAtMs = currentTimeMillis + API_BLOCKED_TIME_MS;

        // Request should not be allowed
        return false;
      }
    } else {
      queue.add(currentTimeMillis);
    }

    return true;
  }
}
