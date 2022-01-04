package dev.thihup.superthiagout;


import static dev.thihup.superthiagout.sdl.SDL.Event.SDL_EVENT_MEMORY_LAYOUT;
import static jdk.incubator.foreign.MemoryLayout.PathElement.groupElement;

import dev.thihup.superthiagout.sdl.SDL;
import dev.thihup.superthiagout.sdl.SDL.Event;
import java.lang.System.Logger.Level;
import java.lang.invoke.VarHandle;
import java.lang.ref.Cleaner;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.random.RandomGenerator;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

public class Main {

    private static final System.Logger LOGGER = System.getLogger(Main.class.getName());

    private static final int FPS = 1000 / 60;
    private static final int SIZE = 10;
    static final int SCREEN_WIDTH = 390;
    static final int SCREEN_HEIGHT = 300;

    private static final short BRICK_WIDTH = 3 * SIZE;
    private static final short BRICK_HEIGHT = (int) (SIZE * 1.5);

    private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.getDefault();
    private static final VarHandle SDL_EVENT_TYPE_HANDLE = SDL_EVENT_MEMORY_LAYOUT.varHandle(
        groupElement("type"));
    private static final VarHandle SDL_KEY_KEY_SYM_SYM_HANDLE = SDL_EVENT_MEMORY_LAYOUT.varHandle(
        groupElement("key"), groupElement("keysym"), groupElement("sym"));

    static boolean fpsManager(int currentTime) throws Throwable {
        currentTime = SDL.getTicks() - currentTime;
        if (currentTime < FPS) {
            SDL.delay(FPS - currentTime);
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws Throwable {
        if (SDL.init(0) != 0) {
            String error = SDL.getError();
            LOGGER.log(Level.ERROR, error);
            return;
        }

        try (ResourceScope resourceScope = ResourceScope.newConfinedScope()) {
            SegmentAllocator segmentAllocator = SegmentAllocator.nativeAllocator(resourceScope);
            MemorySegment event = segmentAllocator.allocate(SDL_EVENT_MEMORY_LAYOUT);

            MemoryAddress sdlSurface = SDL.setVideoMode(390, 300, 0,
                1342177280);

            boolean isRunning = true;
            while (isRunning) {
                while (SDL.pollEvent(event) != 0) {
                    int eventCode = event.get(ValueLayout.JAVA_BYTE, 0);
                    if (eventCode == Event.QUIT) {
                        isRunning = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SDL.quit();
    }

    private static List<Brick> getBricks(SegmentAllocator segmentAllocator,
        Addressable screenFormat) throws Throwable {
        List<Brick> bricks = new CopyOnWriteArrayList<>();
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 13; i++) {
                int r = RANDOM_GENERATOR.nextInt(256);
                int g = RANDOM_GENERATOR.nextInt(256);
                int b = RANDOM_GENERATOR.nextInt(256);

                int sdlRGB = SDL.mapRGB(screenFormat, r, g, b);

                Rect rect = new Rect((i * BRICK_WIDTH), (j * BRICK_HEIGHT), BRICK_WIDTH,
                    BRICK_HEIGHT, segmentAllocator);

                Brick brick = new Brick(sdlRGB, true, rect);

                bricks.add(brick);
            }
        }
        return bricks;
    }
}
