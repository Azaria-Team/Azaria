#define MEDIUM

#define M1 vec4(0.8, 0.2, 0.1, 1.0)
#define M2 vec3(0.9, 0.4, 0.2)
#define M3 vec3(1.0, 0.6, 0.3)

#define NSCALE 200.0 / 2.0
#define DSCALE 180.0 / 2.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

void main(){
    vec2 c = v_texCoords.xy;
    vec2 coords = (c * u_resolution) + u_campos;

    vec4 orig = texture2D(u_texture, c);

    float atime = u_time / 20000.0;
    float noise = (texture2D(u_noise, (coords) / DSCALE + vec2(atime) * vec2(-0.9, 0.8)).r + texture2D(u_noise, (coords) / DSCALE + vec2(atime * 1.1) * vec2(0.8, -1.0)).r) / 2.0;

    noise = abs(noise - 0.5) * 7.0 + 0.23;

    float btime = u_time / 12000.0;

    c += (vec2(
        texture2D(u_noise, (coords) / NSCALE + vec2(btime) * vec2(-0.9, 0.8)).r,
        texture2D(u_noise, (coords) / NSCALE + vec2(btime * 1.1) * vec2(0.8, -1.0)).r
    ) - vec2(0.5)) * 20.0 / u_resolution;

    vec4 color = texture2D(u_texture, c);

    if(noise > 0.9){
        if(color.g >= (M2).g - 0.1){
            color.rgb = M3;
        }else{
            color.rgb = M2;
        }
    }else if(noise > 0.6){
        color.rgb = M2;
    }

    if(orig.g > 0.02){
        color = max(M1, color);
    }

    gl_FragColor = color;
}
