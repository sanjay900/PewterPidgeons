package net.tangentmc.model.MD2;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;

public class ClippableImporter {
    public ClippableImporter() {
    }

    public MD2ClippableModel importModel(File f, PImage texture, PApplet applet) throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(Files.readAllBytes(f.toPath()));
        buf.order(ByteOrder.LITTLE_ENDIAN);
        Header header = new Header(buf);
        String[] skins = new String[header.getSkinCount()];
        short[][] uvs = new short[header.getUVCount()][2];
        short[][] tris = new short[header.getTriCount()][6];
        Frame[] frames = new Frame[header.getFrameCount()];
        float[] glCmds = new float[header.getGlCmdCount()];
        buf.position(header.getSkinOffset());
        byte[] skin = new byte[64];

        int index;
        for(index = 0; index < header.getSkinCount(); ++index) {
            buf.get(skin);
            skins[index] = (new String(skin)).trim();
        }

        buf.position(header.getUVOffset());

        for(index = 0; index < header.getUVCount(); ++index) {
            uvs[index][0] = buf.getShort();
            uvs[index][1] = buf.getShort();
        }

        buf.position(header.getTriOffset());

        int i;
        for(index = 0; index < header.getTriCount(); ++index) {
            for(i = 0; i < tris[index].length; ++i) {
                tris[index][i] = buf.getShort();
            }
        }

        buf.position(header.getFrameOffset());

        for(index = 0; index < header.getFrameCount(); ++index) {
            frames[index] = new Frame(buf, header.getVertexCount());
        }

        buf.position(header.getGlCmdOffset());
        i = 0;

        while(i < header.getGlCmdCount()) {
            glCmds[i++] = (float)(index = buf.getInt());

            for(int i2 = 0; i2 < Math.abs(index); ++i2) {
                glCmds[i++] = buf.getFloat();
                glCmds[i++] = buf.getFloat();
                glCmds[i++] = (float)buf.getInt();
            }
        }

        return new MD2ClippableModel(header, frames, glCmds, texture, applet, uvs, tris);
    }
}

