#define HIGHP

#define S1 vec4(0.1, 0.3, 0.7, 1.0)
#define S2 vec4(0.2, 0.6, 0.9, 1.0)
#define S3 vec4(0.4, 0.8, 1.0, 1.0)

#define NSCALE 200.0 / 2.0
#define DSCALE 180.0 / 2.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

float roundedTriangle(vec2 p, vec2 a, vec2 b, vec2 c, float radius) {
    vec2 ba = b - a;
    vec2 ca = c - a;
    vec2 pa = p - a;

    float h1 = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    float h2 = clamp(dot(pa, ca) / dot(ca, ca), 0.0, 1.0);

    vec2 pb = ba * h1;
    vec2 pc = ca * h2;

    return smoothstep(radius, 0.0, length(pa - pb - pc));
}

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

    // Define triangle vertices
    vec2 a = vec2(0.5, 0.1);
    vec2 b = vec2(0.1, 0.9);
    vec2 c = vec2(0.9, 0.9);
    // Define corner radius
    float radius = 0.1;

    // Calculate smooth edge
    float edge = roundedTriangle(c, a, b, c, radius);

    vec4 color = mix(S1, S2, edge);

    if(noise > 0.9){
        color = S3;
    } else if(noise > 0.6){
        color = S2;
    }

    if(orig.g > 0.02){
        color = max(S1, color);
    }

    gl_FragColor = color;
}
