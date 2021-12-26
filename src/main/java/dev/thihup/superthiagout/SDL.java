package dev.thihup.superthiagout;

import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.GroupLayout;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.SymbolLookup;
import jdk.incubator.foreign.ValueLayout;

public final class SDL {

    public static final MethodHandle SDL_INIT;
    public static final MethodHandle SDL_SET_VIDEO_MODE;
    public static final MethodHandle SDL_FLIP;
    public static final MethodHandle SDL_QUIT;
    public static final MethodHandle SDL_POLL_EVENT;
    public static final MethodHandle SDL_MAP_RGB;
    public static final MethodHandle SDL_WM_SET_CAPTION;
    public static final MethodHandle SDL_GET_TICKS;
    public static final MethodHandle SDL_DELAY;
    public static final MethodHandle SDL_FILL_RECT;
    public static final MethodHandle SDL_GET_ERROR;

    private static final GroupLayout SDL_ACTIVE_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("gain"),
        ValueLayout.JAVA_BYTE.withName("state")
    );

    public static final GroupLayout SDL_KEYSYM_MEMORY_LAYOUT = MemoryLayout.structLayout(
        MemoryLayout.paddingLayout(24),
        ValueLayout.JAVA_BYTE.withName("scancode"),
        ValueLayout.JAVA_INT.withName("sym"),
        ValueLayout.JAVA_INT.withName("mod"),
        MemoryLayout.paddingLayout(16),
        ValueLayout.JAVA_SHORT.withName("unicode"));

    public static final GroupLayout SDL_KEYBOARD_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("state"),
        MemoryLayout.paddingLayout(16),
        SDL_KEYSYM_MEMORY_LAYOUT.withName("keysym")
    );
    public static final GroupLayout SDL_MOUSE_MOTION_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("state"),
        ValueLayout.JAVA_SHORT.withName("x"),
        ValueLayout.JAVA_SHORT.withName("y"),
        ValueLayout.JAVA_SHORT.withName("xrel"),
        ValueLayout.JAVA_SHORT.withName("yrel"));
    public static final GroupLayout SDL_MOUSE_BUTTON_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("button"),
        ValueLayout.JAVA_BYTE.withName("state"),
        ValueLayout.JAVA_SHORT.withName("x"),
        ValueLayout.JAVA_SHORT.withName("y"));
    public static final GroupLayout SDL_JOY_AXIS_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("which"),
        ValueLayout.JAVA_BYTE.withName("axis"),
        ValueLayout.JAVA_SHORT.withName("value"));
    public static final GroupLayout SDL_JOY_BALL_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("which"),
        ValueLayout.JAVA_BYTE.withName("ball"),
        ValueLayout.JAVA_SHORT.withName("xrel"),
        ValueLayout.JAVA_SHORT.withName("yrel"));
    public static final GroupLayout SDL_JOY_HAT_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("which"),
        ValueLayout.JAVA_BYTE.withName("hat"),
        ValueLayout.JAVA_BYTE.withName("value"));
    public static final GroupLayout SDL_JOY_BUTTON_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_BYTE.withName("which"),
        ValueLayout.JAVA_BYTE.withName("button"),
        ValueLayout.JAVA_BYTE.withName("state"));
    public static final GroupLayout SDL_RESIZE_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_INT.withName("w"),
        ValueLayout.JAVA_INT.withName("h"));
    public static final GroupLayout SDL_EXPOSE_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"));
    public static final GroupLayout SDL_QUIT_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"));
    public static final GroupLayout SDL_USER_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.JAVA_INT.withName("code"),
        ValueLayout.ADDRESS.withName("data1"),
        ValueLayout.ADDRESS.withName("data2"));
    public static final GroupLayout SDL_SYS_VM_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        ValueLayout.ADDRESS.withName("msg"));
    public static final GroupLayout SDL_EVENT_MEMORY_LAYOUT = MemoryLayout.unionLayout(
        ValueLayout.JAVA_BYTE.withName("type"),
        SDL_ACTIVE_EVENT_MEMORY_LAYOUT.withName("active"),
        SDL_KEYBOARD_EVENT_MEMORY_LAYOUT.withName("key"),
        SDL_MOUSE_MOTION_EVENT_MEMORY_LAYOUT.withName("motion"),
        SDL_MOUSE_BUTTON_EVENT_MEMORY_LAYOUT.withName("button"),
        SDL_JOY_AXIS_EVENT_MEMORY_LAYOUT.withName("jaxis"),
        SDL_JOY_BALL_EVENT_MEMORY_LAYOUT.withName("jball"),
        SDL_JOY_HAT_EVENT_MEMORY_LAYOUT.withName("jhat"),
        SDL_JOY_BUTTON_EVENT_MEMORY_LAYOUT.withName("jbutton"),
        SDL_RESIZE_EVENT_MEMORY_LAYOUT.withName("resize"),
        SDL_EXPOSE_EVENT_MEMORY_LAYOUT.withName("expose"),
        SDL_QUIT_EVENT_MEMORY_LAYOUT.withName("quit"),
        SDL_USER_EVENT_MEMORY_LAYOUT.withName("user"),
        SDL_SYS_VM_EVENT_MEMORY_LAYOUT.withName("syswm"));

    static {
        System.loadLibrary("SDL");

        SymbolLookup symbolLookup = SymbolLookup.loaderLookup();
        CLinker systemCLinker = CLinker.systemCLinker();

        SDL_INIT = symbolLookup.lookup("SDL_Init")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT))).orElseThrow();
        SDL_SET_VIDEO_MODE = symbolLookup.lookup("SDL_SetVideoMode")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT))
            ).orElseThrow();
        SDL_FLIP = symbolLookup.lookup("SDL_Flip")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS))).orElseThrow();
        SDL_QUIT = symbolLookup.lookup("SDL_Quit")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.ofVoid())).orElseThrow();
        SDL_POLL_EVENT = symbolLookup.lookup("SDL_PollEvent")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS))).orElseThrow();

        SDL_MAP_RGB = symbolLookup.lookup("SDL_MapRGB")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT))).orElseThrow();

        SDL_WM_SET_CAPTION = symbolLookup.lookup("SDL_WM_SetCaption")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS))).orElseThrow();

        SDL_GET_TICKS = symbolLookup.lookup("SDL_GetTicks")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT))).orElseThrow();
        SDL_DELAY = symbolLookup.lookup("SDL_Delay").map(
                x -> systemCLinker.downcallHandle(x, FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT)))
            .orElseThrow();
        SDL_FILL_RECT = symbolLookup.lookup("SDL_FillRect")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS,
                ValueLayout.JAVA_INT))).orElseThrow();

        SDL_GET_ERROR = symbolLookup.lookup("SDL_GetError")
            .map(x -> systemCLinker.downcallHandle(x, FunctionDescriptor.of(
                ValueLayout.ADDRESS))).orElseThrow();
    }

    private SDL() {
    }
}
