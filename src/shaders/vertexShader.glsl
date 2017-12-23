#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 normalVector;
out vec3 lightVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;
uniform int useFakeLighting;

uniform int numberOfRows;
uniform vec2 offset;

const float density = 0.0035;
const float gradient = 5;

void main(void) {

    gl_Position = projectionMatrix * viewMatrix *  transformationMatrix * vec4(position, 1.0);
    pass_textureCoords = textureCoords / numberOfRows + offset;

    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCamera = viewMatrix * worldPosition;

    float distanceToCamera = length(positionRelativeToCamera.xyz);

    visibility = exp(-pow(distanceToCamera * density, gradient));
    visibility = clamp(visibility, 0, 1);

    if (useFakeLighting == 1) {
        normalVector = vec3(0.0, 1.0, 0.0);
    }
    else {
        normalVector = (transformationMatrix * vec4(normal, 0.0)).xyz;
    }

    lightVector = lightPosition - worldPosition.xyz;

}