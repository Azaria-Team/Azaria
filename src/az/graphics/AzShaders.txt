package az.graphics;

import mindustry.graphics.Shaders;

public class AzShaders extends Shaders {

public static SurfaceShader testShader, testShader2, testShader3;
    public static void init() {
        blockbuild = new BlockBuildShader();
        try{
            shield = new ShieldShader();
        }catch(Throwable t){
            //don't load shield shader
            shield = null;
            t.printStackTrace();
        }
        testShader = new SurfaceShader("testShader");
        testShader2 = new SurfaceShader("testShader2");
        testShader3 = new SurfaceShader("testShader3");
    }
}
