package dev.thihup.superthiagout;

import static dev.thihup.superthiagout.SDL.SDL_DELAY;
import static dev.thihup.superthiagout.SDL.SDL_EVENT_MEMORY_LAYOUT;
import static dev.thihup.superthiagout.SDL.SDL_FILL_RECT;
import static dev.thihup.superthiagout.SDL.SDL_FLIP;
import static dev.thihup.superthiagout.SDL.SDL_GET_ERROR;
import static dev.thihup.superthiagout.SDL.SDL_GET_TICKS;
import static dev.thihup.superthiagout.SDL.SDL_INIT;
import static dev.thihup.superthiagout.SDL.SDL_MAP_RGB;
import static dev.thihup.superthiagout.SDL.SDL_POLL_EVENT;
import static dev.thihup.superthiagout.SDL.SDL_QUIT;
import static dev.thihup.superthiagout.SDL.SDL_SET_VIDEO_MODE;
import static dev.thihup.superthiagout.SDL.SDL_WM_SET_CAPTION;
import static jdk.incubator.foreign.MemoryLayout.PathElement.groupElement;

import dev.thihup.superthiagout.SDLConstants.SDLKey;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.random.RandomGenerator;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

public class Main {


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
        currentTime = (int) SDL_GET_TICKS.invokeExact() - currentTime;
        if (currentTime < FPS) {
            SDL_DELAY.invokeExact(FPS - currentTime);
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Throwable {
        if ((int) SDL_INIT.invokeExact(0) != 0) {
            MemoryAddress errorMessage = (MemoryAddress) SDL_GET_ERROR.invokeExact();
            String error = errorMessage.getUtf8String(0);
            System.out.println(error);
            return;
        }

        try (ResourceScope resourceScope = ResourceScope.newConfinedScope()) {
            SegmentAllocator segmentAllocator = SegmentAllocator.nativeAllocator(resourceScope);
            Addressable event = segmentAllocator.allocate(SDL_EVENT_MEMORY_LAYOUT);

            Addressable sdlSurface = (MemoryAddress) SDL_SET_VIDEO_MODE.invokeExact(390, 300, 0,
                1342177280);

            int result = (int) SDL_WM_SET_CAPTION.invokeExact(
                (Addressable) segmentAllocator.allocateUtf8String("Super Thiagout - by Thihup"),
                (Addressable) MemoryAddress.NULL);

            if (sdlSurface == MemoryAddress.NULL) {
                MemoryAddress errorMessage = (MemoryAddress) SDL_GET_ERROR.invokeExact();
                String error = errorMessage.getUtf8String(0);
                System.out.println(error);
                return;
            }

            MemoryAddress screenFormat = ((MemoryAddress) sdlSurface).get(ValueLayout.ADDRESS, 8);

            List<Brick> bricks = new CopyOnWriteArrayList<>();
            for (int j = 0; j < 6; j++) {
                for (int i = 0; i < 13; i++) {
                    bricks.add(new Brick((int) SDL_MAP_RGB.invokeExact((Addressable) screenFormat,
                        RANDOM_GENERATOR.nextInt(256), RANDOM_GENERATOR.nextInt(256),
                        RANDOM_GENERATOR.nextInt(256)), true,
                        new Rect((short) (i * BRICK_WIDTH), (short) (j * BRICK_HEIGHT), BRICK_WIDTH,
                            BRICK_HEIGHT, segmentAllocator)));
                }
            }

            Brick raquete = new Brick(
                (int) SDL_MAP_RGB.invokeExact((Addressable) screenFormat, 255, 255, 0), true,
                new Rect((390 - 70) / 2, 300 - 2 * BRICK_HEIGHT, 70, BRICK_HEIGHT,
                    segmentAllocator));

            Wall wall = new Wall(bricks);

            Rect rect = new Rect(RANDOM_GENERATOR.nextInt(SCREEN_WIDTH),
                (SCREEN_HEIGHT / 2 - (3 * BRICK_HEIGHT)), SIZE, SIZE, segmentAllocator);

            Brick ball = new Brick(
                (int) SDL_MAP_RGB.invokeExact((Addressable) screenFormat, 255, 0, 0), true, rect);

            enum Direction {
                LEFT, RIGHT, NONE
            }
            Direction direction = Direction.NONE;

            boolean isRunning = true;
            while (isRunning) {
                int ticks = (int) SDL_GET_TICKS.invokeExact();

                while ((int) SDL_POLL_EVENT.invokeExact((Addressable) event) != 0) {
                    int eventCode = (int) SDL_EVENT_TYPE_HANDLE.get(event);
                    switch (eventCode) {
                        case SDLConstants.SDLEvent.SDL_QUIT -> isRunning = false;
                        case SDLConstants.SDLEvent.SDL_KEYDOWN -> {
                            int i = (int) SDL_KEY_KEY_SYM_SYM_HANDLE.get(event);
                            direction = switch (i) {
                                case SDLKey.SDLK_RIGHT -> Direction.RIGHT;
                                case SDLKey.SDLK_LEFT -> Direction.LEFT;
                                default -> direction;
                            };

                        }
                        case SDLConstants.SDLEvent.SDL_KEYUP -> {
                            int i = (int) SDL_KEY_KEY_SYM_SYM_HANDLE.get(event);
                            direction = switch (i) {
                                case SDLKey.SDLK_LEFT, SDLKey.SDLK_RIGHT -> Direction.NONE;
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

                    int color = (int) SDL_MAP_RGB.invokeExact((Addressable) screenFormat, 255, 255,
                        255);

                    int ignored = (int) SDL_FILL_RECT.invokeExact(sdlSurface,
                        (Addressable) MemoryAddress.NULL, color);
                    wall.draw(sdlSurface);
                    raquete.draw(sdlSurface);
                    ball.draw(sdlSurface);
                    int ignoredResult = (int) SDL_FLIP.invokeExact(sdlSurface);
                }
            }

            SDL_QUIT.invokeExact();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
