package net.pp.testengine;

import lombok.AllArgsConstructor;

/**
 * Created by Klimpen on 22/04/2017.
 */
@AllArgsConstructor
public class MiniMap {
    TestEngine te;
    int x, y;

    public void render(Player player){
        te.pushMatrix();
        te.ellipseMode(te.CENTER);
        te.noFill();
        te.stroke(0);

        te.ellipse(x,y,50,50);
        te.ellipse(x,y,100,100);
        te.ellipse(x,y,200,200);

        te.translate(x,y);
        te.rotate(player.getCamRot()-(float)(Math.PI));
        for(Player p : te.playerList){
            if(p.equals(player)){
                te.fill(255,0,0);
            } else {
                te.fill(255,255,0);
            }
            te.ellipse(player.getRelative(p).x*5,player.getRelative(p).y*5,5,5);
        }
        te.popMatrix();
    }
}