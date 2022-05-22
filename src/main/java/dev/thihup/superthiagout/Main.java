package dev.thihup.superthiagout;


import static dev.thihup.superthiagout.sdl.SDL.Event.SDL_EVENT_MEMORY_LAYOUT;
import static java.lang.foreign.MemoryLayout.PathElement.groupElement;

import dev.thihup.superthiagout.sdl.SDL;
import dev.thihup.superthiagout.sdl.SDL.Event;
import java.lang.System.Logger.Level;
import java.lang.foreign.MemorySession;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.random.RandomGenerator;
import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

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

        try (MemorySession resourceScope = MemorySession.openConfined()) {
            SegmentAllocator segmentAllocator = SegmentAllocator.newNativeArena(resourceScope);
            Addressable event = segmentAllocator.allocate(SDL_EVENT_MEMORY_LAYOUT);

            MemoryAddress sdlSurface = SDL.setVideoMode(390, 300, 0,
                SDL.SWSURFACE | SDL.DOUBLEBUF | SDL.ANYFORMAT);

            if (sdlSurface == MemoryAddress.NULL) {
                String error = SDL.getError();
                LOGGER.log(Level.ERROR, error);
                return;
            }

            SDL.setCaption(
                segmentAllocator.allocateUtf8String("Super Thiagout - by Thihup"),
                MemoryAddress.NULL);

            MemoryAddress screenFormat = sdlSurface.get(ValueLayout.ADDRESS, 8);

            int color = SDL.mapRGB(screenFormat, 255, 255, 255);

            List<Brick> bricks = getBricks(segmentAllocator, screenFormat);

            Brick raquete = new Brick(
                SDL.mapRGB(screenFormat, 255, 255, 0), true,
                new Rect((390 - 70) / 2, 300 - 2 * BRICK_HEIGHT, 70, BRICK_HEIGHT,
                    segmentAllocator));

            Wall wall = new Wall(bricks);

            Rect rect = new Rect(RANDOM_GENERATOR.nextInt(SCREEN_WIDTH),
                (SCREEN_HEIGHT / 2 - (3 * BRICK_HEIGHT)), SIZE, SIZE, segmentAllocator);

            Brick ball = new Brick(SDL.mapRGB(screenFormat, 255, 0, 0), true, rect);

            Direction direction = Direction.NONE;

            boolean isRunning = true;
            while (isRunning) {
                int ticks = SDL.getTicks();

                while (SDL.pollEvent(event) != 0) {
                    int eventCode = (int) SDL_EVENT_TYPE_HANDLE.get(event);
                    switch (eventCode) {
                        case Event.QUIT -> isRunning = false;
                        case Event.KEYDOWN -> {
                            int i = (int) SDL_KEY_KEY_SYM_SYM_HANDLE.get(event);
                            direction = switch (i) {
                                case SDL.Key.RIGHT -> Direction.RIGHT;
                                case SDL.Key.LEFT -> Direction.LEFT;
                                default -> direction;
                            };

                        }
                        case Event.KEYUP -> {
                            int i = (int) SDL_KEY_KEY_SYM_SYM_HANDLE.get(event);
                            direction = switch (i) {
                                case SDL.Key.LEFT, SDL.Key.RIGHT -> Direction.NONE;
                                default -> direction;
                            };
                        }

                        default -> {
                        }
                    }
                }

                Rect rect1 = raquete.rect();
                switch (direction) {
                    case RIGHT -> rect1.moveToX((short) (rect1.moveTo() + 10));
                    case LEFT -> rect1.moveToX((short) (rect1.moveTo() - 10));
                }

                if (fpsManager(ticks)) {
                    SDL.fillRect(sdlSurface, MemoryAddress.NULL, color);
                    wall.draw(sdlSurface);
                    raquete.draw(sdlSurface);
                    ball.draw(sdlSurface);

                    SDL.flip(sdlSurface);
                }
            }

            SDL.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
