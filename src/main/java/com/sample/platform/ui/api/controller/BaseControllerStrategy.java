package com.sample.platform.ui.api.controller;

public abstract class BaseControllerStrategy<I, T> implements ControllerStrategy<I, T>{
	@Override
	public T convert(final I info) {
		return (T) info;
	}
}
