package com.sovani;


import java.util.concurrent.ThreadLocalRandom;


public class JokesMaster {

    // Jokes sourced from http://onelinefun.com
    private static String[] jokes =
            {
                    "Today a man knocked on my door and asked for a small donation towards the local swimming pool. I gave him a glass of water.",
                    "A recent study has found that women who carry a little extra weight live longer than the men who mention it.",
                    "I'm not saying I hate you, but I would unplug your life support to charge my phone.",
                    "I find it ironic that the colors red, white, and blue stand for freedom until they are flashing behind you.",
                    "Artificial intelligence is no match for natural stupidity.",
            };

    public String getJoke () {
        int random = ThreadLocalRandom.current().nextInt(0, jokes.length);
        return jokes[random];
    }
}
