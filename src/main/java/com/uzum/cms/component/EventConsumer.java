package com.uzum.cms.component;

public interface EventConsumer<E> {
    void listen(final E event);

    void dltHandler(E event, String exceptionMessage);
}
