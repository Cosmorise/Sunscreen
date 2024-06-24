package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.util.Pos2D;

public interface Element {

    Canvas render();

    Pos2D size();

}
