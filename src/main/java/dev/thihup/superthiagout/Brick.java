package dev.thihup.superthiagout;

import dev.thihup.superthiagout.sdl.SDL;
import jdk.incubator.foreign.Addressable;

public record Brick(int color, boolean visible, Rect rect) {

    public void draw(Addressable screen) throws Throwable {
        if (!visible) {
            return;
        }
        int result = SDL.fillRect(screen, rect.sdlRect().address(), color);
    }

}
