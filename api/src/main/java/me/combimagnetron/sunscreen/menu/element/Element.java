package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.menu.Canvas;
import me.combimagnetron.comet.feature.menu.Editable;

public interface Element extends Editable {

    Identifier identifier();

    Canvas canvas();

    Position position();

}
