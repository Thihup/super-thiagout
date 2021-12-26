package dev.thihup.superthiagout;

public final class SDLConstants {

    public static final class SDLEvent {

        public static final int SDL_NOEVENT = 0;
        /**
         * < Unused (do not remove)
         */
        public static final int SDL_ACTIVEEVENT = 1;
        /**
         * < Application loses/gains visibility
         */
        public static final int SDL_KEYDOWN = 2;
        /**
         * < Keys pressed
         */
        public static final int SDL_KEYUP = 3;
        /**
         * < Keys released
         */
        public static final int SDL_MOUSEMOTION = 4;
        /**
         * < Mouse moved
         */
        public static final int SDL_MOUSEBUTTONDOWN = 5;
        /**
         * < Mouse button pressed
         */
        public static final int SDL_MOUSEBUTTONUP = 6;
        /**
         * < Mouse button released
         */
        public static final int SDL_JOYAXISMOTION = 7;
        /**
         * < Joystick axis motion
         */
        public static final int SDL_JOYBALLMOTION = 8;
        /**
         * < Joystick trackball motion
         */
        public static final int SDL_JOYHATMOTION = 9;
        /**
         * < Joystick hat position change
         */
        public static final int SDL_JOYBUTTONDOWN = 10;
        /**
         * < Joystick button pressed
         */
        public static final int SDL_JOYBUTTONUP = 11;
        /**
         * < Joystick button released
         */
        public static final int SDL_QUIT = 12;
        /**
         * < User-requested quit
         */
        public static final int SDL_SYSWMEVENT = 13;
        /**
         * < System specific event
         */
        public static final int SDL_EVENT_RESERVEDA = 14;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVEDB = 15;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_VIDEORESIZE = 16;
        /**
         * < User resized video mode
         */
        public static final int SDL_VIDEOEXPOSE = 17;
        /**
         * < Screen needs to be redrawn
         */
        public static final int SDL_EVENT_RESERVED2 = 18;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVED3 = 19;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVED4 = 20;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVED5 = 21;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVED6 = 22;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_EVENT_RESERVED7 = 23;
        /**
         * < Reserved for future use..
         */
        public static final int SDL_USEREVENT = 24;
        public static final int SDL_NUMEVENTS = 32;

        private SDLEvent() {
        }
    }

    public static final class SDLKey {
        /** @name ASCII mapped keysyms
         *  The keyboard syms have been cleverly chosen to map to ASCII
         */
        /*@{*/
        public static final int SDLK_UNKNOWN		= 0;
        public static final int SDLK_FIRST		= 0;
        public static final int SDLK_BACKSPACE		= 8;
        public static final int SDLK_TAB		= 9;
        public static final int SDLK_CLEAR		= 12;
        public static final int SDLK_RETURN		= 13;
        public static final int SDLK_PAUSE		= 19;
        public static final int SDLK_ESCAPE		= 27;
        public static final int SDLK_SPACE		= 32;
        public static final int SDLK_EXCLAIM		= 33;
        public static final int SDLK_QUOTEDBL		= 34;
        public static final int SDLK_HASH		= 35;
        public static final int SDLK_DOLLAR		= 36;
        public static final int SDLK_AMPERSAND		= 38;
        public static final int SDLK_QUOTE		= 39;
        public static final int SDLK_LEFTPAREN		= 40;
        public static final int SDLK_RIGHTPAREN		= 41;
        public static final int SDLK_ASTERISK		= 42;
        public static final int SDLK_PLUS		= 43;
        public static final int SDLK_COMMA		= 44;
        public static final int SDLK_MINUS		= 45;
        public static final int SDLK_PERIOD		= 46;
        public static final int SDLK_SLASH		= 47;
        public static final int SDLK_0			= 48;
        public static final int SDLK_1			= 49;
        public static final int SDLK_2			= 50;
        public static final int SDLK_3			= 51;
        public static final int SDLK_4			= 52;
        public static final int SDLK_5			= 53;
        public static final int SDLK_6			= 54;
        public static final int SDLK_7			= 55;
        public static final int SDLK_8			= 56;
        public static final int SDLK_9			= 57;
        public static final int SDLK_COLON		= 58;
        public static final int SDLK_SEMICOLON		= 59;
        public static final int SDLK_LESS		= 60;
        public static final int SDLK_EQUALS		= 61;
        public static final int SDLK_GREATER		= 62;
        public static final int SDLK_QUESTION		= 63;
        public static final int SDLK_AT			= 64;
	/*
	   Skip uppercase letters
	 */
        public static final int SDLK_LEFTBRACKET	= 91;
        public static final int SDLK_BACKSLASH		= 92;
        public static final int SDLK_RIGHTBRACKET	= 93;
        public static final int SDLK_CARET		= 94;
        public static final int SDLK_UNDERSCORE		= 95;
        public static final int SDLK_BACKQUOTE		= 96;
        public static final int SDLK_a			= 97;
        public static final int SDLK_b			= 98;
        public static final int SDLK_c			= 99;
        public static final int SDLK_d			= 100;
        public static final int SDLK_e			= 101;
        public static final int SDLK_f			= 102;
        public static final int SDLK_g			= 103;
        public static final int SDLK_h			= 104;
        public static final int SDLK_i			= 105;
        public static final int SDLK_j			= 106;
        public static final int SDLK_k			= 107;
        public static final int SDLK_l			= 108;
        public static final int SDLK_m			= 109;
        public static final int SDLK_n			= 110;
        public static final int SDLK_o			= 111;
        public static final int SDLK_p			= 112;
        public static final int SDLK_q			= 113;
        public static final int SDLK_r			= 114;
        public static final int SDLK_s			= 115;
        public static final int SDLK_t			= 116;
        public static final int SDLK_u			= 117;
        public static final int SDLK_v			= 118;
        public static final int SDLK_w			= 119;
        public static final int SDLK_x			= 120;
        public static final int SDLK_y			= 121;
        public static final int SDLK_z			= 122;
        public static final int SDLK_DELETE		= 127;
        /* End of ASCII mapped keysyms */
        /*@}*/

        /** @name International keyboard syms */
        /*@{*/
        public static final int SDLK_WORLD_0		= 160;		/* 0xA0 */
        public static final int SDLK_WORLD_1		= 161;
        public static final int SDLK_WORLD_2		= 162;
        public static final int SDLK_WORLD_3		= 163;
        public static final int SDLK_WORLD_4		= 164;
        public static final int SDLK_WORLD_5		= 165;
        public static final int SDLK_WORLD_6		= 166;
        public static final int SDLK_WORLD_7		= 167;
        public static final int SDLK_WORLD_8		= 168;
        public static final int SDLK_WORLD_9		= 169;
        public static final int SDLK_WORLD_10		= 170;
        public static final int SDLK_WORLD_11		= 171;
        public static final int SDLK_WORLD_12		= 172;
        public static final int SDLK_WORLD_13		= 173;
        public static final int SDLK_WORLD_14		= 174;
        public static final int SDLK_WORLD_15		= 175;
        public static final int SDLK_WORLD_16		= 176;
        public static final int SDLK_WORLD_17		= 177;
        public static final int SDLK_WORLD_18		= 178;
        public static final int SDLK_WORLD_19		= 179;
        public static final int SDLK_WORLD_20		= 180;
        public static final int SDLK_WORLD_21		= 181;
        public static final int SDLK_WORLD_22		= 182;
        public static final int SDLK_WORLD_23		= 183;
        public static final int SDLK_WORLD_24		= 184;
        public static final int SDLK_WORLD_25		= 185;
        public static final int SDLK_WORLD_26		= 186;
        public static final int SDLK_WORLD_27		= 187;
        public static final int SDLK_WORLD_28		= 188;
        public static final int SDLK_WORLD_29		= 189;
        public static final int SDLK_WORLD_30		= 190;
        public static final int SDLK_WORLD_31		= 191;
        public static final int SDLK_WORLD_32		= 192;
        public static final int SDLK_WORLD_33		= 193;
        public static final int SDLK_WORLD_34		= 194;
        public static final int SDLK_WORLD_35		= 195;
        public static final int SDLK_WORLD_36		= 196;
        public static final int SDLK_WORLD_37		= 197;
        public static final int SDLK_WORLD_38		= 198;
        public static final int SDLK_WORLD_39		= 199;
        public static final int SDLK_WORLD_40		= 200;
        public static final int SDLK_WORLD_41		= 201;
        public static final int SDLK_WORLD_42		= 202;
        public static final int SDLK_WORLD_43		= 203;
        public static final int SDLK_WORLD_44		= 204;
        public static final int SDLK_WORLD_45		= 205;
        public static final int SDLK_WORLD_46		= 206;
        public static final int SDLK_WORLD_47		= 207;
        public static final int SDLK_WORLD_48		= 208;
        public static final int SDLK_WORLD_49		= 209;
        public static final int SDLK_WORLD_50		= 210;
        public static final int SDLK_WORLD_51		= 211;
        public static final int SDLK_WORLD_52		= 212;
        public static final int SDLK_WORLD_53		= 213;
        public static final int SDLK_WORLD_54		= 214;
        public static final int SDLK_WORLD_55		= 215;
        public static final int SDLK_WORLD_56		= 216;
        public static final int SDLK_WORLD_57		= 217;
        public static final int SDLK_WORLD_58		= 218;
        public static final int SDLK_WORLD_59		= 219;
        public static final int SDLK_WORLD_60		= 220;
        public static final int SDLK_WORLD_61		= 221;
        public static final int SDLK_WORLD_62		= 222;
        public static final int SDLK_WORLD_63		= 223;
        public static final int SDLK_WORLD_64		= 224;
        public static final int SDLK_WORLD_65		= 225;
        public static final int SDLK_WORLD_66		= 226;
        public static final int SDLK_WORLD_67		= 227;
        public static final int SDLK_WORLD_68		= 228;
        public static final int SDLK_WORLD_69		= 229;
        public static final int SDLK_WORLD_70		= 230;
        public static final int SDLK_WORLD_71		= 231;
        public static final int SDLK_WORLD_72		= 232;
        public static final int SDLK_WORLD_73		= 233;
        public static final int SDLK_WORLD_74		= 234;
        public static final int SDLK_WORLD_75		= 235;
        public static final int SDLK_WORLD_76		= 236;
        public static final int SDLK_WORLD_77		= 237;
        public static final int SDLK_WORLD_78		= 238;
        public static final int SDLK_WORLD_79		= 239;
        public static final int SDLK_WORLD_80		= 240;
        public static final int SDLK_WORLD_81		= 241;
        public static final int SDLK_WORLD_82		= 242;
        public static final int SDLK_WORLD_83		= 243;
        public static final int SDLK_WORLD_84		= 244;
        public static final int SDLK_WORLD_85		= 245;
        public static final int SDLK_WORLD_86		= 246;
        public static final int SDLK_WORLD_87		= 247;
        public static final int SDLK_WORLD_88		= 248;
        public static final int SDLK_WORLD_89		= 249;
        public static final int SDLK_WORLD_90		= 250;
        public static final int SDLK_WORLD_91		= 251;
        public static final int SDLK_WORLD_92		= 252;
        public static final int SDLK_WORLD_93		= 253;
        public static final int SDLK_WORLD_94		= 254;
        public static final int SDLK_WORLD_95		= 255;		/* 0xFF */
        /*@}*/

        /** @name Numeric keypad */
        /*@{*/
        public static final int SDLK_KP0		= 256;
        public static final int SDLK_KP1		= 257;
        public static final int SDLK_KP2		= 258;
        public static final int SDLK_KP3		= 259;
        public static final int SDLK_KP4		= 260;
        public static final int SDLK_KP5		= 261;
        public static final int SDLK_KP6		= 262;
        public static final int SDLK_KP7		= 263;
        public static final int SDLK_KP8		= 264;
        public static final int SDLK_KP9		= 265;
        public static final int SDLK_KP_PERIOD		= 266;
        public static final int SDLK_KP_DIVIDE		= 267;
        public static final int SDLK_KP_MULTIPLY	= 268;
        public static final int SDLK_KP_MINUS		= 269;
        public static final int SDLK_KP_PLUS		= 270;
        public static final int SDLK_KP_ENTER		= 271;
        public static final int SDLK_KP_EQUALS		= 272;
        /*@}*/

        /** @name Arrows + Home/End pad */
        /*@{*/
        public static final int SDLK_UP			= 273;
        public static final int SDLK_DOWN		= 274;
        public static final int SDLK_RIGHT		= 275;
        public static final int SDLK_LEFT		= 276;
        public static final int SDLK_INSERT		= 277;
        public static final int SDLK_HOME		= 278;
        public static final int SDLK_END		= 279;
        public static final int SDLK_PAGEUP		= 280;
        public static final int SDLK_PAGEDOWN		= 281;
        /*@}*/

        /** @name Function keys */
        /*@{*/
        public static final int SDLK_F1			= 282;
        public static final int SDLK_F2			= 283;
        public static final int SDLK_F3			= 284;
        public static final int SDLK_F4			= 285;
        public static final int SDLK_F5			= 286;
        public static final int SDLK_F6			= 287;
        public static final int SDLK_F7			= 288;
        public static final int SDLK_F8			= 289;
        public static final int SDLK_F9			= 290;
        public static final int SDLK_F10		= 291;
        public static final int SDLK_F11		= 292;
        public static final int SDLK_F12		= 293;
        public static final int SDLK_F13		= 294;
        public static final int SDLK_F14		= 295;
        public static final int SDLK_F15		= 296;
        /*@}*/

        /** @name Key state modifier keys */
        /*@{*/
        public static final int SDLK_NUMLOCK		= 300;
        public static final int SDLK_CAPSLOCK		= 301;
        public static final int SDLK_SCROLLOCK		= 302;
        public static final int SDLK_RSHIFT		= 303;
        public static final int SDLK_LSHIFT		= 304;
        public static final int SDLK_RCTRL		= 305;
        public static final int SDLK_LCTRL		= 306;
        public static final int SDLK_RALT		= 307;
        public static final int SDLK_LALT		= 308;
        public static final int SDLK_RMETA		= 309;
        public static final int SDLK_LMETA		= 310;
        public static final int SDLK_LSUPER		= 311;		/**< Left "Windows" key */
        public static final int SDLK_RSUPER		= 312;		/**< Right "Windows" key */
        public static final int SDLK_MODE		= 313;		/**< "Alt Gr" key */
        public static final int SDLK_COMPOSE		= 314;		/**< Multi-key compose key */
        /*@}*/

        /** @name Miscellaneous function keys */
        /*@{*/
        public static final int SDLK_HELP		= 315;
        public static final int SDLK_PRINT		= 316;
        public static final int SDLK_SYSREQ		= 317;
        public static final int SDLK_BREAK		= 318;
        public static final int SDLK_MENU		= 319;
        public static final int SDLK_POWER		= 320;		/**< Power Macintosh power key */
        public static final int SDLK_EURO		= 321;		/**< Some european keyboards */
        public static final int SDLK_UNDO		= 322;		/**< Atari keyboard has Undo */
        /*@}*/

    }

    private SDLConstants() {
    }
}
