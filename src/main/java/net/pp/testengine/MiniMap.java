package net.pp.testengine;

import lombok.AllArgsConstructor;

/**
 * Created by Klimpen on 22/04/2017.
 */
@AllArgsConstructor
public class MiniMap {
    TestEngine te;

    public void render(Player player){
        te.pushMatrix();
        te.ellipseMode(te.CENTER);
        te.noFill();
        te.stroke(0);

        te.translate(te.width-100,te.height-100);
        te.ellipse(0,0,50,50);
        te.ellipse(0,0,100,100);
        te.ellipse(0,0,200,200);

        te.rotate(player.getCamRot()-(float)(Math.PI));
        for(Player p : te.playerMap.values()){
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