package com.nierprotomata.game.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

public class TextureManager {
    public static LinkedList<Texture> textures = new LinkedList<>();

    public static boolean load(FileHandle file) {
        return textures.add(new Texture(file));
    }

    public static Texture get(int textureIndex) {
        return textures.get(textureIndex);
    }

    public static void dispose() {
        for(Texture texture : textures) {
            texture.dispose();
        }
    }
}
