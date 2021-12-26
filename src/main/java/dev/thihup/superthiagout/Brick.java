package dev.thihup.superthiagout;

import jdk.incubator.foreign.Addressable;

public record Brick(int color, boolean visible, Rect rect) {

    public void draw(Addressable screen) throws Throwable {
        if (!visible) {
            return;
        }
        int result = (int) SDL.SDL_FILL_RECT.invokeExact(screen,
            (Addressable) rect.sdlRect().address(),
            color);
    }

}
