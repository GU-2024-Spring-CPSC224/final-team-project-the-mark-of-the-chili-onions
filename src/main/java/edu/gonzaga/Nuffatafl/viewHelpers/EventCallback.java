/**
 * Nuffatafl
 * CPSC 224, Spring 2024
 * Final Project
 * No sources to cite.
 *
 * @author Mark Reggiardo
 * @version v0.1.0 03/28/2024
 */

package edu.gonzaga.Nuffatafl.viewHelpers;

/** Allows us to use lamda expressions with 1 generic input and no return value */
public interface EventCallback<T> {
    void action(T data);
}
