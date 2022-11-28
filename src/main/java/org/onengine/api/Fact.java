package org.onengine.api;

import lombok.ToString;

import java.util.Objects;

/**
 * A class representing a named fact. Facts have unique names within a Facts
 * instance.
 * 
 * @param <T> type of the fact
 */
@ToString
public class Fact<T> {
	
	private final String name;
	private final T value;

	/**
	 * Create a new fact.
	 * @param name of the fact
	 * @param value of the fact
	 */
	public Fact(String name, T value) {
		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(value, "value must not be null");
		this.name = name;
		this.value = value;
	}

	/**
	 * Get the fact name.
	 * @return fact name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the fact value.
	 * @return fact value
	 */
	public T getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Fact<?> fact = (Fact<?>) o;
		return name.equals(fact.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
