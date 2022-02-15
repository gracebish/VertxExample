package com.graciella.vertx_starter.eventbuscustomcodec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class LocalMessageCodec<T> implements MessageCodec<T, T> {

  private final String typeName;

  public LocalMessageCodec(Class<T> type) {
    this.typeName = type.getName();
  }

  @Override
  public void encodeToWire(final Buffer buffer, final T t) {
    throw new UnsupportedOperationException("Only local encode is supported");
  }

  @Override
  public T decodeFromWire(final int pos, final Buffer buffer) {
    throw new UnsupportedOperationException("Only local decode is supported");
  }

  @Override
  public T transform(T t) {
    return null;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public byte systemCodecID() {
    return 0;
  }
}
