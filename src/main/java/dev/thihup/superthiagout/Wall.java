package dev.thihup.superthiagout;

import java.util.List;
import java.lang.foreign.Addressable;

public record Wall(List<Brick> bricks){

    public void draw(Addressable screen) throws Throwable {
        for(Brick brick: bricks){
            brick.draw(screen);
        }
    }

}
