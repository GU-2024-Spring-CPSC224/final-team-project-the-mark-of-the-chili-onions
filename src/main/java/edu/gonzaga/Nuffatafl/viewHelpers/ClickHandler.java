package edu.gonzaga.Nuffatafl.viewHelpers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface ClickHandler<T> {
    void onClick(T data);
}
