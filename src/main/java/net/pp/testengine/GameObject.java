package net.pp.testengine;

import java.awt.*;

public interface GameObject {
    void update();
    void render(TestEngine engine, Rectangle blueBounds);
}
