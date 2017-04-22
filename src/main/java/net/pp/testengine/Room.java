    package net.pp.testengine;

    import lombok.AllArgsConstructor;
    import lombok.ToString;
    import processing.core.PConstants;
    import processing.core.PGraphics;
    import processing.core.PImage;
    import processing.core.PVector;

    import java.awt.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    import static processing.core.PConstants.QUADS;

    /**
     * Created by Klimpen on 21/04/2017.
     * TODO everything
     */
    @ToString
    @AllArgsConstructor
    public class Room implements GameObject{
        private MapManager manager;
        Location position;
        public static final int ROOM_SIZE = 100;
        public boolean isWall;
        List<GameObject> entities = new ArrayList<>();
        public boolean isStair;


        public Room(MapManager manager, Location loc, boolean isWall) {
            this.manager = manager;
            this.position = loc;
            this.isWall = isWall;
        }

        public void randomize() {

    //        en = new HiddenEntity(300,300,25);
        }
        @Override
        public void update() {
            entities.forEach(GameObject::update);
        }
        PImage wall;
        PImage floor;
        PImage roof;
        String[] roofImages = {"roof.png"};
        String[] floorImages = {"step_texture.png"};
        @Override
        public void render(TestEngine engine, Rectangle bounds) {
            if (wall == null) {
                wall = loadImage(engine,"stone_wall.png");
                floor = loadImage(engine,floorImages[(int) engine.random(floorImages.length)]);
                roof = loadImage(engine,roofImages[(int) engine.random(roofImages.length)]);
            }
            engine.pushMatrix();
            engine.translate(position.getX()*Room.ROOM_SIZE,(-position.getZ()*(Room.ROOM_SIZE+0.01f)),position.getY()*Room.ROOM_SIZE);
            if (isWall) {
                engine.scale(Room.ROOM_SIZE/2);
                engine.beginShape(QUADS);
                engine.texture(wall);
                PVector cam = PVector.fromAngle(-engine.player.getCamRot());
                // Given one texture and six faces, we can easily set up the uv coordinates
                // such that four of the faces tile "perfectly" along either u or v, but the other
                // two faces cannot be so aligned.  This code tiles "along" u, "around" the X/Z faces
                // and fudges the Y faces - the Y faces are arbitrarily aligned such that a
                // rotation along the X axis will put the "top" of either texture at the "top"
                // of the screen, but is not otherwised aligned with the X/Z faces. (This
                // just affects what type of symmetry is required if you need seamless
                // tiling all the way around the cube)
                // +Z "front" face
                engine.vertex(-1, -1, 1, 0, 0);
                engine.vertex(1, -1, 1, 1, 0);
                engine.vertex(1, 1, 1, 1, 1);
                engine.vertex(-1, 1, 1, 0, 1);
                engine.normal(0, 0, 1);
                // -Z "back" face
                engine.vertex(1, -1, -1, 0, 0);
                engine.vertex(-1, -1, -1, 1, 0);
                engine.vertex(-1, 1, -1, 1, 1);
                engine.vertex(1, 1, -1, 0, 1);
                engine.normal(0, 0, -1);
                // +X "right" face
                engine.vertex(1, -1, 1, 0, 0);
                engine.vertex(1, -1, -1, 1, 0);
                engine.vertex(1, 1, -1, 1, 1);
                engine.vertex(1, 1, 1, 0, 1);
                engine.normal(1, 0, 0);
                // -X "left" face
                engine.vertex(-1, -1, -1, 0, 0);
                engine.vertex(-1, -1, 1, 1, 0);
                engine.vertex(-1, 1, 1, 1, 1);
                engine.vertex(-1, 1, -1, 0, 1);
                engine.normal(-1, 0, 0);


                engine.endShape();
            } else if (isStair) {
                engine.translate(0,Room.ROOM_SIZE/2,0);
                engine.rotateX(PConstants.HALF_PI);
                engine.scale(5);
                Models.STAIR.model.drawModel(null,engine);
            } else {
                engine.fill(255, 255, 255);
                engine.scale(Room.ROOM_SIZE/2);
                if (!manager.isStair(position.getRelative(Direction.DOWN))) {
                    engine.beginShape(PConstants.QUAD);
                    engine.texture(floor);
                    // +Y "bottom" face
                    engine.vertex(-1,  1,  1, 0, 0);
                    engine.vertex( 1,  1,  1, 1, 0);
                    engine.vertex( 1,  1, -1, 1, 1);
                    engine.vertex(-1,  1, -1, 0, 1);
                    engine.endShape();
                }
                engine.beginShape(PConstants.QUAD);
                engine.texture(roof);
                // -Y "top" face
                engine.vertex(-1, -1, -1, 0, 0);
                engine.vertex( 1, -1, -1, 1, 0);
                engine.vertex( 1, -1,  1, 1, 1);
                engine.vertex(-1, -1,  1, 0, 1);
                engine.endShape();
            }
            engine.popMatrix();
            entities.forEach(o -> o.render(engine, bounds));
        }

        public Color renderOffscreen(TestEngine engine, PGraphics offscreen) {
            if (!isWall && !isStair) return null;
            offscreen.pushMatrix();
            offscreen.translate(position.getX()*Room.ROOM_SIZE,(-position.getZ()*Room.ROOM_SIZE),position.getY()*Room.ROOM_SIZE);
            Color c = new Color((int)engine.random(255),(int)engine.random(255),(int)engine.random(255));
            offscreen.fill(c.getRed(),c.getGreen(), c.getBlue());
            offscreen.box(ROOM_SIZE);
            offscreen.popMatrix();
            return c;
        }
        static HashMap<String,PImage> imageHashMap = new HashMap<>();
        private static PImage loadImage(TestEngine engine, String image) {
            if (imageHashMap.containsKey(image)) return  imageHashMap.get(image);
            PImage image3 = engine.loadImage(image);
            imageHashMap.put(image,image3);
            return image3;
        }
    }
