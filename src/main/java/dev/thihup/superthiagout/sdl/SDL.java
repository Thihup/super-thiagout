package dev.thihup.superthiagout.sdl;

import java.lang.invoke.MethodHandle;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.GroupLayout;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.SymbolLookup;
import jdk.incubator.foreign.ValueLayout;

public final class SDL {

    public static final int SWSURFACE = 0x00000000;
    public static final int HWSURFACE = 0x00000001;
    public static final int ASYNCBLIT = 0x00000004;

    public static final int ANYFORMAT = 0x10000000;
    public static final int HWPALETTE = 0x20000000;
    public static final int DOUBLEBUF = 0x40000000;
    public static final int FULLSCREEN = 0x80000000;
    public static final int OPENGL = 0x00000002;
    public static final int OPENGLBLIT = 0x0000000A;
    public static final int RESIZABLE = 0x00000010;
    public static final int NOFRAME = 0x00000020;

    private static final MethodHandle SDL_INIT;
    private static final MethodHandle SDL_SET_VIDEO_MODE;
    private static final MethodHandle SDL_FLIP;
    private static final MethodHandle SDL_QUIT;
    private static final MethodHandle SDL_POLL_EVENT;
    private static final MethodHandle SDL_MAP_RGB;
    private static final MethodHandle SDL_WM_SET_CAPTION;
    private static final MethodHandle SDL_GET_TICKS;
    private static final MethodHandle SDL_DELAY;
    private static final MethodHandle SDL_FILL_RECT;
    private static final MethodHandle SDL_GET_ERROR;

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

    public static int init(int flags) throws Throwable {
        return (int) SDL_INIT.invokeExact(flags);
    }

    public static int getTicks() throws Throwable {
        return (int) SDL_GET_TICKS.invokeExact();
    }

    public static int pollEvent(Addressable addressable) throws Throwable {
        return (int) SDL_POLL_EVENT.invokeExact(addressable);
    }

    public static int flip(Addressable addressable) throws Throwable {
        return (int) SDL_FLIP.invokeExact(addressable);
    }

    public static String getError() throws Throwable {
        MemoryAddress errorMessage = (MemoryAddress) SDL_GET_ERROR.invokeExact();
        return errorMessage.getUtf8String(0);
    }

    public static MemoryAddress setVideoMode(int width, int height, int bpp, int flags)
        throws Throwable {
        return (MemoryAddress) SDL_SET_VIDEO_MODE.invokeExact(width, height, bpp, flags);
    }

    public static int setCaption(Addressable title, Addressable icon) throws Throwable {
        return (int) SDL_WM_SET_CAPTION.invokeExact(title, icon);
    }

    public static void delay(int ms) throws Throwable {
        SDL_DELAY.invokeExact(ms);
    }

    public static int fillRect(Addressable surface, Addressable rect, int color) throws Throwable {
        return (int) SDL_FILL_RECT.invokeExact(surface, rect, color);
    }

    public static int mapRGB(Addressable format, int r, int g, int b) throws Throwable {
        return (int) SDL_MAP_RGB.invokeExact(format, r, g, b);
    }

    public static void quit() throws Throwable {
        SDL_QUIT.invokeExact();
    }

    private SDL() {
    }

    public static final class Event {

        public static final int NOEVENT = 0;
        /**
         * < Unused (do not remove)
         */
        public static final int ACTIVEEVENT = 1;
        /**
         * < Application loses/gains visibility
         */
        public static final int KEYDOWN = 2;
        /**
         * < Keys pressed
         */
        public static final int KEYUP = 3;
        /**
         * < Keys released
         */
        public static final int MOUSEMOTION = 4;
        /**
         * < Mouse moved
         */
        public static final int MOUSEBUTTONDOWN = 5;
        /**
         * < Mouse button pressed
         */
        public static final int MOUSEBUTTONUP = 6;
        /**
         * < Mouse button released
         */
        public static final int JOYAXISMOTION = 7;
        /**
         * < Joystick axis motion
         */
        public static final int JOYBALLMOTION = 8;
        /**
         * < Joystick trackball motion
         */
        public static final int JOYHATMOTION = 9;
        /**
         * < Joystick hat position change
         */
        public static final int JOYBUTTONDOWN = 10;
        /**
         * < Joystick button pressed
         */
        public static final int JOYBUTTONUP = 11;
        /**
         * < Joystick button released
         */
        public static final int QUIT = 12;
        /**
         * < User-requested quit
         */
        public static final int SYSWMEVENT = 13;
        /**
         * < System specific event
         */
        public static final int EVENT_RESERVEDA = 14;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVEDB = 15;
        /**
         * < Reserved for future use..
         */
        public static final int VIDEORESIZE = 16;
        /**
         * < User resized video mode
         */
        public static final int VIDEOEXPOSE = 17;
        /**
         * < Screen needs to be redrawn
         */
        public static final int EVENT_RESERVED2 = 18;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVED3 = 19;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVED4 = 20;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVED5 = 21;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVED6 = 22;
        /**
         * < Reserved for future use..
         */
        public static final int EVENT_RESERVED7 = 23;
        /**
         * < Reserved for future use..
         */
        public static final int USEREVENT = 24;
        public static final int NUMEVENTS = 32;

        public static final GroupLayout SDL_ACTIVE_EVENT_MEMORY_LAYOUT = MemoryLayout.structLayout(
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

        private Event() {
        }
    }

    public static final class Key {

        private Key() {
        }

        /**
         * @name ASCII mapped keysyms The keyboard syms have been cleverly chosen to map to ASCII
         */
        /*@{*/
        public static final int UNKNOWN = 0;
        public static final int FIRST = 0;
        public static final int BACKSPACE = 8;
        public static final int TAB = 9;
        public static final int CLEAR = 12;
        public static final int RETURN = 13;
        public static final int PAUSE = 19;
        public static final int ESCAPE = 27;
        public static final int SPACE = 32;
        public static final int EXCLAIM = 33;
        public static final int QUOTEDBL = 34;
        public static final int HASH = 35;
        public static final int DOLLAR = 36;
        public static final int AMPERSAND = 38;
        public static final int QUOTE = 39;
        public static final int LEFTPAREN = 40;
        public static final int RIGHTPAREN = 41;
        public static final int ASTERISK = 42;
        public static final int PLUS = 43;
        public static final int COMMA = 44;
        public static final int MINUS = 45;
        public static final int PERIOD = 46;
        public static final int SLASH = 47;
        public static final int K0 = 48;
        public static final int K1 = 49;
        public static final int K2 = 50;
        public static final int K3 = 51;
        public static final int K4 = 52;
        public static final int K5 = 53;
        public static final int K6 = 54;
        public static final int K7 = 55;
        public static final int K8 = 56;
        public static final int K9 = 57;
        public static final int COLON = 58;
        public static final int SEMICOLON = 59;
        public static final int LESS = 60;
        public static final int EQUALS = 61;
        public static final int GREATER = 62;
        public static final int QUESTION = 63;
        public static final int AT = 64;
        /*
           Skip uppercase letters
         */
        public static final int LEFTBRACKET = 91;
        public static final int BACKSLASH = 92;
        public static final int RIGHTBRACKET = 93;
        public static final int CARET = 94;
        public static final int UNDERSCORE = 95;
        public static final int BACKQUOTE = 96;
        public static final int a = 97;
        public static final int b = 98;
        public static final int c = 99;
        public static final int d = 100;
        public static final int e = 101;
        public static final int f = 102;
        public static final int g = 103;
        public static final int h = 104;
        public static final int i = 105;
        public static final int j = 106;
        public static final int k = 107;
        public static final int l = 108;
        public static final int m = 109;
        public static final int n = 110;
        public static final int o = 111;
        public static final int p = 112;
        public static final int q = 113;
        public static final int r = 114;
        public static final int s = 115;
        public static final int t = 116;
        public static final int u = 117;
        public static final int v = 118;
        public static final int w = 119;
        public static final int x = 120;
        public static final int y = 121;
        public static final int z = 122;
        public static final int DELETE = 127;
        /* End of ASCII mapped keysyms */
        /*@}*/

        /**
         * @name International keyboard syms
         */
        /*@{*/
        public static final int WORLD_0 = 160;        /* 0xA0 */
        public static final int WORLD_1 = 161;
        public static final int WORLD_2 = 162;
        public static final int WORLD_3 = 163;
        public static final int WORLD_4 = 164;
        public static final int WORLD_5 = 165;
        public static final int WORLD_6 = 166;
        public static final int WORLD_7 = 167;
        public static final int WORLD_8 = 168;
        public static final int WORLD_9 = 169;
        public static final int WORLD_10 = 170;
        public static final int WORLD_11 = 171;
        public static final int WORLD_12 = 172;
        public static final int WORLD_13 = 173;
        public static final int WORLD_14 = 174;
        public static final int WORLD_15 = 175;
        public static final int WORLD_16 = 176;
        public static final int WORLD_17 = 177;
        public static final int WORLD_18 = 178;
        public static final int WORLD_19 = 179;
        public static final int WORLD_20 = 180;
        public static final int WORLD_21 = 181;
        public static final int WORLD_22 = 182;
        public static final int WORLD_23 = 183;
        public static final int WORLD_24 = 184;
        public static final int WORLD_25 = 185;
        public static final int WORLD_26 = 186;
        public static final int WORLD_27 = 187;
        public static final int WORLD_28 = 188;
        public static final int WORLD_29 = 189;
        public static final int WORLD_30 = 190;
        public static final int WORLD_31 = 191;
        public static final int WORLD_32 = 192;
        public static final int WORLD_33 = 193;
        public static final int WORLD_34 = 194;
        public static final int WORLD_35 = 195;
        public static final int WORLD_36 = 196;
        public static final int WORLD_37 = 197;
        public static final int WORLD_38 = 198;
        public static final int WORLD_39 = 199;
        public static final int WORLD_40 = 200;
        public static final int WORLD_41 = 201;
        public static final int WORLD_42 = 202;
        public static final int WORLD_43 = 203;
        public static final int WORLD_44 = 204;
        public static final int WORLD_45 = 205;
        public static final int WORLD_46 = 206;
        public static final int WORLD_47 = 207;
        public static final int WORLD_48 = 208;
        public static final int WORLD_49 = 209;
        public static final int WORLD_50 = 210;
        public static final int WORLD_51 = 211;
        public static final int WORLD_52 = 212;
        public static final int WORLD_53 = 213;
        public static final int WORLD_54 = 214;
        public static final int WORLD_55 = 215;
        public static final int WORLD_56 = 216;
        public static final int WORLD_57 = 217;
        public static final int WORLD_58 = 218;
        public static final int WORLD_59 = 219;
        public static final int WORLD_60 = 220;
        public static final int WORLD_61 = 221;
        public static final int WORLD_62 = 222;
        public static final int WORLD_63 = 223;
        public static final int WORLD_64 = 224;
        public static final int WORLD_65 = 225;
        public static final int WORLD_66 = 226;
        public static final int WORLD_67 = 227;
        public static final int WORLD_68 = 228;
        public static final int WORLD_69 = 229;
        public static final int WORLD_70 = 230;
        public static final int WORLD_71 = 231;
        public static final int WORLD_72 = 232;
        public static final int WORLD_73 = 233;
        public static final int WORLD_74 = 234;
        public static final int WORLD_75 = 235;
        public static final int WORLD_76 = 236;
        public static final int WORLD_77 = 237;
        public static final int WORLD_78 = 238;
        public static final int WORLD_79 = 239;
        public static final int WORLD_80 = 240;
        public static final int WORLD_81 = 241;
        public static final int WORLD_82 = 242;
        public static final int WORLD_83 = 243;
        public static final int WORLD_84 = 244;
        public static final int WORLD_85 = 245;
        public static final int WORLD_86 = 246;
        public static final int WORLD_87 = 247;
        public static final int WORLD_88 = 248;
        public static final int WORLD_89 = 249;
        public static final int WORLD_90 = 250;
        public static final int WORLD_91 = 251;
        public static final int WORLD_92 = 252;
        public static final int WORLD_93 = 253;
        public static final int WORLD_94 = 254;
        public static final int WORLD_95 = 255;        /* 0xFF */
        /*@}*/

        /**
         * @name Numeric keypad
         */
        /*@{*/
        public static final int KP0 = 256;
        public static final int KP1 = 257;
        public static final int KP2 = 258;
        public static final int KP3 = 259;
        public static final int KP4 = 260;
        public static final int KP5 = 261;
        public static final int KP6 = 262;
        public static final int KP7 = 263;
        public static final int KP8 = 264;
        public static final int KP9 = 265;
        public static final int KP_PERIOD = 266;
        public static final int KP_DIVIDE = 267;
        public static final int KP_MULTIPLY = 268;
        public static final int KP_MINUS = 269;
        public static final int KP_PLUS = 270;
        public static final int KP_ENTER = 271;
        public static final int KP_EQUALS = 272;
        /*@}*/

        /**
         * @name Arrows + Home/End pad
         */
        /*@{*/
        public static final int UP = 273;
        public static final int DOWN = 274;
        public static final int RIGHT = 275;
        public static final int LEFT = 276;
        public static final int INSERT = 277;
        public static final int HOME = 278;
        public static final int END = 279;
        public static final int PAGEUP = 280;
        public static final int PAGEDOWN = 281;
        /*@}*/

        /**
         * @name Function keys
         */
        /*@{*/
        public static final int F1 = 282;
        public static final int F2 = 283;
        public static final int F3 = 284;
        public static final int F4 = 285;
        public static final int F5 = 286;
        public static final int F6 = 287;
        public static final int F7 = 288;
        public static final int F8 = 289;
        public static final int F9 = 290;
        public static final int F10 = 291;
        public static final int F11 = 292;
        public static final int F12 = 293;
        public static final int F13 = 294;
        public static final int F14 = 295;
        public static final int F15 = 296;
        /*@}*/

        /**
         * @name Key state modifier keys
         */
        /*@{*/
        public static final int NUMLOCK = 300;
        public static final int CAPSLOCK = 301;
        public static final int SCROLLOCK = 302;
        public static final int RSHIFT = 303;
        public static final int LSHIFT = 304;
        public static final int RCTRL = 305;
        public static final int LCTRL = 306;
        public static final int RALT = 307;
        public static final int LALT = 308;
        public static final int RMETA = 309;
        public static final int LMETA = 310;
        public static final int LSUPER = 311;
        /**
         * < Left "Windows" key
         */
        public static final int RSUPER = 312;
        /**
         * < Right "Windows" key
         */
        public static final int MODE = 313;
        /**
         * < "Alt Gr" key
         */
        public static final int COMPOSE = 314;        /**< Multi-key compose key */
        /*@}*/

        /**
         * @name Miscellaneous function keys
         */
        /*@{*/
        public static final int HELP = 315;
        public static final int PRINT = 316;
        public static final int SYSREQ = 317;
        public static final int BREAK = 318;
        public static final int MENU = 319;
        public static final int POWER = 320;
        /**
         * < Power Macintosh power key
         */
        public static final int EURO = 321;
        /**
         * < Some european keyboards
         */
        public static final int UNDO = 322;        /**< Atari keyboard has Undo */
        /*@}*/

    }
}
