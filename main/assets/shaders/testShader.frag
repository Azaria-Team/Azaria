#define MEDIUM

#define M1 vec3(0.2, 0.05, 0.05)
#define M2 vec3(0.4, 0.1, 0.1)
#define M3 vec3(0.6, 0.15, 0.15)
#define NSCALE 150.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

void main(){
    vec2 c = v_texCoords.xy;
    vec2 coords = vec2(c.x * u_resolution.x + u_campos.x, c.y * u_resolution.y + u_campos.y);

    float btime = u_time / 7000.0;
    float wave = abs(sin(coords.x * 1.3 + coords.y) + 0.1 * sin(2.8 * coords.x) + 0.15 * sin(3.5 * coords.y)) / 50.0;
    float noise = wave + (texture2D(u_noise, (coords) / NSCALE + vec2(btime) * vec2(-0.2, 0.8)).r + texture2D(u_noise, (coords) / NSCALE + vec2(btime * 1.1) * vec2(0.8, -1.0)).r) / 2.0;
    vec4 color = texture2D(u_texture, c);

    if(noise > 0.52 && noise < 0.56){
        color.rgb = M3;
    }else if (noise > 0.45 && noise < 0.65){
        color.rgb = M2;
    }else if (noise > 0.35 && noise < 0.75){
        color.rgb = M1;
    }

    gl_FragColor = color;
}