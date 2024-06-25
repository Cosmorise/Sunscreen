package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.menu.Editable;

public interface Element extends Editable {

    Identifier identifier();

    Canvas canvas();

    Position position();

}
