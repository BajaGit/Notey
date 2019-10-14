package com.github.bajagit.notey.util;

import java.util.Vector;

import com.github.bajagit.notey.exception.NoteyRuntimeException;

import lotus.domino.Base;
import lotus.domino.NotesException;

public final class NoteyUtils {
	
	private NoteyUtils() {
		throw new UnsupportedOperationException("no");
	}
	
	/**
	 * Execute code with a Domino derived class,
	 * wrapping all thrown NotesException into Unchecked ones.
	 * 
	 * @param <T>
	 * @param with
	 * @param user
	 */
	public static <T extends Base> void with( T with, NotesConsumer<T> user) {
		try {
			user.accept(with);
		} catch (Exception ne) {
			throw new NoteyRuntimeException(ne);
		}
	}
	
	/**
	 * Execute code with a Domino derived class,
	 * wrapping all thrown NotesException into Unchecked ones.
	 * 
	 * @param <T>
	 * @param with
	 * @param user
	 */
	public static <T extends Base, R> R mapWith( T with, NotesFunction1<T, R> user) {
		try {
			return user.apply(with);
		} catch (Exception ne) {
			throw new NoteyRuntimeException(ne);
		}
	}
	
	/**
	 * calls {@link #typed(Vector, Class)} with Object.class
	 * @param toType
	 * @return
	 */
	public static Vector<Object> typed( Vector<?> toType){
		return typed(toType, Object.class);
	}
	
	/**
	 * Casts an untyped Vector to the supplied class.
	 * 
	 * Necessary because Notes-Api does not type its vectors.
	 * @param <T>
	 * @param toType
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Vector<T> typed( Vector<?> toType, Class<T> type){
		return (Vector<T>) toType;
	}
	
	/**
	 * Calls {@link #recycle(Base)} for each provided Base.
	 * 
	 * @param bases to recycle
	 */
	public static void recycle(Base...bases) {
		for ( Base base : bases) {
			NoteyUtils.recycle(base);
		}
	}
	
	/**
	 * Calls {@link Base#recycle()} on the supplied Base, silencing all NotesExceptions.
	 * 
	 * @param base to recycle
	 */
	public static void recycle(Base base) {
		try {
			base.recycle();
		} catch(NotesException ne) {
			// TODO: configurable if this throws?? ignore for now
		}
	}
	
	
}