package dev.thihup.superthiagout;

import java.util.Objects;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemoryLayout.PathElement;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

public final class Rect {

    private static final MemoryLayout SDL_RECT = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("x"),
        ValueLayout.JAVA_SHORT.withName("y"),
        ValueLayout.JAVA_SHORT.withName("w"),
        ValueLayout.JAVA_SHORT.withName("h")
    );
    public static final long X_PATH = SDL_RECT.byteOffset(PathElement.groupElement("x"));
    public static final long Y_PATH = SDL_RECT.byteOffset(PathElement.groupElement("y"));
    private short x;
    private short y;
    private final short width;
    private final short height;
    private final MemorySegment sdlRect;


    public Rect(short x, short y, short width, short height, SegmentAllocator allocator) {
        this(x, y, width, height, fill(allocator.allocate(SDL_RECT), x, y, width, height));
    }

    public Rect(int x, int y, int width, int height, SegmentAllocator allocator) {
        this((short) x, (short) y, (short) width, (short) height,
            fill(allocator.allocate(SDL_RECT), (short) x, (short) y, (short) width,
                (short) height));
    }

    private static MemorySegment fill(MemorySegment address, short x, short y, short w, short h) {
        address.set(ValueLayout.JAVA_SHORT, SDL_RECT.byteOffset(PathElement.groupElement("x")), x);
        address.set(ValueLayout.JAVA_SHORT, SDL_RECT.byteOffset(PathElement.groupElement("y")), y);
        address.set(ValueLayout.JAVA_SHORT, SDL_RECT.byteOffset(PathElement.groupElement("w")), w);
        address.set(ValueLayout.JAVA_SHORT, SDL_RECT.byteOffset(PathElement.groupElement("h")), h);
        return address;
    }


    public Rect(short x, short y, short width, short height, MemorySegment sdlRect) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sdlRect = sdlRect;
    }

    public short moveTo() {
        return x;
    }

    public short moveToX(short x) {
        if (x < 0 || x + width > Main.SCREEN_WIDTH) {
            return this.x;
        }
        this.x = x;
        sdlRect.set(ValueLayout.JAVA_SHORT, X_PATH, x);
        return x;
    }

    public short moveToY() {
        return y;
    }

    public short moveToY(short y) {
        if (y < 0 || y > Main.SCREEN_HEIGHT) {
            return this.y;
        }
        this.y = y;
        sdlRect.set(ValueLayout.JAVA_SHORT, Y_PATH, y);
        return y;
    }

    public short width() {
        return width;
    }

    public short height() {
        return height;
    }

    public MemorySegment sdlRect() {
        return sdlRect;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Rect) obj;
        return this.x == that.x &&
            this.y == that.y &&
            this.width == that.width &&
            this.height == that.height &&
            Objects.equals(this.sdlRect, that.sdlRect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, sdlRect);
    }

    @Override
    public String toString() {
        return "Rect[" +
            "x=" + x + ", " +
            "y=" + y + ", " +
            "width=" + width + ", " +
            "height=" + height + ", " +
            "sdlRect=" + sdlRect + ']';
    }


}
