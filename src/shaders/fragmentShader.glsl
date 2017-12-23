#version 400 core

in vec2 pass_textureCoords;
in vec3 normalVector;
in vec3 lightVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform vec3 fogColor;

void main(void) {

    vec3 unitNormalVector = normalize(normalVector);
    vec3 unitLightVector = normalize(lightVector);

    float dotProduct = dot(unitNormalVector, unitLightVector);
    float brightness = max(dotProduct, 0.2);

    vec3 light = brightness * lightColor;

    vec4 textureColor = texture(textureSampler, pass_textureCoords);

    if (textureColor.a < 0.5) {
        discard;
    }

    out_Color = vec4(light, 1) * textureColor;
    out_Color = mix(vec4(fogColor, 1), out_Color, visibility);

}