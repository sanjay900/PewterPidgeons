package net.pp.testengine;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

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
            te.ellipse(player.getRelative(p.getLocation()).x*5,player.getRelative(p.getLocation()).y*5,5,5);
        }
        for(ArrayList<Room> rooms : te.manager.stairMap.values()){
            for (Room r: rooms) {
                te.fill(0,255,255);
                te.ellipse(player.getRelative(r.getPosition()).x*5, player.getRelative(r.getPosition()).y*5, 5, 5);

            }
        }
        for(Collectible c : te.manager.collectibles){
            te.fill(0,255,0);
            te.ellipse(player.getRelative(c.getPosition()).x*5, player.getRelative(c.getPosition()).y*5, 5, 5);
        }
    }
}