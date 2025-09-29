package az.graphics;

import mindustry.graphics.CacheLayer;

public class AzCacheLayers extends CacheLayer {
    public static CacheLayer
    testShader, testShader2, testShader3;

    public static void init() {
        addLast(
                testShader = new ShaderLayer(AzShaders.testShader),
                testShader2 = new ShaderLayer(AzShaders.testShader2),
                testShader3 = new ShaderLayer(AzShaders.testShader3)
        );
    }
}
